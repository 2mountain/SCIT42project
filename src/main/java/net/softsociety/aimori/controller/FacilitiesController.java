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
		
		//이부분에다가 값을 바꿔주면 처음 띄울 때 값이 바뀐다!
		String place = "애완동물";
		
		model.addAttribute("place", place);
		
		log.debug("[FacilitiesController] facilities.html");
		
		return "/facilities/facilities";
	}
	
	@PostMapping({"", "/"})
	public String placeSave(Model model, 
			@RequestParam(name="place", defaultValue="애완동물") String place,
			@RequestParam(name="radius", defaultValue="3000") int radius) {
		log.debug("[FacilitiesController] placeSave - parameter : {}", place);

		model.addAttribute("place", place);
		
		return "/facilities/facilities";
	}
}
