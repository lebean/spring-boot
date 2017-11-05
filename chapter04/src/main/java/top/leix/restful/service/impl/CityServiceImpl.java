package top.leix.restful.service.impl;

import top.leix.restful.model.City;
import top.leix.restful.model.Region;
import top.leix.restful.repository.CityRepository;
import top.leix.restful.repository.RegionRepository;
import top.leix.restful.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Name: CityServiceImpl
 * Description:
 * Author:leix
 * Time: 2017/3/28 14:02
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegionRepository regionRepository;
    @Override
    public City findById(int id) {
        return cityRepository.findById(id);
    }

    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }
}
