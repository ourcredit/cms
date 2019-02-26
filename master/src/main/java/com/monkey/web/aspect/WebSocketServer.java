package com.monkey.web.aspect;


import java.io.IOException;
import java.net.SocketException;
import java.sql.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.html.parser.Entity;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.monkey.application.Device.IDeviceService;
import com.monkey.application.OperationLogs.IErrorlogService;
import com.monkey.application.Payfor.IChargeorderService;
import com.monkey.application.Payfor.ICustomerOrderService;
import com.monkey.application.Payfor.IOrderService;
import com.monkey.application.Payfor.OrderServiceImpl;
import com.monkey.common.base.SocketConstant;
import com.monkey.core.entity.*;
import com.monkey.web.config.SpringContextBean;
import com.monkey.web.controller.dtos.WebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{clientId}")
@Component
public class WebSocketServer {
    protected static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    @Autowired
    IOrderService _orderService;
    @Autowired
    ICustomerOrderService _customerOrderService;
    @Autowired
    IChargeorderService _chargeOrderService;
    @Autowired
    IDeviceService _deviceService;
    @Autowired
    IErrorlogService _errorlogService;
    private static Map<String, Integer> clientsState = new ConcurrentHashMap<>();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static Integer onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String, WebSocketServer> clients = new ConcurrentHashMap();
    //   private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String clientId;
    //状态
    private Boolean state;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId) throws SocketException {
        if (this._deviceService == null) {
            this._deviceService = SpringContextBean.getBean(IDeviceService.class);
        }
        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("deviceNum", clientId.toLowerCase());
        Device d = _deviceService.selectOne(ew);
        if (d != null) {
            logger.error("设备链入:" + d.getDeviceNum());
            this.session = session;
            this.clientId = clientId;
            this.state = true;
            clients.put(this.clientId, this);     //加入set中
            clientsState.put(this.clientId, 1);
            addOnlineCount();           //在线数加1
            try {
                WebSocketMessage m = new WebSocketMessage(this.clientId, "connect success", SocketConstant.HEART);
                sendMessageTo(m);
            } catch (IOException e) {
                logger.error("发送消息报错" + e.getMessage());
            }
        } else {
            throw new SocketException("设备信息不存在");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        logger.error("设备退出" + this.clientId);
        clients.remove(this.clientId);
        clientsState.put(this.clientId, 0);
        subOnlineCount();           //在线数减1
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        JSONObject jsonTo = JSONObject.parseObject(message);
        Integer type = (Integer) jsonTo.get("type");

        if (type == SocketConstant.HEART) {
            Heart();
        } else if (type == SocketConstant.UPDATEORDERSTATE) {
            logger.error("接收到订单同步消息:" + message);
            if (this._orderService == null) {
                this._orderService = SpringContextBean.getBean(IOrderService.class);
            }
            if (this._deviceService == null) {
                this._deviceService = SpringContextBean.getBean(IDeviceService.class);
            }
            if (this._customerOrderService == null) {
                this._customerOrderService = SpringContextBean.getBean(ICustomerOrderService.class);
            }
            if (this._chargeOrderService == null) {
                this._chargeOrderService = SpringContextBean.getBean(IChargeorderService.class);
            }
            JSONObject data = jsonTo.getJSONObject("data");
            String key = data.getString("id");
            Order o = _orderService.selectById(key);
            if (o == null) {
                //更新订单
                WebSocketMessage m = new WebSocketMessage(this.clientId, "订单信息不存在", SocketConstant.ERROR);
                sendMessageTo(m);
                return;
            }
            Integer state = (Integer) data.get("orderState");
            if (o.getCustomerOrder() != null && !o.getCustomerOrder().isEmpty() && o.getOrderFrom() == 0) {
                CustomerOrder co = _customerOrderService.selectById(o.getCustomerOrder());
                if (co == null) {
                    //更新订单
                    WebSocketMessage m = new WebSocketMessage(this.clientId, "客户订单信息不存在", SocketConstant.ERROR);
                    sendMessageTo(m);
                }
                co.setOrderState(state == 4 ? 3 : state);
                _customerOrderService.updateById(co);
                o.setOrderState(state == 4 ? 3 : state);
            } else {
                if (o.getPayType() == 4 && state >= 3 &&o.getOrderState()<=2) {
                    o.setPayState(1);
                    EntityWrapper ew = new EntityWrapper();
                    ew.eq("deviceNum", o.getDeviceNum());
                    Device d = _deviceService.selectOne(ew);
                    if (d != null && d.getCount() > 0) {
                        d.setCount(d.getCount() - 1);
                        _deviceService.updateById(d);
                    }
                }
                o.setOrderState(state);
            }
            _orderService.updateById(o);
            //更新订单
            WebSocketMessage m = new WebSocketMessage(this.clientId, "订单状态更新成功", SocketConstant.NOTIFYORDERSTATE);
            sendMessageTo(m);
        } else if (type == SocketConstant.ERROR) {
            logger.error("接收到错误日志消息:" + message);
            //写日志
            if (this._errorlogService == null) {
                this._errorlogService = SpringContextBean.getBean(IErrorlogService.class);
            }
            if (this._deviceService == null) {
                this._deviceService = SpringContextBean.getBean(IDeviceService.class);
            }
            try {
                JSONObject data = jsonTo.getJSONObject("data");
                String num = data.getString("deviceNum");
                Errorlog er = new Errorlog();
                if (!num.isEmpty()) {
                    EntityWrapper ew = new EntityWrapper();
                    ew.eq("deviceNum", num);
                    Device d = _deviceService.selectOne(ew);
                    if (d != null) {
                        er.setDeviceName(d.getDeviceName());
                        er.setPointName(d.getPointName());
                    }
                }
                String logType = data.getString("logType");
                String logMessage = data.getString("message");
                er.setDeviceNum(num);
                er.setLogType(Integer.parseInt(logType));
                er.setMessage(logMessage);
                _errorlogService.insert(er);
            } catch (Exception e) {
                logger.error("写入错误日志失败:原因" + e.getMessage());
            }
        }
    }

    private void Heart() {
        WebSocketServer ws = clients.get(this.clientId);
        if (!ws.state) {
            ws.state = true;
        }
    }

    /**
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("设备信息:" + this.clientId + "," + "错误信息:" + throwable.getMessage());
    }

    public void sendMessageTo(WebSocketMessage message) throws IOException {
        String json = JSON.toJSONString(message);
        Map<String, WebSocketServer> maps = clients;
        for (WebSocketServer item : maps.values()) {
            if (item.clientId != null && item.clientId.equals(message.to))
                item.session.getAsyncRemote().sendText(json);
        }
    }

    public void sendMessageAll(WebSocketMessage message) throws IOException {
        String json = JSON.toJSONString(message);
        Map<String, WebSocketServer> maps = clients;
        for (WebSocketServer item : maps.values()) {
            item.session.getAsyncRemote().sendText(json);
        }
    }

    public static synchronized WebSocketServer getClient(String id) {
        return clients.get(id);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        Integer count = onlineCount;
        if (count == null) {
            onlineCount = 0;
        }
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        Integer count = onlineCount;
        if (count == null) {
            onlineCount = 0;
        } else if (count > 0) {
            onlineCount--;
        }
    }

}