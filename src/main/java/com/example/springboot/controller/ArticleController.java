package com.example.springboot.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Collect;
import com.example.springboot.entity.Like;
import com.example.springboot.mapper.ArticleMapper;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.service.ICollectService;
import com.example.springboot.utils.TokenUtils;
import com.example.springboot.service.IArticleService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.entity.Article;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @Resource
    private CollectMapper collectMapper;
    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Article article) {
        if (article.getId() == null) { // 新增
            article.setTime(DateUtil.now());  // new Date()
            article.setUser(TokenUtils.getCurrentUser().getNickname());
        }
        articleService.saveOrUpdate(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        articleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return articleService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll(@RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(start)) {
            // where time >= start
            queryWrapper.ge("time", start);
        }
        if (StrUtil.isNotBlank(end)) {
            // where time <= end
            queryWrapper.le("time", end);
        }
        return Result.success(articleService.list(queryWrapper));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(articleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        System.out.println("aaaa");
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(articleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/collect/selectCollect")
    public Result selectCol(@RequestParam Integer user_id){
        return Result.success(articleService.selCollect(user_id));
    }

    @GetMapping("/collect/check/{id}")
    public Result checkcollect(@PathVariable("id") Integer id){
        return Result.success(articleService.checkCollection(id));
    }

    @PostMapping("/collect/addCollect")
    public Result addCollection(@RequestBody Collect collect){
        collect.setTime(DateUtil.now());
        articleService.addCollection(collect);
        return Result.success();
    }
    @GetMapping("/collect/cancleCol/{id}")
    public Result cancleCol(@PathVariable("id") int id){
        articleService.updateCol(id);
        articleService.remCol(id);
        return Result.success();
    }

    @GetMapping("/collect/page")
    public Result Page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        return Result.success(collectMapper.selectPage(new Page<>(pageNum, pageSize), null));
    }

    @GetMapping("/like/{id}")
    public Result checkLike(@PathVariable("id") Integer id){
        return Result.success(articleService.checkLike(id));
    }
    @PostMapping("/like/addLike")
    public Result addLike(@RequestBody Like like){
        return Result.success(articleService.addLike(like));
    }
    @GetMapping("/like/cancleLike/{id}")
    public Result cancleLike(@PathVariable("id") int id){
        articleService.updateLike(id);
        articleService.remLike(id);
        return Result.success();
    }

}

