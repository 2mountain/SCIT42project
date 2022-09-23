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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.BoardLiked;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.service.BoardService;
import net.softsociety.aimori.util.FileService;
import net.softsociety.aimori.util.PageNavigator;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {

	// 게시판 목록의 페이지당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	// 게시판 목록의 페이지 이동 링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	// 게시판 첨부파일 업로드 경로
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	@Autowired
	BoardService service;

	/**
	 * 커뮤니티 메인 화면
	 * 
	 * @return board.html
	 */
	@GetMapping({ "", "/" })
	public String board() {
		log.debug("[Open Board mainScreen]");
		return "/board/board";
	}

	/**
	 * 커뮤니티 글목록
	 * 
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	@GetMapping("list")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "1") int page, String type,
			String searchWord) {

		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);

		ArrayList<Board> boardlist = service.list(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);

		log.debug("searchWord 출력! :{}", searchWord);
		log.debug("boardlist 출력! : {}", boardlist);

		return "/board/board";
	}
	
	/**
	 * 인기글 출력
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	@GetMapping("hotList")
	public String hotList(Model model, @RequestParam(name = "page", defaultValue = "1") int page, String type,
			String searchWord) {

		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);

		ArrayList<Board> boardlist = service.hotList(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);

		log.debug("searchWord 출력! :{}", searchWord);

		return "/board/boardHot";
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

		log.debug("저장할 글정보 : {}", board);
		log.debug("파일 업로드 경로: {}", uploadPath);
		log.debug("파일 정보: {}", upload);

		board.setMemberId(user.getUsername());
		
		board.setMemberNickName(board.getMemberNickName());
//		board.setMemberId("test1");
		board.setMemberNickName("testUser");

		// 첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅
		if (!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setBoardImageOriginal(upload.getOriginalFilename());
			board.setBoardImageSaved(savedfile);
		}

		log.debug("set 처리 후 board : {}", board);

		int result = service.boardInsert(board);

		return "redirect:/board/list";
	}

	/**
	 * 글 읽기, 댓글 목록 불러오기
	 * 
	 * @param model       글객체
	 * @param boardNumber 글번호
	 * @return
	 */
	@GetMapping("read")
	public String read(Model model
			, @RequestParam(name = "boardNumber", defaultValue = "0") int boardNumber
			, @AuthenticationPrincipal UserDetails user) {

		
		
		// 현재 로그인 중인 회원 아이디 조회
		// String id = user.getUsername();
		
		// 게시글 객체 하나 조회
		Board board = service.boardRead(boardNumber);
		
		if (board == null) {
			return "redirect:/board/list"; // 글이 없으면 목록으로
		}
		
		String id = board.getMemberId();
		
		if(id != null) {
			
			if(user == null) {
				
				System.out.println("user null 가능");
				
			}
			
			// 해당 게시글에 대한 좋아요 개수
			int boardliked = service.boardSelectRecommend(boardNumber);
			
			
			// 현재 로그인 중인 회원이 해당 글에 좋아요 했는지 여부
			BoardLiked boardLiked = new BoardLiked(boardNumber, id);
			
			BoardLiked boardLiked2 = service.getBoardLiked(boardLiked);
			
			if(boardLiked2 != null){
				model.addAttribute("ifLiked", boardLiked2);
			}else{
				model.addAttribute("ifLiked", new BoardLiked());
			}
			
			log.debug("좋아요 여부 :{}", boardLiked2 );
			
			log.debug("boardliked 값: {}", boardliked);
			
			model.addAttribute("boardLikedData", boardliked);
		
		}
		
		// 현재 글에 달린 댓글들
		ArrayList<Reply> replylist = service.replyList(boardNumber);
		
		// 결과를 모델에 담아서 HTML에서 출력
		model.addAttribute("board", board);
		model.addAttribute("replylist", replylist);

		log.debug("board 읽어오기, {}", board);
		log.debug("reply 값 읽어오기, {}", replylist);

		return "/board/boardRead";
	}

	/**
	 * 첨부파일 다운로드
	 * 
	 * @param boardNumber 본문 글번호
	 */
	@GetMapping("download")
	public String fileDownload(int boardNumber, Model model, HttpServletResponse response) {
		// 전달된 글 번호로 글 정보 조회
		Board board = service.boardRead(boardNumber);

		// 원래의 파일명
		String originalfile = new String(board.getBoardImageOriginal());

		try {
			response.setHeader("Content-Disposition",
					" attachment;filename=" + URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 저장된 파일 경로
		String fullPath = uploadPath + "/" + board.getBoardImageSaved();

		// 서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
		FileInputStream filein = null;
		ServletOutputStream fileout = null;

		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();

			// Spring의 파일 관련 유틸 이용하여 출력
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
	 * 
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
	 * 
	 * @param board  수정할 글내용
	 * @param user   인증정보
	 * @param upload 첨부파일 정보
	 */
	@PostMapping("update")
	public String update(Board board, @AuthenticationPrincipal UserDetails user, MultipartFile upload) {

		log.debug("저장할 글정보 : {}", board);
		log.debug("파일 정보: {}", upload);

		// 작성자 아이디 추가
		board.setMemberId(user.getUsername()); 
//		board.setMemberId("test1");

		Board oldBoard = null;
		String oldSavedfile = null;
		String savedfile = null;

		// 첨부파일이 있는 경우 기존파일 삭제 후 새 파일 저장
		if (upload != null && !upload.isEmpty()) {
			oldBoard = service.boardRead(board.getBoardNumber());
			oldSavedfile = oldBoard == null ? null : oldBoard.getBoardImageSaved();

			savedfile = FileService.saveFile(upload, uploadPath);
			board.setBoardImageOriginal(upload.getOriginalFilename());
			board.setBoardImageSaved(savedfile);
			log.debug("새파일:{}, 구파일:{}", savedfile, oldSavedfile);
		}

		int result = service.boardUpdate(board);

		// 글 수정 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + oldSavedfile);
		}

		return "redirect:/board/read?boardNumber=" + board.getBoardNumber();
	}

	/**
	 * 글 삭제
	 * 
	 * @param boardNumber 삭제할 글 번호
	 * @param user        인증정보
	 */
	@GetMapping("delete")
	public String boardDelete(int boardNumber, @AuthenticationPrincipal UserDetails user) {

		// 해당 번호의 글 정보 조회
		Board board = service.boardRead(boardNumber);

		if (board == null) {
			return "redirect:/board/list";
		}

		// 첨부된 파일명 확인
		String savedfile = board.getBoardImageSaved();

		// 로그인 아이디를 board객체에 저장
		board.setMemberId(user.getUsername());
		// board.setMemberId("test1");

		// 글 삭제
		int result = service.boardDelete(board);

		// 글 삭제 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
		}
		
		return "redirect:/board/list";
	}

	/**
	 * 게시글 좋아요 누르기
	 * 
	 * @param board       불러온 게시글
	 * @param boardNumber 불러온 게시글의 글번호
	 * @param user        사용자 계정
	 * @return
	 */
	@ResponseBody
	@GetMapping("recommend")
	public int boardRecommend(int boardNumber, @AuthenticationPrincipal UserDetails user) {
		
		int isLiked = 0;
		
		String id = user.getUsername();
		
		log.debug("recommend 호출됨!!!");
		
		// 받아온 글번호와 로그인된 아이디가 담긴 boardLiked 객체를 만든다
		BoardLiked boardLiked = new BoardLiked(boardNumber, id);
		
		// 객체를 얻어온다
		BoardLiked boardLiked2 = service.getBoardLiked(boardLiked);
		
		log.debug("새롭게 얻은 boardLiked:{}", boardLiked2);
		
		// 좋아요가 되어 있지 않다면(객체가 null)이라면
		if (boardLiked2 == null) {
			// insert
			service.boardRecommend(boardLiked);
			isLiked = 1;
			log.debug("like 처리 완료");
		} else {
			// 좋아요가 되어 있다면 delete
			service.deleteRecommend(boardLiked2.getBoardLikedNumber());
			isLiked = 0;
			log.debug("like 취소");
		}
		
		return isLiked;

	}
	
	// 댓글 저장
	@PostMapping("replyInsert")
	public String replyInsert(
		Reply reply
		, @AuthenticationPrincipal UserDetails user) {
		
		reply.setMemberId(user.getUsername());
		// reply.setMemberId("test1");
		reply.setMemberNickName("testUser");
		
		log.debug("저장할 리플 정보 : {}", reply);
		int result = service.replyInsert(reply);
		
		int replyPlus = service.replyPlus(reply.getBoardNumber());
		
		return "redirect:/board/read?boardNumber=" + reply.getBoardNumber();
	}
	
	/**
	 * 댓글 삭제
	 * @param reply 삭제할 댓글 객체
	 * @return 삭제 후 리다이렉트 페이지
	 */
	@GetMapping("replyDelete")
	public String replyDelete(
			Reply reply
			, @AuthenticationPrincipal UserDetails user) {
		
		reply.setMemberId(user.getUsername());
		// reply.setMemberId("test1");
		
		int result = service.replyDelete(reply);
		int replyMinus = service.replyMinus(reply.getBoardNumber());
		
		return "redirect:/board/read?boardNumber=" + reply.getBoardNumber();
	}
	
	/**
	 * 게시글 이미지 보기
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	@GetMapping("boardImage")
	public String boardImage(Model model, @RequestParam(name = "page", defaultValue = "1") int page, String type,
			String searchWord) {

		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);

		ArrayList<Board> boardlist = service.list(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);

		log.debug("searchWord 출력! : {}", searchWord);
		log.debug("boardlist 출력! : {}", boardlist);

		return "/board/boardImage";
	}
	

	
	
}
