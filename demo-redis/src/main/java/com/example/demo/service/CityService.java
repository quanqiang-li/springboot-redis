package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.City;
import com.example.demo.mapper.CityMapper;

//在redis的key前缀,
@CacheConfig(cacheNames = "cityCache")

@Service
public class CityService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String cityKey = "city_cach:";

	@Autowired
	private RedisService redisService;

	@Autowired
	private CityMapper cityMapper;
	
	/**
	 * 直接查数据库
	 * @param id
	 * @return
	 */
	public City getCity(Integer id) {
		City city = cityMapper.getCityById(id);
		return city;
	}

	/**
	 * 自己写逻辑,使用redis作为缓存
	 * 
	 * @param id
	 * @return
	 */
	public City findById(Integer id) {
		Object object = redisService.get(cityKey + id);
		if (object == null) {
			City city = cityMapper.getCityById(id);
			redisService.set(cityKey + id, city);
			log.info("查询city{}来自mysql", id);
			return city;
		} else {
			log.info("查询city{}来自redis", id);
			return (City) object;
		}
	}

	/**
	 * 自动查询缓存
	 * 
	 * @param id
	 * @return
	 */
	// #p0 spring表达式,第一个参数,这里redis的key=cityCatch::{#id},unless排除不缓存,即null不缓存
	@Cacheable(key = "#p0", unless = "#result == null")
	public City findByIdWithCache(Integer id) {
		City city = cityMapper.getCityById(id);
		log.info("查询city{}来自mysql", id);
		return city;
	}

	/**
	 * 添加city,并放置到缓存中
	 * 
	 * @param city
	 * @return
	 */
	// 必须返回对象
	@CachePut(key = "#p0.id")
	public City createCity(City city) {
		cityMapper.createCityAndGetId(city);
		// 为了保证缓存的数据完整,从数据库查询最新的
		return cityMapper.getCityById(city.getId());
	}

	/**
	 * 删除对象
	 * 
	 * @param id
	 */
	// 删除缓存内容
	@CacheEvict(key = "#p0")
	public void deleteCity(Integer id) {
		cityMapper.deleteCityById(id);
	}
}
