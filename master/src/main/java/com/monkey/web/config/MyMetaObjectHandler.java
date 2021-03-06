package com.monkey.web.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.monkey.common.util.DateUtil;
import com.monkey.core.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
/**
 * 自定义填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object creationTime = getFieldValByName("creationTime",metaObject);
      //  Object tenantId = getFieldValByName("tenantId",metaObject);
        if (null == creationTime) {
            this.setFieldValByName("creationTime",  LocalDateTime.now(), metaObject);//版本号3.0.6以及之前的版本
        }
        Object creatorUserId = metaObject.getValue("creatorUserId");
       //获取当前登录用户
        ServletRequestAttributes s=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(s!=null){
            HttpServletRequest req = s.getRequest();
            User user = (User) req.getAttribute("currentUser");
            if (null == creatorUserId &&user!=null) {
                this.setFieldValByName("creatorUserId",  user.getId(), metaObject);//版本号3.0.6以及之前的版本
            }
        }
    }
    @Override
    public boolean openUpdateFill() {
        return false;
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 关闭更新填充、这里不执行
    }
}