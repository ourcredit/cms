package com.monkey.application.Device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.monkey.core.entity.Tree;
import com.monkey.web.controller.dtos.TreeDtoInput;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-02
 */
public interface ITreeService extends IService<Tree> {
            void mutiInsertOrgs(List<TreeDtoInput> input);
}
