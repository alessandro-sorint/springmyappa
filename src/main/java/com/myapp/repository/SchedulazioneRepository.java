package com.myapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myapp.entity.Schedulazione;

@Transactional
public interface SchedulazioneRepository extends JpaRepository<Schedulazione, Integer> {
	@Modifying
	@Query("select s FROM Schedulazione s WHERE userid=(:userid)")
	List<Schedulazione> findByUserid(@Param("userid") int userid);
	
	@Modifying
	@Query("DELETE FROM Schedulazione s WHERE userid=(:userid)")
	void deleteAllByUser(@Param("userid") int userid);
}
