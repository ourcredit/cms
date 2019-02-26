package com.monkey.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.monkey.application.Device.ITreeService;
import com.monkey.common.base.Constant;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.core.entity.Tree;
import com.monkey.core.entity.User;
import com.monkey.web.annotation.CurrentUser;
import com.monkey.web.controller.dtos.TreeDtoInput;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-02
 */
@RestController
@RequestMapping("api/tree")
@RequiresPermissions(value = {})
public class TreeController {
    @Autowired
    ITreeService _treeService;
    @ApiOperation(value = "获取机构列表",notes = "机构列表")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public PublicResult<List<Tree>> Trees( @CurrentUser User current) throws Exception{
        QueryWrapper<Tree> filter = new QueryWrapper<>();
        Tree t;List<Tree> res;
        if(current.getAreaId()!=null){
             t=_treeService.getById(current.getAreaId());
            if(t!=null){
                filter.like("levelCode",t.getLevelCode());
            }
             res= _treeService.list( filter);
            res.forEach(c->{
                if(c.getId()==t.getId()){
                    c.setParentId(null  );
                }
            });
        }else {
            res= _treeService.list( filter);
        }
        Tree tt=new Tree("未分配设备",null, Constant.UnknownCode);
        tt.setId(999);
        res.add(tt);

        return new PublicResult<>(PublicResultConstant.SUCCESS, res);
    }
    @ApiOperation(value = "获取机构详情",notes = "机构列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PublicResult<Tree> Tree(@PathVariable Integer id) throws Exception{
        Tree m=_treeService.getById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, m);
    }
    @ApiOperation(value = "批量添加机构",notes = "机构列表")
    @RequestMapping(value = "batch",method = RequestMethod.PUT)
    public PublicResult<Object> mutiInsert(@RequestBody List<TreeDtoInput> model) throws Exception{
        _treeService.mutiInsertOrgs(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, true);
    }
    @ApiOperation(value = "添加或编辑机构",notes = "机构列表")
    @RequestMapping(method = RequestMethod.PUT)
    public PublicResult<Object> insert(@RequestBody Tree model) throws Exception{
        QueryWrapper ew=new QueryWrapper();
        String code=UUID.randomUUID().toString().split("-")[0];
        if(model.getLevelCode()==null||model.getLevelCode().isEmpty()){
            if(model.getParentId()!=null){
                ew.eq("id",model.getParentId());
             Tree parent=   _treeService.getOne(ew);
             if(parent!=null    ){
                 code=parent.getLevelCode()+"."+code;
             }
            }
            model.setLevelCode(code);
        }
        Boolean r=_treeService.saveOrUpdate(model);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @ApiOperation(value = "删除机构",notes = "机构列表")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public PublicResult<Object> delete(@PathVariable Integer id) throws Exception{
        QueryWrapper ew=new QueryWrapper();
        ew.eq("areaId",id);
        Boolean r=_treeService.removeById(id);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }
    @ApiOperation(value = "批量删除机构",notes = "机构列表")
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    public PublicResult<Object> batchdelete(@RequestBody List<Integer> ids) throws Exception{
        Boolean r=_treeService.removeByIds(ids);
        return new PublicResult<>(PublicResultConstant.SUCCESS, r);
    }

}

