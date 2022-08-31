package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	 private MemberService ms;
	 
	 @Autowired
	 private PasswordEncoder pwEnc; // WebSecurityConfig.java의 passwordEncoder
	
	@GetMapping("/joinMember")
	public String joinMemberPage() {
		return "joinMember";
	}
	@PostMapping("/joinMember")
	public String joinMember(Member member) {
		
		log.debug("[MemeberController] joinMember : {}", member);
		
		String encodededPassword = pwEnc.encode(member.getMemberpw()); // 비밀번호 암호화
		member.setMemberpw(encodededPassword); // member의 비밀번호에 암호화된 비밀번호 대입
		
		int result = ms.joinMember(member);
		
		if(result == 0) {
			log.debug("저장 실패");
		}
		
		return "home";
	}
	
	@GetMapping("/joinMember/idCheck")
	public String idCheckPage() {
		return "/memberView/idCheck";
	}
	
	@PostMapping("/joinMember/idCheck")
	public String idCheck(Model model, String idCheck) {
		log.debug("[MemberController] idCheck - parameter : {}", idCheck);
		
			boolean result = ms.idCheck(idCheck); // parameter로 받은 값과 동일 값 유무 판단
		
		log.debug("[MemberController] idCheck - 중복 여부 :  {}", result);
		
			model.addAttribute("result", result); // 사용 가능 여부
			model.addAttribute("checkedId", idCheck); // 중복 여부 검색 완료된 ID
		
		return "/memberView/idCheck";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/memberView/loginForm";
		// template 폴더 아래 memberview 폴더 아래의 loginForm 파일을 return
	}
	
	/**
	 * 개인정보 수정 페이지로 이동
	 * @return View "/memberView/mypage"
	 */
	@GetMapping("/mypage")
	public String mypageForm(Model model, @AuthenticationPrincipal UserDetails user) {
		String id = user.getUsername(); // 현재 로그인한 사용자의 ID
		log.debug("[MemberController] mypageForm - 현재 로그인한 사용자 ID : {}]", id);
		
		Member member = ms.getMemberInfo(id);
		
		model.addAttribute("member", member);
		
		return "/memberView/mypageForm";
	}
	/**
	 * 사용자가 입력한 정보를 DB에 update
	 * @return
	 */
	@PostMapping("/mypage")
	public String mypageUpdate(Member member, @AuthenticationPrincipal UserDetails user) {
		log.debug("[MemberController] mypageUpdate - parameter : {}", member);

		// 로그인한 ID를 읽어서 member객체에 추가
		member.setMemberid(user.getUsername());
		member.setMemberpw(pwEnc.encode(member.getPassword()));
		
		// 비밀번호 미입력 시 기존 비밀번호 대입
		if(member.getPassword().length() == 0) {
			member.setMemberpw(member.getPassword());
		}
		
		// member 객체를 서비스로 전달해 DB에 update
		int result = ms.updateInfo(member);
		
		if(result == 0) {
			log.debug("[MemberController] mypageUpdate - update 실패");
		}
		
		log.debug("[MemberController] mypageUpdate - update 성공");
		
		return "/home";
	}
	
	@GetMapping("/exit")
	public String exit() {
		return "redirect:/home";
	}
}
