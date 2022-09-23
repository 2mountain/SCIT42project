package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.service.MemberService;

@Slf4j
@RequestMapping("mypage")
@Controller
public class PetpageController {
	
	@Autowired
	MemberService service;

	@GetMapping("/petPage")
	public String petPage(@AuthenticationPrincipal UserDetails user, Model model) {
		
		return "/mypageView/petPage";
	}
}
