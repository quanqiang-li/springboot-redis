package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.City;

@Mapper
public interface CityMapper {
	@Select("SELECT * FROM CITY WHERE id = #{id}")
	City findById(@Param("id") Integer id);
}
