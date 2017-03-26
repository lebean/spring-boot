package leix.lebean.sweb.service.impl;

import java.util.List;

import leix.lebean.sweb.domain.City;
import leix.lebean.sweb.mapper.primary.CityMapper;
import leix.lebean.sweb.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;
	
	@Override
	@Transactional(value = "primaryTxMan", readOnly = true)
	public List<City> findByState(String state) {
		return this.cityMapper.findByState(state);
	}

}
