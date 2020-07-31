package com.myapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myapp.entity.Job;
import com.myapp.entity.User;

@Transactional
public interface JobRepository extends JpaRepository<Job, Long>{
	List<Job> findByUser(User user);
	Job findById(int id);
	List<Job> findByName(String name);
	//void deleteByUser(User user);
	
	@Modifying
	@Query("DELETE FROM Job j WHERE userid=(:userid)")
	void deleteByUserid(@Param("userid") int userid);
}
