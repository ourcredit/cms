package com.monkey.application.Channel;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkey.core.entity.Visit;
import com.monkey.core.mapper.VisitRepository;
import org.springframework.stereotype.Service;


@Service
public class VisitService extends ServiceImpl<VisitRepository, Visit> implements IVisitService {

}