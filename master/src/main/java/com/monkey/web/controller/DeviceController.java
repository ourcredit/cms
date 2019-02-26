package com.monkey.web.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Device.IDeviceProductService;
import com.monkey.application.Device.IDeviceService;
import com.monkey.application.Device.IPointService;
import com.monkey.application.OperationLogs.IErrorlogService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.Constant;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.dtos.ProductDto;
import com.monkey.core.entity.Device;
import com.monkey.core.entity.Errorlog;
import com.monkey.core.entity.User;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.annotation.Log;
import com.monkey.web.annotation.Pass;
import com.monkey.web.aspect.WebSocketServer;
import com.monkey.web.controller.dtos.DeviceInput;
import com.monkey.web.controller.dtos.DeviceProductInput;
import com.monkey.web.controller.dtos.ProductInput;
import com.monkey.web.controller.dtos.UserLoginInput;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/device")
public class DeviceController {
    @Autowired
    IDeviceService _deviceService;
    @Autowired
    IPointService _pointService;
    @Autowired
    IDeviceProductService _deviceProductService;
    @Autowired
    IErrorlogService _errorLogService;

    @ApiOperation(value = "获取设备列表",notes = "设备列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._devices._device.list})
    public PublicResult<Page<Device>> devices(@RequestBody PagedAndFilterInputDto page) throws Exception{
        EntityWrapper<Device> filter = new EntityWrapper<>();
      Object online=  page.where.get("online");
      page.where.remove("online");
        filter=  ComUtil.genderFilter(filter,page.where,true);
        String code=  (String)page.where.get("code");
        if(code!=null&&!code.isEmpty()){
            if(code.equals(Constant.UnknownCode)){
                filter.where("pointId is null").or("pointId=''");
            }else {
                List<Integer> ids=   _pointService.selectPointIdsByCode(code);
                if(!ids.isEmpty()){
                    filter.in("pointId",ids);
                }else{
                    return  new PublicResult<Page<Device>>(PublicResultConstant.SUCCESS,new Page<>());
                }
            }
        }
        Page<Device> res= _deviceService.selectPage(new Page<>(page.index,page.size), filter);
        List<Device> result=new ArrayList<>();
       res.getRecords().forEach(w->{
           WebSocketServer ws=  WebSocketServer.getClient(w.getDeviceNum());
           w.setOnline(ws==null?0:1);
           if(online!=null){
               Integer onl=Integer.parseInt(online.toString());
            if(onl==1&&ws!=null){
            result.add(w);
            }
            if(onl==0&&ws==null){
                result.add(w);
            }
           }else{
            result.add(w);  
           }
       });
       res.setRecords(result);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @Pass
    @ApiOperation(value = "获取设备详情",notes = "设备列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @RequiresPermissions(value = {PermissionConst._devices._device.first})
    public PublicResult<Device> Device(@PathVariable Integer id) throws Exception{
        Device m=_deviceService.selectById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @ApiOperation(value = "设备自注册",notes = "设备列表")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @Pass
    public PublicResult<Device> register(@RequestBody DeviceInput model){
       
        EntityWrapper ew=new EntityWrapper();
        ew.eq("deviceNum",model.deviceNum);
        Device d=_deviceService.selectOne(ew);
        if(d!=null) return new PublicResult<Device>(PublicResultConstant.SUCCESS, d);
        Device m=new Device();
        m.setDeviceName(model.deviceName);
        m.setDeviceNum(model.deviceNum);
        m.setPassWord(BCrypt.hashpw(model.deviceNum, BCrypt.gensalt()));
      if(_deviceService.insert(m)){
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
      }  
      return new PublicResult<>(PublicResultConstant.FAILED, null);
    }
    @Pass
    @ApiOperation(value = "获取运维权限",notes = "设备列表")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public PublicResult<Device> login(@RequestBody UserLoginInput model){
        EntityWrapper ew=new EntityWrapper();
        ew.eq("deviceNum",model.userName);
        Device d=_deviceService.selectOne(ew    );
        if(d==null)return new PublicResult<Device>(PublicResultConstant.NORESULTS,null);

        if ( !BCrypt.checkpw(model.passWord, d.getPassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, d);
    }
    @Log(description="设备管理:/添加或编辑设备")
    @ApiOperation(value = "添加或编辑设备",notes = "设备列表")
    @RequestMapping(method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._devices._device.modify})
    public PublicResult<Object> insert(@RequestBody Device model) throws Exception{
        if(model.getPassWord()!=null&&!model.getPassWord().isEmpty()){
            String pass=  BCrypt.hashpw(model.getPassWord(), BCrypt.gensalt());
            model.setPassWord(pass);
        }
        Boolean r=_deviceService.insertOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="设备管理:/添加设备记录")
    @ApiOperation(value = "添加设备记录",notes = "设备列表")
    @RequestMapping(value = "/add/{id}/{count}",method = RequestMethod.PUT)
    @RequiresPermissions(value = {PermissionConst._devices._device.addcount})
    public PublicResult<Object> addCount(@PathVariable Integer id, @PathVariable Integer count, @CurrentUser User current) throws Exception{
        if(count<=0)return new PublicResult<Object>(PublicResultConstant.FAILED,"次数不能小于0",null);
        Device d= _deviceService.selectById(id  );
        if(d!=null){
            d.setCount(d.getCount()+count);
        }
        Boolean r=_deviceService.updateById(d);
        if(r){
            Errorlog er=new Errorlog();
            er.setDeviceNum(d.getDeviceNum());
            er.setDeviceName(d.getDeviceName());
            er.setPointName(d.getPointName());
            String message= current.getUserName()+ "增加:"+count +"次,当前总次数:"+d.getCount();
            er.setMessage(message);
            er.setLogType(3);
            _errorLogService.insert(er);
        }
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="设备管理:/删除设备")
    @ApiOperation(value = "删除设备",notes = "设备列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions(value = {PermissionConst._devices._device.delete})
    public PublicResult<Object> delete(@PathVariable Integer id) throws Exception{
        Boolean r=_deviceService.deleteById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @Log(description="设备管理:/批量删除设备")
    @ApiOperation(value = "批量删除设备",notes = "设备列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    @RequiresPermissions(value = {PermissionConst._devices._device.batch})
    public PublicResult<Object> batchdelete(@RequestBody List<Integer> ids) throws Exception{
        Boolean r=_deviceService.deleteBatchIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}

