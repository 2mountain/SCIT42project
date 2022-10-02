package net.softsociety.aimori.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.Pet;
import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.service.BoardService;
import net.softsociety.aimori.service.MemberService;
import net.softsociety.aimori.service.QnaService;

@Slf4j
@Controller
public class HomeController {

	@Autowired
	BoardService service;

	@Autowired
	QnaService qnaservice;

	@Autowired
	MemberService mservice;

	@GetMapping({ "/", "" })
	public String home(@AuthenticationPrincipal UserDetails user, Model model, Member member) {
		log.debug("[home]");

		ArrayList<Board> boardlist = service.boardMainList();
		model.addAttribute("boardlist", boardlist);

		log.debug("boardlist 출력! : {}", boardlist);

		ArrayList<Question> qnalist = qnaservice.qnaMainList();
		model.addAttribute("qnalist", qnalist);

		/*
		 로그인이 안 됐을 때 가능한 페이지에 로그인 시 보이는걸 만드는게 말이 안 된다!
		if (member.getMemberId().length() != 0) {
			log.debug("유저출력", user.getUsername());
			member = mservice.selectOne(user.getUsername());
			log.debug(member.getMemberImageSavedFile()); 
			model.addAttribute("member",member);
		}
		*/
		model.addAttribute("member",member);
		
		 

		return "home";
	}
	
	@PostMapping("getMemberInfo")
	@ResponseBody
	public Member getMemberInfo(String memberId) {
		
		log.debug("도착함");
		
		Member member = mservice.selectOne(memberId);
		
		log.debug("member 객체 : {}", member);
		
		return member;
		
		
	}
}
