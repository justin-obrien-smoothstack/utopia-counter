package com.ss.training.utopia.counter.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
public class UserService {

	@Autowired
	UserDao userDao;

	public Boolean userIsTraveler(String username) {
		User user = userDao.findByUsername(username);
		return (user != null && "TRAVELER".equals(user.getRole()));
	}

	public User getUser(String username) {
		return userDao.findByUsername(username);
	}

}
