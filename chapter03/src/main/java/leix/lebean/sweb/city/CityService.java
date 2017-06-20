package leix.lebean.sweb.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityService implements ICityService {

    @Autowired
    ICityRepository cityRepository;

    @Override
    @Cacheable(value = "city")
    public City findById(int id) {
        return cityRepository.findById(id);
    }
}
