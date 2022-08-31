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
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.service.BoardService;
import net.softsociety.aimori.service.MemberService;
import net.softsociety.aimori.util.FileService;
import net.softsociety.aimori.util.PageNavigator;


@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {
	@Autowired // 객체로 생성된 것 대입
	BoardService bs;
	
	@Autowired
	MemberService ms;

	// 페이지 처리
	@Value("${user.board.page}") // @Value == properties 등에 저장한 값을 불러오는 annotation
	int countPerPage;
	@Value("${user.board.group}")
	int pagePerGroup;
	
	// 파일 저장 관리 --> properties에 설정한 것
	@Value("${spring.servlet.multipart.location}")
	String uploadPath; // c:upload
	
	/**
	 * 게시판
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model
			, @RequestParam(name="page", defaultValue="1") int page // 전달받은 page번호 X시 1페이지
			, String type
			, String searchWord) {
		log.debug("[BoardController] list - parameter - 페이지 당 글 수 : {} /  페이지 이동 링크 수 : {} / "
				+ "현재 페이지 : {} / 검색 대상 : {} / 검색어 : {}", countPerPage, pagePerGroup, page, type, searchWord);
		
		// 페이지 정보 생성(ex) 페이지당 표시 글 수 , 그룹 수 등)
		PageNavigator navi = bs.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		// 전체 데이터 가져오기
		ArrayList<Board> list = bs.selectAll(navi, type, searchWord);
		log.debug("[BoardController] list - 전체 게시글 : {}", list);
		
		model.addAttribute("navi", navi);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
		return "/board/list";
	}
	
	/**
	 * 글쓰기 폼으로 이동
	 * @return
	 */
	@GetMapping("/write")
	public String writeForm() {
		return "/board/writeForm";
	}
	
	@PostMapping("/write")
	public String write(
			Board board, 
			@AuthenticationPrincipal UserDetails user,
			MultipartFile upload) { 
			// MultipartFile
			// 스프링에서 파일을 미리 서버에 임시로 올려놓고 해당 정보를 갖고 있는 객체 / input type="file"의 name과 이름 같아야 함
			// POST 방식으로 값 전달 받아야 함
			// 파일이 여러개인 경우 ArrayList로 받으면 됨
		
		if(upload != null) {
			log.debug("[BoardController] write - 업로드 : {}", upload.getName());
			log.debug("[BoardController] write - 업로드 : {}", upload.getOriginalFilename());
			log.debug("[BoardController] write - 업로드 : {}", upload.getContentType());
			log.debug("[BoardController] write - 업로드 : {}", upload.getSize());
			log.debug("[BoardController] write - 업로드 : {}", upload.isEmpty());
		}
		
		if(upload != null && !upload.isEmpty()) { // 업로드 된 파일이 존재하는 경우
			String savedfile = FileService.saveFile(upload, uploadPath); 
			// upload == 파일 / MultiPartFile 
			// uploadPath == properties에서 설정한 경로
			// 파일이 서버에 저장된 이름을 반환 받음
			
			board.setOriginalfile(upload.getOriginalFilename()); // 실제 사용자가 저장한 파일명
			board.setSavedfile(savedfile); // 서버에 저장된 이름
		}
		log.debug("[BoardController] write - 저장할 글 정보 : {}", board);
		log.debug("[BoardController] write - parameter - board : {}", board);
		log.debug("[BoardController] write - parameter - user : {}", user);
		
		Member member = ms.getMemberInfo(user.getUsername());
		
		board.setMemberid(member.getMemberid());
		
		int result = bs.write(board);
		
		// 저장 실패
		if(result == 0) {
			log.debug("[BoardController] write : 실패");
		}
				
		return "redirect:/board/list";
	}
	
	/**
	 * 글 읽기
	 * @param model
	 * @param board --> int boardnum
	 * @return
	 */
	@GetMapping("/read")
	public String read(Model model, 
			@RequestParam(name="boardnum", defaultValue="0") int boardnum) {
		log.debug("[BoardController] read - parameter : {}", boardnum);
		
		// boardnum으로 일치하는 글의 정보을 가져오는 method 필요
		Board board = bs.read(boardnum); // 해당 번호의 글 가져옴
		log.debug("[BoardController] read - board : {}", board);
		
		if(board == null) {
			return "redirect:/board/list";
		}
		
		// 해당 글의 댓글 목록을 읽어서 Model에 저장
		ArrayList<Reply> replylist = bs.readReply(boardnum);
		
		// 결과를 model에 담아 HTML에서 출력
		model.addAttribute("board", board); 
		model.addAttribute("replylist", replylist);
		
		
		return "board/read";
	}
	
	//첨부파일 다운로드
	//@RequestMapping(value = "download", method = RequestMethod.GET) // @GetMapping()을 길게 쓴 것
	@GetMapping("/download") // WebSecurity에 추가 X 시 로그인 한 사람만 다운 가능
	public String fileDownload(int boardnum, Model model, HttpServletResponse response) { 
											// HttpServletResponse​ - HTTP 응답 정보(요청 처리 결과)를 제공하는 인터페이스
		Board board = bs.read(boardnum);
		
		//원래의 파일명
		String originalfile = new String(board.getOriginalfile());
		try {
			// header == 파일 등 전달받을 것을 알려주는 것
			response.setHeader("Content-Disposition", // Content-Disposition == 헤더명
					" attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8")); 
					// DB에 저장된 원래 이름에 한글, 특수문자등이 포함되어 있으므로 encode해서 가져옴
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//저장된 파일 경로
		String fullPath = uploadPath + "/" + board.getSavedfile();
		
		//서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
		FileInputStream filein = null;
		ServletOutputStream fileout = null;  // client 쪽으로 출력하는 Stream
		
		// Stream의 역할 == 파일이 실제 저장된 경로와 Controller의 method
		// 			    == 실행 중인 프로그램 기준으로 파일이 들어오고 나가는 통로
		// FileInputStream == 파일을 읽어오는 통로
		// FileOutputStream == 파일을 내보내는 통로
		// download 기능 == 브라우저가 읽지 못하는 것을 그냥 저장하는 것
		try {
			filein = new FileInputStream(fullPath);// file이 실제 저장된 경로에서 파일 가져오는 InputStream
			fileout = response.getOutputStream(); // client로 내보내는 OutputStream
			
			//Spring의 파일 관련 유틸 이용하여 출력
			FileCopyUtils.copy(filein, fileout); // 파일이 실제 저장된 위치에서 파일을 복사해 출력
												 // InputStream을 OutputStream으로 연결 == 복붙
												 // filein --> fileout
			filein.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 글 번호를 전달받아 삭제
	 * @param boardnum
	 * @return 삭제 후 글목록으로 이동
	 */
	@GetMapping("/delete")
	public String delete(
			int boardnum
			, @AuthenticationPrincipal UserDetails user) {
		// bs.delete(boardnum); 만 쓰는 경우
		// ==> 글은 삭제됨 but 주소창에 URL을 임의로 변경하는 경우 자신의 글이 아닌 글도 삭제 가능
		//	   서버 상의 첨부파일은 지워지지 않고 남음 + 사용자 정보는 삭제 ==> 주인 없는 파일이 남아있음
		// 따라서 Controller와 SQL문에서도 사용자의 인증 정보 확인해야함

		// 해당 번호의 글 정보 조회
		Board board = bs.read(boardnum);
		log.debug("[BoardController] delete - 글 정보 : {}", board);
		if(board == null) {
			return "redirect:/board/list";
		}
		
		// 첨부파일 이름 확인
		String savedfile = board.getSavedfile();
		
		// 로그인 아이디 확인
//		board.setMemberid(user.getUsername()); ==> SQL의 WHERE절에로 아이디 불일치를 일으켜 막는 것
//		==> 아래 if문은 Controller에서 아이디 불일치 막는 것
		if(!user.getUsername().equals(board.getMemberid())) {
			log.debug("[BoardController] delete : 사용자 정보 불일치");
			return "redirect:/board/list";
		}
		
		// 글삭제 (글 번호와 아이디 전달) ==> 글번호와 아이디를 함께 전달해야하므로 MAP or 객체 사용
		// 글 삭제 성공 and 첨부파일 있는 경우 함께 삭제
		int result = bs.delete(board);
		
		if(result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + savedfile);
			// uploadPath == properties에서 설정한 "c:/upload"
		}
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/update")
	public String update(int boardnum, Model model) {
		// parameter에 해당하는 글 정보
		Board board = bs.read(boardnum);
		log.debug("[BoardController] update - 글 정보 : {}", board);
		
		// 전달받은 번호의 글 정보를 가지고 HTML로 return
		model.addAttribute("board", board);
		
		return "/board/updateForm";
	}
	
	@PostMapping("/update")
	public String update(
			Board board
			, MultipartFile upload // 수정시 첨부파일도 수정(변경 or 삭제) 가능하게 하기 위한 parameter
			, @AuthenticationPrincipal UserDetails user // 본인 확인용
			) {
		log.debug("[BoardController] update - board {}", board);
		log.debug("[BoardController] update - upload {}", upload);
		log.debug("[BoardController] update - user {}", user);

		// 본인 글 확인
		if(!board.getMemberid().equals(user.getUsername())) {
			return "redirect:/board/read?boardnum=" + board.getBoardnum(); 
		}
		
		// 첨부파일 처리
		if(upload != null && !upload.isEmpty()) { // 업로드 된 파일이 존재하는 경우
			String savedfile = FileService.saveFile(upload, uploadPath); 
			// upload == 파일 / MultiPartFile (업로드된 파일의 정보를 가진 객체)
			// uploadPath == properties에서 설정한 경로 / c:/upload
			// 파일이 서버에 저장된 이름을 반환 받음
			
			board.setOriginalfile(upload.getOriginalFilename()); // 실제 사용자가 저장한 파일명
			board.setSavedfile(savedfile); // 서버에 저장된 이름
		}
		
		// 수정
			bs.update(board);
		
		// 읽던 본인 글로 이동
		return "redirect:/board/read?boardnum=" + board.getBoardnum();
	}
	
	@PostMapping("replyWrite")
	public String replyWrite(
			Reply reply,
			@AuthenticationPrincipal UserDetails user
			) {
		log.debug("[BoardController] replyWrite - parmeter : {}", reply);
		
		// 전달 받은 Reply객체(본문글 번호, 리플내용)에 로그인한 아이디 추가
		reply.setMemberid(user.getUsername());
		
		// DB에 저장
		int result = bs.replyWrite(reply);
		
		if(result == 0) {
			log.debug("댓글 저장 실패");
		}
		
		// 읽던 글로 돌아가기
		return "redirect:/board/read?boardnum=" + reply.getBoardnum();
	}
	
	@GetMapping("deleteReply")
	public String deleteReply(
			int replynum,
			@AuthenticationPrincipal UserDetails user
			) {
		
		log.debug("[BoardController] deleteReply - parmeter : {}", replynum);
		
		// 댓글 번호로 댓글 정보 가져오기
		Reply reply = bs.selectReply(replynum);
		log.debug("[BoardController] deleteReply - reply : {}", reply);
		
		if(!reply.getMemberid().equals(user.getUsername())) {
			return"redirect:/board/read?boardnum=" + reply.getBoardnum();
		}
		
		// 다른 사람이 타인의 댓글 삭제하는 것을 SQL의 WHERE절에서 한번 더 막기 위한 코드 
		reply.setMemberid(user.getUsername());
		
		// 댓글 삭제
		int result = bs.deleteReply(reply);
		
		if(result == 0) {
			log.debug("[BoardController] deleteReply : 댓글 삭제 실패");
		}
		
		return"redirect:/board/read?boardnum=" + reply.getBoardnum();
	}
	
	@GetMapping("admin")
	public String admin() {
		return "board/admin";
	}
}
