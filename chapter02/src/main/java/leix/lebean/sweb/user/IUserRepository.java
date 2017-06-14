package leix.lebean.sweb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Name:UserRepository
 * Description:
 * Author:leix
 * Time: 2017/6/12 10:31
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    User save(User user);
}
