package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("weather")
@Controller
public class WeatherController {
/**
 * 날씨 api를 우선 가져와서 뿌려보는 목록(하는 중)
 * @return
 */
	
	@GetMapping({"list"})
	public String write() {
		return "/weather/weather";
	}
}
