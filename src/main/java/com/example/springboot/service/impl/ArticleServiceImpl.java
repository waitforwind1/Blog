package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.Collect;
import com.example.springboot.entity.Like;
import com.example.springboot.mapper.ArticleMapper;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.mapper.LikeMapper;
import com.example.springboot.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private CollectMapper collectMapper;

    @Resource
    private LikeMapper likeMapper;

    @Override
    public int addCollection(Collect collect) {
        return collectMapper.insert(collect);
    }

    @Override
    public List<Collect> selCollect(Integer user_id) {
        QueryWrapper<Collect> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id);
        return collectMapper.selectList(queryWrapper);
    }

    @Override
    public Collect checkCollection(Integer id) {
        return collectMapper.selectById(id);
    }

    @Override
    public Like checkLike(Integer id) {
        return likeMapper.selectById(id);
    }

    @Override
    public int addLike(Like like) {
        like.setIslike(1);
        return likeMapper.insert(like);
    }

    @Override
    public int updateLike(int id) {
        return likeMapper.updateLike(id);
    }

    @Override
    public int updateCol(int id) {
        return collectMapper.updateCol(id);
    }

    @Override
    public int remCol(int id) {
        return collectMapper.deleteById(id);
    }

    @Override
    public int remLike(int id) {
        return likeMapper.deleteById(id);
    }
}
