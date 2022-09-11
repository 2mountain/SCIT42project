package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {
	
	@GetMapping("/signIn")
	public String signIn() {
		log.debug("signin");
		System.out.println("sdfasdfsd");
		return "member/signIn";
	}

}