package net.softsociety.aimori.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.BoardDAO;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.util.FileService;
import net.softsociety.aimori.util.PageNavigator;
import net.softsociety.aimori.service.BoardService;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {

	//게시판 목록의 페이지당 글 수
	@Value("${user.board.page}")
	int countPerPage;
	
	//게시판 목록의 페이지 이동 링크 수
	@Value("${user.board.group}")
	int pagePerGroup;
	
	// 게시판 첨부파일 업로드 경로
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	@Autowired
	BoardService service;

	@Autowired
	BoardDAO boardDAO;
	
	/**
	 * 커뮤니티 메인 화면
	 * 
	 * @return board.html
	 */
	@GetMapping({"", "/"})
	public String board() {
		log.debug("[Open Board mainScreen]");
		return "/board/board";
	}
	
	/**
	 * 커뮤니티 글목록
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	@GetMapping("list")
	public String list(Model model
		, @RequestParam(name = "page", defaultValue = "1") int page
		, String type
		, String searchWord) {
		
		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		ArrayList<Board> boardlist = service.list(navi, type, searchWord);
		
		log.debug("boardlist : {}", boardlist);
		
		model.addAttribute("navi", navi);
		model.addAttribute("boardlist", boardlist);
//		model.addAttribute("type", type);
//		model.addAttribute("searchWord", searchWord);
		
		return "/board/board";
	}

	/**
	 * 글쓰기 폼으로 이동
	 */
	@GetMapping("write")
	public String write() {
		return "/board/boardWriteForm";
	}

	/**
	 * 글 저장
	 * 
	 * @param board  사용자가 폼에 입력한 게시글 정보
	 * @param user   인증정보
	 * @param upload 첨부 파일
	 */
	@PostMapping("write")
	public String write(Board board, @AuthenticationPrincipal UserDetails user, MultipartFile upload) {

		log.debug("저장할 글정보 : {}", board); log.debug("파일 업로드 경로: {}", uploadPath);
		log.debug("파일 정보: {}", upload);

//		board.setMemberId(user.getUsername());
		board.setMemberId("aaa");
		board.setMemberNickName("aaa");

		// 첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅
		if (!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setBoardImageOriginal(upload.getOriginalFilename());
			board.setBoardImageSaved(savedfile);
		}
		
		log.debug("set 처리 후 board : {}", board);

		int result = boardDAO.insertBoard(board);
		return "redirect:/board";
	}

}
