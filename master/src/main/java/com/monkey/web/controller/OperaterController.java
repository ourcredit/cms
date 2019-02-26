package com.monkey.web.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Device.IRegionService;
import com.monkey.application.OperationLogs.IErrorlogService;
import com.monkey.application.OperationLogs.IOperaterService;
import com.monkey.application.Payfor.ICustomerOrderService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.base.PermissionConst._watch._errorlog;
import com.monkey.common.util.ComUtil;
import com.monkey.common.util.DateTimeUtil;
import com.monkey.core.entity.*;
import com.monkey.web.annotation.Log;
import com.monkey.web.annotation.Pass;
import com.monkey.web.controller.dtos.OperaterInput;
import com.monkey.web.controller.dtos.RegionDto;
import com.monkey.web.controller.dtos.UserLoginInput;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/operater")
public class OperaterController {
    @Autowired
    IOperaterService _operaterService;
    @Autowired
    ICustomerOrderService _orderService;
    @Autowired
    IRegionService _regionService;
   

    @ApiOperation(value = "获取运维人员列表", notes = "运维人员")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public PublicResult<Page<Operater>> operaters(@RequestBody PagedAndFilterInputDto page) throws Exception {
        String name = "";
        if (page.where != null && page.where.get("name") != null) {
            name = page.where.get("name").toString();
        }
        Page<Operater> res = _operaterService.selectOperaterList(new Page<>(page.index, page.size), name);
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @Pass
    @ApiOperation(value = "获取需要处理的订单", notes = "运维人员")
    @RequestMapping(value = "/dealorders", method = RequestMethod.POST)
    public PublicResult<Page<CustomerOrder>> dealorders(@RequestBody PagedAndFilterInputDto page) throws Exception {
        Object o = page.where.get("operaterId");
        if (o == null) return new PublicResult<>(PublicResultConstant.FAILED, null);
        Page<CustomerOrder> res = _orderService.selectOrders(new Page<>(page.index, page.size), o.toString());
        res.getRecords().forEach(w -> {
            w.setTimeShow(DateTimeUtil.formatDateTimetoString(w.getCreationTime(), "yyyy年MM月dd日"));
        });
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @Pass
    @ApiOperation(value = "获取当前用户的订单", notes = "运维人员")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public PublicResult<Page<CustomerOrder>> orders(@RequestBody PagedAndFilterInputDto page) throws Exception {
        EntityWrapper<CustomerOrder> filter = new EntityWrapper<>();
        filter=  ComUtil.genderFilter(filter,page.where,true);
        Page<CustomerOrder> res = _orderService.selectPage(new Page<CustomerOrder>(page.index, page.size), filter);
        res.getRecords().forEach(w -> {
            w.setTimeShow(DateTimeUtil.formatDateTimetoString(w.getCreationTime(), "yyyy年MM月dd日"));
        });
        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取运维人员详情", notes = "运维人员")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PublicResult<Operater> Point(@PathVariable Integer id) throws Exception {
        Operater m = _operaterService.selectById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }

    @Log(description = "点位接口:/添加或编辑运维人员")
    @ApiOperation(value = "添加或编辑运维人员", notes = "运维人员")
    @RequestMapping(method = RequestMethod.PUT)
    public PublicResult<Boolean> insert(@RequestBody OperaterInput model) throws Exception {
        Operater o = new Operater();
        o.setAccount(model.account);
        o.setOpenId(model.openId);
        if (model.region!=null&& !model.region.isEmpty()) {
            o.setRegion(model.region);
        }
        o.setId(model.id);
        if (!model.password.isEmpty()) {
            String pass = BCrypt.hashpw(model.password, BCrypt.gensalt());
            o.setPassword(pass);
        }
        Boolean r = _operaterService.insertOrUpdate(o);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Log(description = "运维接口:/删除运维")
    @ApiOperation(value = "删除运维", notes = "运维人员")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public PublicResult<Object> delete(@PathVariable Integer id) throws Exception {
        Boolean r = _operaterService.deleteById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Pass
    @ApiOperation(value = "获取地区", notes = "运维人员")
    @RequestMapping(value = "/allregions", method = RequestMethod.POST)
    public PublicResult<List<RegionDto>> areas() {
        EntityWrapper e = new EntityWrapper();
        e.orderBy("code", true);
        List<Region> list = _regionService.selectList(e);
        RegionDto root = new RegionDto();
        root.setKey("");
        root.setExpand(true);
        root.setTitle("全国");
        root.setLevel(0);
        List<RegionDto> r = GenderList(1, list, "");
        root.setChildren(r);
        List<RegionDto> rr = new ArrayList<RegionDto>() {{
            add(root);
        }};

        return new PublicResult<List<RegionDto>>(PublicResultConstant.SUCCESS, rr);
    }

    private List<RegionDto> GenderList(Integer node, List<Region> items, String key) {
        List<RegionDto> result = new ArrayList<>();
        for (Region r : items
                ) {
            if (r.getLevel() == node) {

                RegionDto model = new RegionDto();
                model.setTitle(r.getName());
                model.setKey(r.getCode());
                model.setExpand(false);
                model.setLevel(r.getLevel());
                if (key == "" || r.getCode().substring(0, node * 2).startsWith(key)) {
                    List<RegionDto> childs = GenderList(r.getLevel() + 1, items, r.getCode().substring(0, node * 2));
                    model.setChildren(childs);
                    result.add(model);
                }
            }
        }
        return result;
    }

    @Log(description = "运维接口:/批量删除运维")
    @ApiOperation(value = "批量删除运维", notes = "运维人员")
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public PublicResult<Object> batchdelete(@RequestBody List<Integer> ids) throws Exception {
        Boolean r = _operaterService.deleteBatchIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

    @Pass
    @Log(description = "运维接口:/登陆")
    @ApiOperation(value = "运维人员登陆", notes = "运维人员")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public PublicResult<Object> login(@RequestBody UserLoginInput input) throws Exception {
        if (ComUtil.isEmpty(input.userName) || ComUtil.isEmpty(input.passWord)) {
            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, null);
        }
        EntityWrapper ew = new EntityWrapper<>();
        ew.eq("account", input.userName);
        Operater user = _operaterService.selectOne(ew);
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(input.passWord, user.getPassword())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }
        ew = new EntityWrapper<>();
        ew.eq("operaterId", user.getId());
        Integer count = _orderService.selectCount(ew);
        user.setOrderCount(count);
        return new PublicResult<>(PublicResultConstant.SUCCESS, user);
    }

    @Pass
    @ApiOperation(value = "获取地区", notes = "运维人员")
    @RequestMapping(value = "/area", method = RequestMethod.POST)
    public PublicResult<Object> getAreaList() {
        EntityWrapper e = new EntityWrapper();
        List<Region> list = _regionService.selectList(e);
        if (!list.isEmpty()) {
            Map<String, Object> maps = new HashMap<>();
            Map<String, String> a = new HashMap<>();
            Map<String, String> b = new HashMap<>();
            Map<String, String> c = new HashMap<>();
            Map<String, String> d = new HashMap<>();
            for (Region r :
                    list) {
                if (r.getLevel() == 1) {
                    a.put(r.getCode(), r.getName());
                } else if (r.getLevel() == 2) {
                    b.put(r.getCode(), r.getName());
                } else {
                    if(r.getEnable()==1){
                        c.put(r.getCode(), r.getName());
                        if(r.getPrice()!=null){
                          d.put(r.getCode(),r.getPrice().toString());
                        }
                    }
                  
                }
            }
            maps.put("province_list", a);
            maps.put("city_list", b);
            maps.put("county_list", c);
            maps.put("price_list", d);
            return new PublicResult<Object>(PublicResultConstant.SUCCESS, maps);
        }
        return new PublicResult<Object>(PublicResultConstant.NORESULTS, null);
    }

   
}
