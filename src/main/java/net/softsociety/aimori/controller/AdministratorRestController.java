package net.softsociety.aimori.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.DateJoinNumber;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.PetTypeNNumber;
import net.softsociety.aimori.service.AdministratorService;
import net.softsociety.aimori.service.BoardService;
import net.softsociety.aimori.service.FacilitiesService;
import net.softsociety.aimori.service.QnaService;
import net.softsociety.aimori.util.FileService;

@Slf4j
@RequestMapping("administrator")
@Controller
public class AdministratorRestController {
	@Autowired
	AdministratorService aService;
	
	@Autowired
	FacilitiesService fService;
	
	@Autowired
	BoardService bService;
	
	@Autowired
	QnaService service;
	
	// 게시판 첨부파일 업로드 경로
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
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
	
	/**
	 * 관리자 페이지에서 게시글 삭제
	 * @param boardNumber
	 * @param memberId
	 * @return 
	 */
	@ResponseBody
	@PostMapping("/deleteBoard")
	public String deleteBoard(int boardNumber, String memberId) {
		log.debug("[AdministratorController] deleteBoard - param : {}, {}", boardNumber, memberId);
		
		// 해당 번호의 글 정보 조회
		Board board = bService.boardRead(boardNumber);
		log.debug("[AdministratorController] deleteBoard - board : {}", board);


		if (board == null) {
			return "삭제 실패";
		}

		// 첨부된 파일명 확인
		String savedfile = board.getBoardImageSaved();

		// 로그인 아이디를 board객체에 저장
		board.setMemberId(memberId);
		// board.setMemberId("test1");

		// 글 삭제
		int result = bService.boardDelete(board);
		log.debug("[AdministratorController] deleteBoard - result : {}", result);
		

		// 글 삭제 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		
		if(result == 1) {
			return "success";
		} else {
			return "failed";
		}
	}
	
	/**
	 * 관리자 페이지의 차트를 위한 일자와 가입자 수를 반환
	 * @return 일자, 가입자 수
	 */
	@ResponseBody
	@PostMapping("/getJoinNumber")
	public List<DateJoinNumber> getJoinNumber() {
		List<DateJoinNumber> list = aService.getJoinNumber();
		log.debug("[AdministratorController] getJoinNumber - list : {}", list);
		
		return list;
	}

	/**
	 * 회원들의 견종을 반환하는 메소드
	 * @return 회원들의 견종 List
	 */
	@ResponseBody
	@PostMapping("/getDogType")
	public List<PetTypeNNumber> getDogType() {
		List<PetTypeNNumber> list = aService.getDogType();
		log.debug("[AdministratorController] getDogType - list : {}", list);
		
		return list;
	}
}
