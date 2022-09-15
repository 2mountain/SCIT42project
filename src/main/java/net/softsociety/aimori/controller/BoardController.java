package net.softsociety.aimori.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
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
	public String write(Board board
			, @AuthenticationPrincipal UserDetails user
			, MultipartFile upload) {

		log.debug("저장할 글정보 : {}", board);
		log.debug("파일 업로드 경로: {}", uploadPath);
		log.debug("파일 정보: {}", upload);

//		board.setMemberId(user.getUsername());
		board.setMemberId("test1");
		board.setMemberNickName("testUser");

		// 첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅
		if (!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setBoardImageOriginal(upload.getOriginalFilename());
			board.setBoardImageSaved(savedfile);
		}
		
		log.debug("set 처리 후 board : {}", board);

		int result = boardDAO.insertBoard(board);
		
		return "redirect:/board/list";
	}
	
	/**
	 * 글읽기
	 * @param model 글객체
	 * @param boardNumber 글번호
	 * @return
	 */
	@GetMapping("read")
	public String read(
			Model model
			, @RequestParam(name="boardNumber", defaultValue = "0") int boardNumber) { 

		Board board = service.boardRead(boardNumber);
		if (board == null) {
			return "redirect:/board/list"; //글이 없으면 목록으로
		}
			
		//결과를 모델에 담아서 HTML에서 출력
		model.addAttribute("board", board);
		
		return "/board/boardRead";
	}
	
	/**
	 * 첨부파일 다운로드 
	 * @param boardNumber 본문 글번호
	 */
	@GetMapping("download")
	public String fileDownload(int boardNumber, Model model, HttpServletResponse response) {
		//전달된 글 번호로 글 정보 조회
		Board board = service.boardRead(boardNumber);
		
		//원래의 파일명
		String originalfile = new String(board.getBoardImageOriginal());
		
		try {
			response.setHeader("Content-Disposition", " attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//저장된 파일 경로
		String fullPath = uploadPath + "/" + board.getBoardImageSaved();
		
		//서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
		FileInputStream filein = null;
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			
			//Spring의 파일 관련 유틸 이용하여 출력
			FileCopyUtils.copy(filein, fileout);
			
			filein.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}
	
	/**
	 * 글수정 폼으로 이동
	 * @param boardNumber 수정할 글번호
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping("update")
	public String update(int boardNumber, Model model) {
		
		Board board = service.boardRead(boardNumber);
		model.addAttribute("board", board);
		
		return "/board/boardUpdateForm";
	}
	
	/**
	 * 글 수정 
	 * @param board 수정할 글내용
	 * @param user 인증정보
	 * @param upload 첨부파일 정보
	 */
	@PostMapping("update")
	public String update(
			Board board
			, @AuthenticationPrincipal UserDetails user
			, MultipartFile upload) {
		
		log.debug("저장할 글정보 : {}", board);
		log.debug("파일 정보: {}", upload);
		
		//작성자 아이디 추가
		/* board.setMemberId(user.getUsername()); */
		board.setMemberId("test1");

		
		Board oldBoard = null;
		String oldSavedfile = null;
		String savedfile = null;
		
		//첨부파일이 있는 경우 기존파일 삭제 후 새 파일 저장
		if (upload != null && !upload.isEmpty()) {
			oldBoard = service.boardRead(board.getBoardNumber());
			oldSavedfile = oldBoard == null ? null : oldBoard.getBoardImageSaved();
			
			savedfile = FileService.saveFile(upload, uploadPath);
			board.setBoardImageOriginal(upload.getOriginalFilename());
			board.setBoardImageSaved(savedfile);
			log.debug("새파일:{}, 구파일:{}", savedfile, oldSavedfile);
		}
		
		int result = service.boardUpdate(board);
		
		//글 수정 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + oldSavedfile);
		}
		
		return "redirect:/board/read?boardNumber=" + board.getBoardNumber();
	}
	
	/**
	 * 글 삭제
	 * @param boardNumber 삭제할 글 번호
	 * @param user 인증정보
	 */
	@GetMapping ("delete")
	public String boardDelete(int boardNumber, @AuthenticationPrincipal UserDetails user) {
		
		//해당 번호의 글 정보 조회
		Board board = service.boardRead(boardNumber);
		
		if (board == null) {
			return "redirect:/board/list";
		}
		
		//첨부된 파일명 확인
		String savedfile = board.getBoardImageSaved();
		
		//로그인 아이디를 board객체에 저장
		// board.setMemberId(user.getUsername());
		board.setMemberId("test1");
		
		//글 삭제
		int result = service.boardDelete(board);
		
		//글 삭제 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		
		return "redirect:/board/list";
	}
	
	

}
