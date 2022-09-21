package net.softsociety.aimori.controller;

import java.lang.reflect.Member;

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
public class MypageContrroller {
	
	@Autowired
	MemberService service;

	/**
	 * 마이페이지 메인 진입
	 * @return
	 */
	@GetMapping("main")
	public String mypageMain(@AuthenticationPrincipal UserDetails user, Model model) {
		log.debug("UserDetails 정보 : {}", user);
		//Member member = service.getMemberInfo(user.getUsername());
		//model.addAttribute("member", member);
		return "/mypageView/mypageMain";
	}
	
	@GetMapping("/petPage")
	public String petPage() {
		log.debug("성공");
		return "mypageView/petPage";
	}
}
