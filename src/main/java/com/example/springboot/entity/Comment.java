package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
  @TableName("blog_comment")
public class Comment  {


      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String content;

      private Integer userId;

      private String time;

      private Integer pid;  //parent-id :上级评论id

      private Integer originId;  //

      private Integer articleId;

      @TableField(exist = false)
      private String pNickname;  // 父节点的用户昵称

      @TableField(exist = false)
      private Integer pUserId;  // 父节点的用户id

      @TableField(exist = false)
      private String nickname;

      @TableField(exist = false)
      private String avatarUrl;

      @TableField(exist = false)
      private List<Comment> children;


}
