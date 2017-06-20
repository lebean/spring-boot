package leix.lebean.sweb.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Name:CityRepository
 * Description:
 * Author:leix
 * Time: 2017/3/28 14:13
 */
@Repository
public interface ICityRepository extends JpaRepository<City, Integer> {
    City findById(int id);
}
