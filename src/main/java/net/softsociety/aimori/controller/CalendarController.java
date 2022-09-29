package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("mypage")
@Controller
public class CalendarController {
	// 캘린더 페이지로 이동
	@GetMapping("/calendar")
	public String calendar() {
		
		return "/mypageView/calendar";
	}
}
