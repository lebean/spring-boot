package leix.lebean.sweb.mapper.primary;

import java.util.List;

import leix.lebean.sweb.sqlprovide.CitySqlProvide;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import leix.lebean.sweb.domain.City;

public interface CityMapper {

	@SelectProvider(type = CitySqlProvide.class, method = "findByState")
	List<City> findByState(@Param("state") String state);
}
