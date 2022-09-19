package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("weather")
@Controller
public class WeatherController {
	/**
	 * 날씨 api를 우선 가져와서 뿌려보는 목록(하는 중)
	 * 
	 * @return
	 */

	@GetMapping("list")
	public String weatherList() {
		log.debug("성공");
		return "weatherView/weather";
	}

	@GetMapping("goOut")
	public String weatherGoOut(String weather, Model model) {
		log.debug("현재날씨 : {}", weather);
		
		 if(weather.equals("clear sky") || weather.equals("few clouds") || 
		    weather.equals("scattered clouds") || weather.equals("broken clouds")) { 
			 String place = "공원"; 
			 model.addAttribute("place",place); 
			 int radius = 3000; 
			 model.addAttribute("radius", radius);
		 } else {
			 String place = "애견카페"; 
			 model.addAttribute("place",place); 
			 int radius = 3000; 
			 model.addAttribute("radius", radius);
		 }
			
		log.debug("[FacilitiesController] facilities");
		return "weatherView/weatherFacilities";
	}

}
