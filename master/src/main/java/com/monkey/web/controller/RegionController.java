package com.monkey.web.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.monkey.application.Device.IRegionService;
import com.monkey.application.dtos.PagedAndFilterInputDto;
import com.monkey.common.base.PermissionConst;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.ComUtil;
import com.monkey.core.entity.Region;
import com.monkey.web.annotation.Pass;
import com.monkey.web.controller.dtos.RegionListDto;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@RestController
@RequestMapping("api/region")
public class RegionController {
    @Autowired
    IRegionService _regionService;

    @Pass
    @ApiOperation(value = "获取区域列表", notes = "区域列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public PublicResult<Page<RegionListDto>> devices(@RequestBody PagedAndFilterInputDto page) throws Exception {
        EntityWrapper<Region> filter = new EntityWrapper<>();
        filter = ComUtil.genderFilter(filter, page.where,false);
        filter.eq("level",3).orderBy("code",true);
        Page<Region> res = _regionService.selectPage(new Page<>(page.index, page.size), filter);
        filter = new EntityWrapper<>();
        filter.where("level={0}", 1).orNew("level={0}", 2);
        List<Region> r = _regionService.selectList(filter);
        Page<RegionListDto> result = new Page<>();
        List<RegionListDto> rr=new ArrayList<>();
        if (res.getTotal() != 0) {
            res.getRecords().forEach(w -> {
                RegionListDto mo = new RegionListDto();
                mo.setCountry(w.getName());
                mo.setCreationTime(w.getCreationTime());
                mo.setId(w.getId());
                mo.setCode(w.getCode());
                mo.setPrice(w.getPrice());
                mo.setEnable(w.getEnable());
                String l2 = w.getCode().substring(0, 4) ;
                String l1 = w.getCode().substring(0, 2) ;

                r.forEach(e -> {
                    if (e.getCode().startsWith(l2)  && e.getLevel() == 2) {
                        mo.setCity(e.getName());
                    }
                    if (e.getCode().startsWith(l1) && e.getLevel() == 1) {
                        mo.setProvence(e.getName());
                    }
                });
                rr.add(mo);
            });
        }
        result.setRecords(rr);
        result.setTotal(res.getTotal());
        return new PublicResult<Page<RegionListDto>>(PublicResultConstant.SUCCESS, result);
    }
    @Pass
    @ApiOperation(value = "获取商品详情", notes = "区域列表")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PublicResult<Region> Product(@PathVariable Integer id) throws Exception {
        Region m = _regionService.selectById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @Pass
    @ApiOperation(value = "编辑region", notes = "区域列表")
    @RequestMapping(method = RequestMethod.PUT)
    public PublicResult<Object> insert(@RequestBody Region model) throws Exception {
        model.setPrice(model.getPrice() * 100);
        Boolean r = _regionService.insertOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @ApiOperation(value = "批量启用/禁用设备",notes = "设备列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    public PublicResult<Object> batchdelete(@RequestBody List<Integer> ids) throws Exception{
      List<Region> rr=  _regionService.selectBatchIds(ids);
        rr.forEach(w->{
          Integer state=  w.getEnable()==1?0:1;
          w.setEnable(state);
        });
        Boolean r=_regionService.updateBatchById(rr);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
}

