package net.softsociety.aimori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {
	
	/**
	 * 커뮤니티 메인 화면
	 * @return board.html
	 */
	@GetMapping({"/", ""})
	public String board() {
		log.debug("[Open Board mainScreen]");
		return "/board/board";
	}
	
	/**
	 * 글쓰기 폼으로 이동
	 */
	@GetMapping("write")
	public String write() {
		return "/board/boardWriteForm";
	}
}
