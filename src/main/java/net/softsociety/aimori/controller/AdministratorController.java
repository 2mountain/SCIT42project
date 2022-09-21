package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("administrator")
@Controller
public class AdministratorController {
	@GetMapping({"","/"})
	public String Administrator() {
		// return "administrator/administrator";
		return "administrator/administrator";
	}
	
}
