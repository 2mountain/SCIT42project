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
		
		log.debug("[FacilitiesController] facilities");
		
		return "facilities/facilities";
	}
	
	@PostMapping({"", "/"})
	public String placeSave(Model model, 
			@RequestParam(name="place", defaultValue="애완동물") String place,
			@RequestParam(name="radius", defaultValue="3000") int radius) {
		log.debug("[FacilitiesController] placeSave - parameter : {}", place);
		log.debug("[FacilitiesController] placeSave - parameter : {}", radius);

		model.addAttribute("place", place);
		model.addAttribute("radius", radius);
		
		return "facilities/facilities";
	}
}
