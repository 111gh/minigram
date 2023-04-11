package com.marondalgram.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marondalgram.test.bo.TestBO;

@Controller
public class TestController {
	@Autowired
	private TestBO testBO;
	
	@ResponseBody
	@RequestMapping("/test2")
	public String test() {
		return "Hello World";
	}
	
	@RequestMapping("/test_db2")
	@ResponseBody
	public Map<String, Object> testDb() {
		Map<String, Object> result = testBO.getUser();
		
		return result;
	}
	
	@RequestMapping("/test_jsp2")
	public String testJsp() {
		return "test/test";
	}
}
