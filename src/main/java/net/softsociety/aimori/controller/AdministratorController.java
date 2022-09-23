package net.softsociety.aimori.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.service.AdministratorService;
import net.softsociety.aimori.service.FacilitiesService;

@Slf4j
@RequestMapping("administrator")
@Controller
public class AdministratorController {
	
	@Autowired
	AdministratorService aService;
	
	@Autowired
	FacilitiesService fService;
	
	@GetMapping({"","/"})
	public String Administrator(@AuthenticationPrincipal UserDetails user, Model model) {
		log.debug("[AdministratorController] Administrator - param : {}", user);
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] Administrator - currentUserRole : {}", currentUserRole);
		
		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] Administrator - NOT ADMIN");
			return "관리자만 접근 가능";
		}
		
		// 회원 정보를 VIEW로 보냄
		List<Member> list = aService.getMemberList();
		log.debug("[AdministratorController] list : {}", list);
		
		// 현재 접속중인 계정의 ID를 VIEW로 보냄
		model.addAttribute("currentUserId", user.getUsername());
		// 모든 회원 정보를 VIEW로 보냄
		model.addAttribute("memberList", list);
		
		return "administrator/administrator";
	}
	
	/**
	 * 해당 회원의 차단 여부 변경
	 * @param member
	 * @param user
	 * @return boolean
	 */
	@ResponseBody
	@PostMapping("/changeBlock") 
	public int changeBlock(Member member, 
							@AuthenticationPrincipal UserDetails user){
		log.debug("[AdministratorController] changeBlock - param : {}", member);
		
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] changeBlock - currentUserRole : {}", currentUserRole);

		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] changeBlock - NOT ADMIN");
			 return -1; 
		}
		
		// 회원의 차단 여부 변경
		int result = aService.changeBlock(member);
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/changeRole") 
	public int changeRole(Member member, 
							@AuthenticationPrincipal UserDetails user){
		log.debug("[AdministratorController] changeRole - param : {}", member);
		
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] changeRole - currentUserRole : {}", currentUserRole);

		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] changeRole - NOT ADMIN");
			 return -1; 
		}
		
		// 회원의 권한 변경
		int result = aService.changeRole(member);
	
		return result;
	}
}
