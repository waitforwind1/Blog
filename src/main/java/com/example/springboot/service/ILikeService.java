package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Like;

public interface ILikeService extends IService<Like> {
    void insert(Like like);
}
