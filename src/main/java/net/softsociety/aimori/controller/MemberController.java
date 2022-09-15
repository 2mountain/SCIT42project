package net.softsociety.aimori.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String signIn(Member member, Model model) {
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
	
	@GetMapping("/nNCheck")
	public String nNCheck() {
		return "/member/nNCheck";
	}
	
	@PostMapping("/nNCheck")
	public String nNCheck(String cNN, Model model) {
		log.debug("검색할 닉네임 : {}", cNN);
		
		boolean result = service.nNCheck(cNN);
		
		model.addAttribute("result", result);
		model.addAttribute("cNN", cNN);
		
		log.debug("사용가능 여부 : {}", result);
		
		return "/member/nNCheck";
	}
	
	@PostMapping("/member/email")
	private int sendEmail(HttpServletRequest request, String memberEmail) {
		log.debug("hsr : {}", request);
		HttpSession session = request.getSession();
		log.debug("이메일 : {}", memberEmail);
		service.mailSend(session, memberEmail);
		log.debug("이메일 : {}", memberEmail);
		return 123;
	}
	
	@PostMapping("/member/email/certification")
	private boolean emailCertification(HttpServletRequest request, String memberEmail, String inputCode) {
		HttpSession session = request.getSession();
		boolean result = service.emailCertification(session, memberEmail, Integer.parseInt(inputCode));
		
		return result;
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