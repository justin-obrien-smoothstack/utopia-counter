package com.ss.training.utopia.counter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
	public List<User> findByUsername(String username);
}