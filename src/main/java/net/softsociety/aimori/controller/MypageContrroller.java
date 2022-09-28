package net.softsociety.aimori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.service.MemberService;
import net.softsociety.aimori.util.FileService;

@Slf4j
@RequestMapping("mypage")
@Controller
public class MypageContrroller {
	
	@Autowired
	MemberService service;
	
	// 게시판 첨부파일 업로드 경로
		@Value("${spring.servlet.multipart.location}")
		String uploadPath;

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
	public String mypageList(@AuthenticationPrincipal UserDetails user, Model model, Member member, MultipartFile upload) {
		log.debug("수정할 정보 : {}", member);
		member.setMemberId(user.getUsername()); 
		
		// 첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅
				if (!upload.isEmpty()) {
					String savedfile = FileService.saveFile(upload, uploadPath);
					member.setMemberImageOriginalFile(upload.getOriginalFilename());
					member.setMemberImageSavedFile(savedfile);
				}
		
		int result = service.updateMember(member);
		return "redirect:/";
	}
	
}
