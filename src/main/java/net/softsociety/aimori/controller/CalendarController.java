package net.softsociety.aimori.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Calendar;
import net.softsociety.aimori.service.CalendarService;

@Slf4j
@RequestMapping("mypage")
@Controller
public class CalendarController {
	
	/**
	 * 마이페이지 > 캘린더 진입
	 */
	@GetMapping("/calendar")
	public String calendar(@AuthenticationPrincipal UserDetails user, Model model) {
		
		return "/mypageView/calendar";
	}
	
	@Autowired
	CalendarService calendarService;
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	// 일정 호출
	@RequestMapping(value = "/cal", method = RequestMethod.GET)
	public String cal() {
		return "/mypageView/calendar";
	}
	
	// 일정 전체 조회
	@RequestMapping(value = "/calendar_getAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Calendar> calendar_getAll() throws Exception {
		logger.info("일정 전체 조회");
		
		List<Calendar> list = new ArrayList<Calendar>();
		list = calendarService.getAll();
		
		return list;
	}
	
	// 일정 단일 조회(1개)
	@RequestMapping(value = "/calendar_getCalendar", method = RequestMethod.POST)
	@ResponseBody
	public Calendar calendar_getCalendar(@RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
		logger.info("일정 단일 조회");
		
		Calendar calendar = new Calendar();
		calendar.setTitle(title);
		calendar.setStart_date(start);
		calendar.setEnd_date(end);
		
		return calendarService.getCalendar(calendar);
	}
	
	// 일정 단일 조회(1개 - SEQ)
	@RequestMapping(value = "/calendar_getCalendarSeq", method = RequestMethod.POST)
	@ResponseBody
	public Calendar calendar_getCalendarSeq(@RequestParam("seq") String seq) throws Exception {
		logger.info("일정 seq 조회");
		
		Calendar calendar = new Calendar();
		calendar.setSeq(Integer.parseInt(seq));
		
		return calendarService.getCalendarSeq(calendar);
	}
	
	// 일정 추가
	@RequestMapping(value = "/calendar_add", method = RequestMethod.POST)
	@ResponseBody
	public String calendar_add(@RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
		logger.info("일정 추가");
		
		Calendar Calendar = new Calendar();
		Calendar.setTitle(title);
		Calendar.setStart_date(start);
		Calendar.setEnd_date(end);
		
		int result = calendarService.add(Calendar);
		if(result > 0) {
			logger.info("insert success!");
			return "success";
		} else {
			logger.info("insert fail!");
			return "fail";
		}
	}
	
	// 일정 수정
	@RequestMapping(value = "/calendar_update", method = RequestMethod.POST)
	@ResponseBody
	public String calendar_update(@RequestParam("seq") String seq, @RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
		logger.info("일정 수정");
		
		Calendar Calendar = new Calendar();
		Calendar.setSeq(Integer.parseInt(seq));
		Calendar.setTitle(title);
		Calendar.setStart_date(start);
		Calendar.setEnd_date(end);
		
		int result = calendarService.update(Calendar);
		if(result > 0) {
			logger.info("update success!");
			return "success";
		} else {
			logger.info("update fail!");
			return "fail";
		}
	}
	
	// 일정 삭제
	@RequestMapping(value = "/calendar_delete", method = RequestMethod.POST)
	@ResponseBody
	public String calendar_delete(@RequestParam("seq") String seq) throws Exception {
		logger.info("일정 삭제");
		
		Calendar Calendar = new Calendar();
		Calendar.setSeq(Integer.parseInt(seq));
		
		int result = calendarService.delete(Calendar);
		if(result > 0) {
			logger.info("delete success!");
			return "success";
		} else {
			logger.info("delete fail!");
			return "fail";
		}
	}
}
