package leix.lebean.sweb.mapper.second;

import java.util.List;

import leix.lebean.sweb.sqlprovide.UserTestProvide;
import org.apache.ibatis.annotations.SelectProvider;

import leix.lebean.sweb.domain.UserTest;

public interface UserTestMapper {

	@SelectProvider(type = UserTestProvide.class, method = "getAllUsers")
	List<UserTest> getAllUsers();
}
