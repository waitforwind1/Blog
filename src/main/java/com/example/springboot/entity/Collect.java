package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@TableName("blog_collect")
public class Collect {
    @TableId(value="blog_id",type=IdType.AUTO)
    private Integer blogId;
    @TableField(value="user_id")
    private Integer userId;
    @TableField("create_time")
    private String time;
    private String blogname;
    private String username;
    @TableField("is_collect")
    private int iscollect;


}
