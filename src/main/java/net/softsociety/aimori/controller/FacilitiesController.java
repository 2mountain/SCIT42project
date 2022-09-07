package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FacilitiesController {
	@GetMapping("/facilities")
	public String facilities() {
		log.debug("[facilities]");
		return "/facilities/facilities";
	}
}
