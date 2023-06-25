package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Like;
import com.example.springboot.mapper.LikeMapper;
import com.example.springboot.service.ILikeService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper,Like> implements ILikeService {
    
    @Resource
    private ILikeService LikeService;


    @Override
    public void insert(Like like) {

    }
}
