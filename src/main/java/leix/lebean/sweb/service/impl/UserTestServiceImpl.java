package leix.lebean.sweb.service.impl;

import java.util.List;

import leix.lebean.sweb.domain.UserTest;
import leix.lebean.sweb.mapper.second.UserTestMapper;
import leix.lebean.sweb.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTestServiceImpl implements UserTestService {

	@Autowired
	private UserTestMapper userTestMapper;
	
	@Override
	@Transactional(value = "secondTxMan", readOnly = true)
	public List<UserTest> getAllUsers() {
		return this.userTestMapper.getAllUsers();
	}

}
