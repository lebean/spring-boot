package leix.lebean.sweb.service.impl;

import leix.lebean.sweb.model.City;
import leix.lebean.sweb.repository.CityRepository;
import leix.lebean.sweb.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    public City findById(int id) {
        return cityRepository.findById(id);
    }
}
