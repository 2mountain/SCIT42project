package net.softsociety.aimori.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.service.BoardService;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	BoardService service;
	
	@GetMapping({"/", ""})
	public String home(Model model) {
		log.debug("[home]");
		

		ArrayList<Board> boardlist = service.boardMainList();

		model.addAttribute("boardlist", boardlist);

		log.debug("boardlist 출력! : {}", boardlist);
		
		return "home";
	}
}

