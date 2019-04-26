package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CityMapper;
import com.example.demo.model.City;

@Service
public class CityService {

	@Autowired
	private CityMapper cityMapper;

	public City findById(Integer id) {
		return cityMapper.findById(id);
	}
}
