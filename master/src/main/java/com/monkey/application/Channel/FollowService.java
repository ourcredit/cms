package com.monkey.application.Channel;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.core.entity.Follow;
import com.monkey.core.mapper.FollowRepository;
import org.springframework.stereotype.Service;


@Service
public class FollowService extends ServiceImpl<FollowRepository, Follow> implements IFollowService {

}