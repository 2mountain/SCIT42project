package net.softsociety.aimori.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.softsociety.aimori.domain.Calendar;


@Repository // 현재 클래스를 dao bean으로 등록
public class CalendarDAO {

	@Autowired
	SqlSession SqlSession;
	
	// 일정 단일 조회
	public Calendar getCalendar(Calendar Calendar) throws Exception {
		return SqlSession.selectOne("getCalendar", Calendar);
	}
	
	// 일정 단일 조회(SEQ)
	public Calendar getCalendarSeq(Calendar Calendar) throws Exception {
		return SqlSession.selectOne("getCalendarSeq", Calendar);
	}
	
	// 일정 전체 조회
	public List<Calendar> getAll() throws Exception {
		return SqlSession.selectList("getAll");
	}
	
	// 일정 추가
	public int add(Calendar Calendar) throws Exception {
		return SqlSession.insert("add", Calendar);
	}
	
	// 일정 추가
	public int update(Calendar Calendar) throws Exception {
		return SqlSession.update("update", Calendar);
	}
	
	// 일정 삭제
	public int delete(Calendar Calendar) throws Exception {
		return SqlSession.delete("delete", Calendar);
	}
}
