package top.leix.restful.service;

import top.leix.restful.model.City;
import top.leix.restful.model.Region;

import java.util.List;

/**
 * Name: CityService
 * Description:
 * Author: leix
 * Time: 2017/3/28 14:02
 */
public interface CityService {
    City findById(int id);
    List<Region> findAllRegions();
}
