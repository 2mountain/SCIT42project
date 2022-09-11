package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/facilities")
@Controller
public class FacilitiesController {
	
	@GetMapping("")
	public String facilities() {
		log.debug("[FacilitiesController] facilities.html");
		return "/facilities/facilities";
	}
}
