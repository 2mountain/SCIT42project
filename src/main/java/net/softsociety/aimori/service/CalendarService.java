package net.softsociety.aimori.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softsociety.aimori.dao.CalendarDAO;
import net.softsociety.aimori.domain.Calendar;


@Service
public class CalendarService {

	@Autowired
	CalendarDAO calendarDAO;
	
	// 일정 단일 조회
	public Calendar getCalendar(Calendar Calendar) throws Exception {
		return calendarDAO.getCalendar(Calendar);
	}
	
	// 일정 단일 조회(SEQ)
	public Calendar getCalendarSeq(Calendar Calendar) throws Exception {
		return calendarDAO.getCalendarSeq(Calendar);
	}
	
	// 일정 전체 조회
	public List<Calendar> getAll() throws Exception {
		return calendarDAO.getAll();
	}
	
	// 일정 추가
	public int add(Calendar Calendar) throws Exception {
		return calendarDAO.add(Calendar);
	}
	
	// 일정 수정
	public int update(Calendar Calendar) throws Exception {
		return calendarDAO.update(Calendar);
	}
	
	// 일정 삭제
	public int delete(Calendar Calendar) throws Exception {
		return calendarDAO.delete(Calendar);
	}
}