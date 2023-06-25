package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Collect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
public interface CollectMapper extends BaseMapper<Collect> {
    @Update("update blog_collect set is_collect=0 where blog_id = #{id}")
    int updateCol(int id);
}
