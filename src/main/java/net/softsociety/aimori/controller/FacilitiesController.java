package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("facilities")
@Controller
public class FacilitiesController {
	
	@GetMapping({"", "/"})
	public String facilities(Model model) {
		String place = "애완동물";
		
		model.addAttribute("place", place);
		
		log.debug("[FacilitiesController] facilities.html");
		
		return "/facilities/facilities";
	}
	
	@PostMapping({"", "/"})
	public String placeSave(Model model, @RequestParam(name="place", defaultValue="애완동물") String place) {
		log.debug("[FacilitiesController] placeSave - parameter : {}", place);

		model.addAttribute("place", place);
		
		return "/facilities/facilities";
	}
}
