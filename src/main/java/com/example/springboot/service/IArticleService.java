package com.example.springboot.service;

import com.example.springboot.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Collect;
import com.example.springboot.entity.Like;

import java.util.List;


public interface IArticleService extends IService<Article> {
    int addCollection(Collect collect);
    List<Collect> selCollect(Integer user_id);
    Collect checkCollection(Integer id);
    Like checkLike(Integer id);
    int addLike(Like like);

    int updateLike(int id);

    int  updateCol(int id);

    int remCol(int id);

    int remLike(int id);
}
