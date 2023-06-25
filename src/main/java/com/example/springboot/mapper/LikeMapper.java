package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Like;
import org.apache.ibatis.annotations.Update;

public interface LikeMapper extends BaseMapper<Like> {
    @Update("update blog_like set islike=0 where blog_id = #{id}")
    int updateLike(int id);

}
