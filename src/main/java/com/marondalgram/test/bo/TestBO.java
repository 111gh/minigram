package com.marondalgram.test.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marondalgram.test.dao.TestDAO;

@Service
public class TestBO {
	@Autowired
	private TestDAO testDAO;
	
	public Map<String, Object> getUser() {
		return testDAO.getUser();
	}
}
