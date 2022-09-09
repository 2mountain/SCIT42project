package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {
	
	@GetMapping({"/", ""})
	public String board() {
		log.debug("[Open Board mainScreen]");
		return "/board/board";
	}
}
