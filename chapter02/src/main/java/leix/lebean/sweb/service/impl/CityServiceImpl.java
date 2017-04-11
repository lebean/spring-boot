package leix.lebean.sweb.service.impl;

import leix.lebean.sweb.model.City;
import leix.lebean.sweb.repository.CityRepository;
import leix.lebean.sweb.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    @Cacheable(value = "city")
    public City findById(int id) {
        return cityRepository.findById(id);
    }
}
