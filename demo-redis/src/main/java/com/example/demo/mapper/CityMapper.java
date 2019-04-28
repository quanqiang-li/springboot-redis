package com.example.demo.mapper;

import com.example.demo.domain.City;

public interface CityMapper {
	/**
	 * 查询city
	 * 
	 * @param id
	 * @return
	 */
	City getCityById(Integer id);

	/**
	 * 创建city并返回主键city.getId
	 * 
	 * @param city
	 * @return
	 */
	int createCityAndGetId(City city);
}
