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
		User user;
		try {
			user = userDao.findByUsername(username);
		} catch (Throwable t) {
			return null;
		}
		return (user != null && "TRAVELER".equals(user.getRole()));
	}
	
	public User getUser(String username) {
		try {
			return userDao.findByUsername(username);
		} catch (Throwable t) {
			return null;
		}
	}

}
