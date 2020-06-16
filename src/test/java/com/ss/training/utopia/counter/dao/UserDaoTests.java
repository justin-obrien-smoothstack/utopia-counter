package com.ss.training.utopia.counter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@DataJpaTest
public class UserDaoTests {

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private UserDao userDao;

	@Test
	public void findByUsernameTest() {
		String thisUsername = "ThisUsername", otherUsername = "OtherUsername";
		User savedUser = new User(null, thisUsername, null, null, null),
				otherUser = new User(null, otherUsername, null, null, null), foundUser;
		testEntityManager.persist(savedUser);
		testEntityManager.persist(otherUser);
		testEntityManager.flush();
		foundUser = userDao.findByUsername(thisUsername);
		assertEquals(savedUser, foundUser);
	}

}
