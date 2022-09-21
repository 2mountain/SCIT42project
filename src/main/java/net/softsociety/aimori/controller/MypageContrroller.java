package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("mypage")
@Controller
public class MypageContrroller {

	@GetMapping("main")
	public String mypageMain() {
		log.debug("성공");
		return "mypageView/mypageMain";
	}
	
	@GetMapping("/petPage")
	public String petPage() {
		log.debug("성공");
		return "mypageView/petPage";
	}
}
