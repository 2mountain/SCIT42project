package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.service.MemberService;

@Slf4j
@RequestMapping("mypage")
@Controller
public class MypageContrroller {
	
	@Autowired
	MemberService service;

	/**
	 * 마이페이지 메인 진입
	 */
	@GetMapping("/main")
	public String mypageMain(@AuthenticationPrincipal UserDetails user, Model model) {
		log.debug("들어오기 성공, UserDetails 정보 : {}", user);
		Member member = service.getMemberInfo(user.getUsername());
		model.addAttribute("member", member);
		return "mypageView/mypageMain";
	}
	
	/**
	 * 마이페이지 정보 수정
	 */
	@PostMapping("/user")
	public String mypageList(@AuthenticationPrincipal UserDetails user, Model model, Member member) {
		log.debug("수정할 정보 : {}", member);
		member.setMemberId(user.getUsername()); 
		int result = service.updateMember(member);
		return "redirect:/";
	}
}
