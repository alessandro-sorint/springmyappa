package com.myapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myapp.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM user u WHERE username=(:username)")
	User findByUsername(@Param("username") String username);
	//@Query("SELECT u FROM user u WHERE internalid=(:internalid)")
	User findByInternalid(@Param("internalid")int internalid);
}
