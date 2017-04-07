package leix.lebean.sweb.repository;

import leix.lebean.sweb.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Name:CityRepository
 * Description: 数据操作仓库CityRepository
 * Author:leix
 * Time: 2017/3/28 14:13
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    City findById(int id);
}
