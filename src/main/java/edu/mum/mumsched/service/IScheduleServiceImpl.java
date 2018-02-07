package edu.mum.mumsched.service;

import edu.mum.mumsched.exception.ScheduleLockException;
import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class IScheduleServiceImpl implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private IEntryService iEntryService;

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
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Schedule schedule) throws RuntimeException{
        if (isScheduleLockOnUpdate(schedule)) {
            throw new ScheduleLockException();
        }
        Entry entry = schedule.getEntry();
        entry.setSchedule(null);
        iEntryService.save(entry);
        scheduleRepository.delete(schedule.getId());
    }

    public boolean isScheduleLockOnUpdate(Schedule schedule) {
        for (Section section : schedule.getSections()) {
            if (section.getEnrolled() > 0)
                return true;
        }
        return false;
    }
}
