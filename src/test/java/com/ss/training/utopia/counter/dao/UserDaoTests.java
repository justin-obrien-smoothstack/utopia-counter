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
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTests {

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private UserDao userDao;

	@Test
	public void findByUsernameTest() {
		String testUsername = "TestUsername";
		User testUser = new User(null, testUsername, null, null, null);
		testEntityManager.persist(testUser);
		testEntityManager.flush();
		assertEquals(testUser, userDao.findByUsername(testUsername));
	}

}
