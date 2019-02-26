package com.monkey.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.monkey.application.OperationLogs.IOperaterService;
import com.monkey.common.base.PublicResult;
import com.monkey.common.base.PublicResultConstant;
import com.monkey.common.util.QyChatHelper;
import com.monkey.core.entity.Operater;
import com.monkey.web.annotation.Log;
import com.monkey.web.annotation.Pass;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author liugh
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/api/qy")
public class QyChatController {
    @Autowired
    private IOperaterService _operaterService;
    @Pass
    @Log(description = "企业微信:/用户同步")
    @ApiOperation(value = "用户同步", notes = "企业微信")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PublicResult < Object > genderUser() throws Exception {
        List < JSONObject > ul = QyChatHelper.getUserList();
        if (ul != null && !ul.isEmpty()) {
            for (JSONObject u:
                ul) {
                EntityWrapper ew = new EntityWrapper();
                String userId = u.getString("userid").toLowerCase();
                ew.eq("account", userId);
                Operater o = _operaterService.selectOne(ew);
                if (o == null) {
                    o = new Operater();
                    o.setAccount(userId);
                    o.setOrderCount(0);
                    o.setName(u.getString("name"));
                    o.setPassword(BCrypt.hashpw("1234567",BCrypt.gensalt()));
                    o.setOpenId(userId);
                }else {
                    o.setName(u.getString("name"));
                }
                _operaterService.insertOrUpdate(o);

            }
        }
        return new PublicResult < Object > (PublicResultConstant.SUCCESS, null);
    }
}