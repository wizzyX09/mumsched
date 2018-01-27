package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer>{

    List<Schedule> findAllByStatus(String status);

}
