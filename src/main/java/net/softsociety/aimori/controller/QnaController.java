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
import net.softsociety.aimori.domain.Answer;
import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.service.QnaService;
import net.softsociety.aimori.util.FileService;
import net.softsociety.aimori.util.PageNavigator;

@Slf4j
@RequestMapping("qna")
@Controller
public class QnaController {

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
	QnaService service;

	/**
	 * 커뮤니티 메인 화면
	 * 
	 * @return board.html
	 */
	@GetMapping({ "", "/" })
	public String qna() {
		return "/qna/qna";
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

		ArrayList<Question> questionlist = service.list(navi, type, searchWord);

		model.addAttribute("navi", navi);
		model.addAttribute("questionlist", questionlist);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);

		log.debug("searchWord 출력! :{}", searchWord);
		log.debug("questionlist 출력! : {}", questionlist);

		return "/qna/qna";
	}

	/**
	 * 인기글 출력
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	/*
	 * @GetMapping("hotList") public String hotList(Model model, @RequestParam(name
	 * = "page", defaultValue = "1") int page, String type, String searchWord) {
	 * 
	 * PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage,
	 * page, type, searchWord);
	 * 
	 * ArrayList<Board> boardlist = service.hotList(navi, type, searchWord);
	 * 
	 * model.addAttribute("navi", navi); model.addAttribute("boardlist", boardlist);
	 * model.addAttribute("type", type); model.addAttribute("searchWord",
	 * searchWord);
	 * 
	 * log.debug("searchWord 출력! :{}", searchWord);
	 * 
	 * return "/board/boardHot"; }
	 */

	/**
	 * 글쓰기 폼으로 이동
	 */
	@GetMapping("write")
	public String write() {
		return "/qna/qnaWriteForm";
	}

	/**
	 * 글 저장
	 * 
	 * @param board  사용자가 폼에 입력한 게시글 정보
	 * @param user   인증정보
	 * @param upload 첨부 파일
	 */
	@PostMapping("write")
	public String write(Question question, @AuthenticationPrincipal UserDetails user, MultipartFile upload) {

		log.debug("저장할 글정보 : {}", question);
		log.debug("파일 업로드 경로: {}", uploadPath);
		log.debug("파일 정보: {}", upload);

		question.setMemberId(user.getUsername());

		question.setMemberNickName(question.getMemberNickName());
		question.setMemberNickName("testUser");

		// 첨부파일이 있는 경우 지정된 경로에 저장하고, 원본 파일명과 저장된 파일명을 Board객체에 세팅

		if (!upload.isEmpty()) { String savedfile = FileService.saveFile(upload,
				uploadPath); question.setQuestionImageOriginal(upload.getOriginalFilename());
				question.setQuestionImageSaved(savedfile); }

		log.debug("set 처리 후 question : {}", question);

		int result = service.questionInsert(question);

		return "redirect:/qna/list";
	}

	/**
	 * 글 읽기, 댓글 목록 불러오기
	 * 
	 * @param model       글객체
	 * @param boardNumber 글번호
	 * @return
	 */

	@GetMapping("read") public String read(Model model
			, @RequestParam(name = "questionNumber", defaultValue = "0") int questionNumber
			, @AuthenticationPrincipal UserDetails user) {



		// 현재 로그인 중인 회원 아이디 조회
		// String id = user.getUsername();

		// 게시글 객체 하나 조회
		Question question = service.questionRead(questionNumber);

		if (question == null) {
			return "redirect:/qna/list"; // 글이 없으면 목록으로 }
		}

		String id = question.getMemberId();

		if(id != null) {

			if(user == null) {

				System.out.println("user null 가능");

			}

			// 해당 게시글에 대한 좋아요 개수 int boardliked =
			// service.questionSelectRecommend(questionNumber);


			// 현재 로그인 중인 회원이 해당 글에 좋아요 했는지 여부 BoardLiked boardLiked = new
			/*BoardLiked(boardNumber, id);

			BoardLiked boardLiked2 = service.getBoardLiked(boardLiked);

			if(boardLiked2 != null){ model.addAttribute("ifLiked", boardLiked2); }else{
			model.addAttribute("ifLiked", new BoardLiked()); }

			log.debug("좋아요 여부 :{}", boardLiked2 );

			log.debug("boardliked 값: {}", boardliked);

			model.addAttribute("boardLikedData", boardliked);
			 */

		}

		// 현재 글에 달린 댓글들
		ArrayList<Answer> answerlist = service.answerList(questionNumber);

		// 결과를 모델에 담아서 HTML에서 출력
		model.addAttribute("question", question);
		model.addAttribute("answerlist", answerlist);

		log.debug("question 읽어오기, {}", question);
		log.debug("answerlist 값 읽어오기, {}", answerlist);

		return "/qna/qnaRead";
	}


	/**
	 * 첨부파일 다운로드
	 * 
	 * @param boardNumber 본문 글번호
	 */
	@GetMapping("download") public String fileDownload(int questionNumber, Model
			model, HttpServletResponse response) {

		// 전달된 글 번호로 글 정보 조회
		Question question = service.questionRead(questionNumber);

		// 원래의 파일명
		String originalfile = new String(question.getQuestionImageOriginal());

		try { response.setHeader("Content-Disposition",
				" attachment;filename=" + URLEncoder.encode(originalfile, "UTF-8")); }
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 저장된 파일 경로
		String fullPath = uploadPath + "/" + question.getQuestionImageSaved();

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

		return "redirect:/"; }


	/**
	 * 글수정 폼으로 이동
	 * 
	 * @param boardNumber 수정할 글번호
	 * @param user
	 * @param model
	 * @return
	 */
	
	  @GetMapping("update")
	  public String update(int questionNumber, Model model) {
	  
	  Question question = service.questionRead(questionNumber);
	  model.addAttribute("question", question);
	  
	  return "/qna/qnaUpdateForm"; }
	 

	/**
	 * 글 수정
	 * 
	 * @param board  수정할 글내용
	 * @param user   인증정보
	 * @param upload 첨부파일 정보
	 */
	  @PostMapping("update")
	  public String update(Question question, @AuthenticationPrincipal UserDetails user, MultipartFile upload) {

			log.debug("저장할 글정보 : {}", question);
			log.debug("파일 정보: {}", upload);

			// 작성자 아이디 추가
			question.setMemberId(user.getUsername()); 

			Question oldBoard = null;
			String oldSavedfile = null;
			String savedfile = null;

			// 첨부파일이 있는 경우 기존파일 삭제 후 새 파일 저장
			if (upload != null && !upload.isEmpty()) {
				oldBoard = service.questionRead(question.getQuestionNumber());
				oldSavedfile = oldBoard == null ? null : oldBoard.getQuestionImageSaved();

				savedfile = FileService.saveFile(upload, uploadPath);
				question.setQuestionImageOriginal(upload.getOriginalFilename());
				question.setQuestionImageSaved(savedfile);
				log.debug("새파일:{}, 구파일:{}", savedfile, oldSavedfile);
			}

			int result = service.questionUpdate(question);

			// 글 수정 성공 and 첨부된 파일이 있는 경우 파일도 삭제
			if (result == 1 && savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + oldSavedfile);
			}

			return "redirect:/qna/read?questionNumber=" + question.getQuestionNumber();
		}

	/**
	 * 글 삭제
	 * 
	 * @param boardNumber 삭제할 글 번호
	 * @param user        인증정보
	 */
	  @GetMapping("delete")
		public String questionDelete(int questionNumber, @AuthenticationPrincipal UserDetails user) {

			// 해당 번호의 글 정보 조회
		  	Question question = service.questionRead(questionNumber);

			if (question == null) {
				return "redirect:/qna/list";
			}

			// 첨부된 파일명 확인
			String savedfile = question.getQuestionImageSaved();

			// 로그인 아이디를 board객체에 저장
			question.setMemberId(user.getUsername());
			// board.setMemberId("test1");

			// 글 삭제
			int result = service.questionDelete(question);

			// 글 삭제 성공 and 첨부된 파일이 있는 경우 파일도 삭제
			if (result == 1 && savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + savedfile);
			}
			
			return "redirect:/qna/list";
		}

	/**
	 * 게시글 좋아요 누르기
	 * 
	 * @param board       불러온 게시글
	 * @param boardNumber 불러온 게시글의 글번호
	 * @param user        사용자 계정
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("recommend") public int boardRecommend(int
	 * boardNumber, @AuthenticationPrincipal UserDetails user) {
	 * 
	 * int isLiked = 0;
	 * 
	 * String id = user.getUsername();
	 * 
	 * log.debug("recommend 호출됨!!!");
	 * 
	 * // 받아온 글번호와 로그인된 아이디가 담긴 boardLiked 객체를 만든다 BoardLiked boardLiked = new
	 * BoardLiked(boardNumber, id);
	 * 
	 * // 객체를 얻어온다 BoardLiked boardLiked2 = service.getBoardLiked(boardLiked);
	 * 
	 * log.debug("새롭게 얻은 boardLiked:{}", boardLiked2);
	 * 
	 * // 좋아요가 되어 있지 않다면(객체가 null)이라면 if (boardLiked2 == null) { // insert
	 * service.boardRecommend(boardLiked); isLiked = 1; log.debug("like 처리 완료"); }
	 * else { // 좋아요가 되어 있다면 delete
	 * service.deleteRecommend(boardLiked2.getBoardLikedNumber()); isLiked = 0;
	 * log.debug("like 취소"); }
	 * 
	 * return isLiked;
	 * 
	 * }
	 */

	// 답변 저장
		@PostMapping("answerInsert")
		public String answerInsert(
			Answer answer
			, @AuthenticationPrincipal UserDetails user) {
			
			answer.setMemberId(user.getUsername());
			answer.setMemberNickName("testUser");
			
			/*
			 * log.debug("가져온 question 객체 번호:{}", question.getAnswerAccepted());
			 * 
			 * Question questiontemp = service.questionRead(question.getQuestionNumber());
			 * 
			 * question.setAnswerAccepted(questiontemp.getAnswerAccepted());
			 * 
			 * model.addAttribute(question);
			 */
			
			/* log.debug("채택 여부 확인하기 위한 객체 : {}", question); */
			
			log.debug("저장할 리플 정보 : {}", answer);
			int result = service.answerInsert(answer);
			
			int replyPlus = service.answerPlus(answer.getQuestionNumber());
			
			return "redirect:/qna/read?questionNumber=" + answer.getQuestionNumber();
		}

	/**
	 * 댓글 삭제
	 * @param reply 삭제할 댓글 객체
	 * @return 삭제 후 리다이렉트 페이지
	 */
	@GetMapping("answerDelete")
	public String answerDelete(
			Answer answer
			, @AuthenticationPrincipal UserDetails user) {
		
		answer.setMemberId(user.getUsername());
		
		int result = service.answerDelete(answer);
		int answerMinus = service.answerMinus(answer.getQuestionNumber());
		
		return "redirect:/qna/read?questionNumber=" + answer.getQuestionNumber();
	}

	/**
	 * 게시글 이미지 보기
	 * @param model
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	/*
	 * @GetMapping("boardImage") public String boardImage(Model
	 * model, @RequestParam(name = "page", defaultValue = "1") int page, String
	 * type, String searchWord) {
	 * 
	 * PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage,
	 * page, type, searchWord);
	 * 
	 * ArrayList<Board> boardlist = service.list(navi, type, searchWord);
	 * 
	 * model.addAttribute("navi", navi); model.addAttribute("boardlist", boardlist);
	 * model.addAttribute("type", type); model.addAttribute("searchWord",
	 * searchWord);
	 * 
	 * log.debug("searchWord 출력! : {}", searchWord); log.debug("boardlist 출력! : {}",
	 * boardlist);
	 * 
	 * return "/board/boardImage"; }
	 */
	
	// 답변 채택
	@GetMapping("answerAccept")
	public String answerAccept(
			int answerNumber
			, int questionNumber
			, @AuthenticationPrincipal UserDetails user) {
		
		Answer answer = service.answerSelect(answerNumber);
		
		answer.setMemberId(user.getUsername());
		
		int result1 = service.answerAccept(questionNumber);
		int result2 = service.answerSubAccept(answerNumber);
		
		return "redirect:/qna/read?questionNumber=" + answer.getQuestionNumber();
	}

}
