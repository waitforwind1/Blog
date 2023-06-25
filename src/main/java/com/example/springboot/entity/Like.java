package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("blog_like")
public class Like {
    @TableId(value = "blog_id",type= IdType.AUTO)
    private Integer bid;
    @TableField(value="user_id")
    private Integer uid;
    private Integer islike;
}
