package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@TableName("blog_article")
public class Article  {


      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private String content;

      private String user;

      private String time;


}
