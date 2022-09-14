package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.service.MemberService;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {
	
	@Autowired
	MemberService service;
	
	@GetMapping("/signIn")
	public String signIn() {

		return "/member/signIn";
	}
	
	@PostMapping("/signIn")
	public String signIn(Member member) {
		log.debug("전달된 객체 : {}", member);
		
		int result = service.signInMember(member);
		
		if(result == 0) {
			return "redirect:/signIn";
		}
		return "redirect:/";
	}
	
	@GetMapping("/idCheck")
	public String idcheck() {
		return "/member/idCheck";
	}
	
	@PostMapping("/idCheck")
	public String idCheck(String cId, Model model) {
		log.debug("검색할 ID : {}", cId);
		
		boolean result = service.idCheck(cId);	// 전달받은 ID의 사용가능 여부
		
		model.addAttribute("result", result);	// ID 사용가능 여부
		model.addAttribute("cId", cId);			// 검색한 ID
		
		log.debug("사용가능 여부 : {}", result);
		
		return "/member/idCheck";
	}
	
	@GetMapping("/logIn")
	public String logIn() {
		return "/member/logIn";
	}
	
	@GetMapping("/findId")
	public String findId() {
		return "/member/findId";
	}
	
	@GetMapping("/findPassword")
	public String findPassword() {
		return "/member/findPassword";
	}

}