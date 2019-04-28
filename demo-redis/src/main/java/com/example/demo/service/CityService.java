package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.City;
import com.example.demo.mapper.CityMapper;

@Service
public class CityService {

	@Autowired
	private CityMapper cityMapper;

	public City findById(Integer id) {
		return cityMapper.getCityById(id);
	}
	
	public Integer createCity(City city) {
		int result = cityMapper.createCityAndGetId(city);
		
		return result;
	}
}
