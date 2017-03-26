package leix.lebean.sweb.service;

import java.util.List;

import leix.lebean.sweb.domain.City;

public interface CityService {

	List<City> findByState(String state);
}
