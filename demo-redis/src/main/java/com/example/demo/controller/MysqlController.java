package com.example.demo.controller;

import java.lang.Character.UnicodeBlock;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.City;
import com.example.demo.service.CityService;

@RestController
@RequestMapping("mysql")
public class MysqlController {

	@Autowired
	private CityService cityService;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@GetMapping("selectDB")
	
	/**
	 * 直接查数据库
	 * @param id
	 * @return
	 */
	public String selectDB(Integer id,HttpServletRequest request) {
		log.info("查询city:id=" + id);
		//return cityService.findById(id).toString();
		return cityService.getCity(id).toString();
	}

	@GetMapping("select")
	public String select(Integer id) {
		log.info("查询city:id=" + id);
		//return cityService.findById(id).toString();
		return cityService.findByIdWithCache(id).toString();
	}
	@GetMapping("create")
	public String create(String name,String areaCode) {
		City city = new City();
		city.setAreaCode(areaCode);
		city.setCreateTime(new Date());
		city.setName(name);;
		cityService.createCity(city);
		return "插入主键id:" + city.getId();
	}
	
	@GetMapping("delete")
	public String delete(Integer id) {
		log.info("删除city:id=" + id);
		cityService.deleteCity(id);
		return "删除city:id=" + id;
	}
	
}
