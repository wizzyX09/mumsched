package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IScheduleServiceImpl implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public Schedule findById(Integer scheduleId) {
        return scheduleRepository.findOne(scheduleId);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public void delete(Integer scheduleId) {
        scheduleRepository.delete(scheduleId);
    }

    @Override
    public Schedule saveOrUpdate(Schedule schedule) {
      return  scheduleRepository.save(schedule);
    }
}
