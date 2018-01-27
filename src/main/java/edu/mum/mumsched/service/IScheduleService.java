package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Schedule;

import java.util.List;

public interface IScheduleService {
    public Schedule findById(Integer scheduleId);
    public List<Schedule> findAll();
    public void delete(Integer scheduleId);
    public Schedule saveOrUpdate(Schedule schedule);
}