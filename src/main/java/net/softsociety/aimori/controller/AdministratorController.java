package net.softsociety.aimori.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.ReportedBoard;
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

	// 게시판 첨부파일 업로드 경로
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	@GetMapping({"","/"})
	public String administrator(Model model, @AuthenticationPrincipal UserDetails user) {
		log.debug("[AdministratorController] administrator - param : {}", user);
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] administrator - currentUserRole : {}", currentUserRole);

		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] administrator - NOT ADMIN");
			return "관리자만 접근 가능";
		}

		// 회원 정보를 VIEW로 보냄
		List<Member> list = aService.getMemberList();
		log.debug("[AdministratorController] administrator - list : {}", list);

		// 현재 접속중인 계정의 ID를 VIEW로 보냄
		model.addAttribute("currentUserId", user.getUsername());
		// 모든 회원 정보를 VIEW로 보냄
		model.addAttribute("memberList", list);

		return "administrator/administrator";

	}

	// 관리자 - 회원관리 페이지
	@GetMapping("/adminMembers")
	public String adminMembers(Model model, @AuthenticationPrincipal UserDetails user) {
		log.debug("[AdministratorController] adminMembers - param : {}", user);
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] adminMembers - currentUserRole : {}", currentUserRole);

		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] adminMembers - NOT ADMIN");
			return "관리자만 접근 가능";
		}

		// 회원 정보를 VIEW로 보냄
		List<Member> list = aService.getMemberList();
		log.debug("[AdministratorController] adminMembers - list : {}", list);

		// 현재 접속중인 계정의 ID를 VIEW로 보냄
		model.addAttribute("currentUserId", user.getUsername());
		// 모든 회원 정보를 VIEW로 보냄
		model.addAttribute("memberList", list);
		// 전체 회원 수
		model.addAttribute("allMemberCount", list.size());
		
		int bu = 0;
		StringBuilder allAdminAccount = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMemberRoleName().equals("ROLE_ADMIN")) {
				allAdminAccount.append(list.get(i).getMemberId())
								.append(" / ");
			}
			if(list.get(i).isEnabled() == false) {
				bu++;
			}
		}
		allAdminAccount.deleteCharAt(allAdminAccount.length()-2);
		
		
		// 모든 관리자 계정
		model.addAttribute("allAdminAccount", allAdminAccount);
		model.addAttribute("countBUser", bu);

		return "administrator/adminMembers";
	}

	/**
	 * 신고 글 확인
	 * @param user
	 * @param model
	 * @return View (reported.html)
	 */
	@GetMapping("/reported")
	public String reported(@AuthenticationPrincipal UserDetails user, Model model) {
		log.debug("[AdministratorController] Administrator - param : {}", user);
		// 현재 접속한 계정의 role을 가져옴
		String currentUserRole = fService.checkRole(user.getUsername());
		log.debug("[AdministratorController] Administrator - currentUserRole : {}", currentUserRole);

		// 관리자 여부 확인
		if(!currentUserRole.equals("ROLE_ADMIN")) {
			log.debug("[AdministratorController] Administrator - NOT ADMIN");
			return "관리자만 접근 가능";
		}
		// 현재 접속중인 계정의 ID를 VIEW로 전달
		model.addAttribute("currentUserId", user.getUsername());

		/********************************************* 신고된 글 출력 *********************************************/
		List<ReportedBoard> list = aService.getReportedBoard();
		log.debug("[AdministratorController] getReportedBoard : {}", list);

		// 신고된 글 VIEW로 전달
		model.addAttribute("reportData", list);

		return "administrator/reported";
	}
}
