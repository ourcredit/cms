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
import com.monkey.common.base.SocketConstant;
import com.monkey.web.controller.dtos.WebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{clientId}")
@Component
public class WebSocketServer {
    protected static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
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
        // if (this._deviceService == null) {
        //     this._deviceService = SpringContextBean.getBean(IDeviceService.class);
        // }
       
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
            //更新订单
            WebSocketMessage m = new WebSocketMessage(this.clientId, "订单状态更新成功", SocketConstant.NOTIFYORDERSTATE);
            sendMessageTo(m);
        } else if (type == SocketConstant.ERROR) {
            logger.error("接收到错误日志消息:" + message);
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