package leix.lebean.sweb.controller;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leix.lebean.sweb.domain.City;
import leix.lebean.sweb.service.Sender;

@RestController
@Api(value = "用户服务",description = "与用户信息相关的服务接口")
public class UserController {

	@Autowired
	private Sender sender;
	
	@RequestMapping("/")
	public String home() throws Exception {
//		this.sender.send();
		
		City city = new City();
		city.setId(111L);
		city.setName("name-city");
		city.setState("sss");
		city.setCountry("country===");
		this.sender.send(city);
		return "home";
	}

	@GetMapping("users")
	@ApiOperation(value = "用户列表服务",notes = "返回所有用户列表")
	public List<String> listUser() {
		List<String> list = new ArrayList<String>();
		list.add("s1");
		list.add("s2");
		list.add("s3");
		list.add("s4");

		return list;
	}
}
