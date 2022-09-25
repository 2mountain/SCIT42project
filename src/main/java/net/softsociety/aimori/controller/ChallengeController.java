package net.softsociety.aimori.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.service.ChallengeService;
import net.softsociety.aimori.util.PageNavigator;
import net.softsociety.spring5.domain.Board;
import net.softsociety.spring5.domain.Reply;

@Slf4j
@RequestMapping("challenge")
@Controller
public class ChallengeController {


	//게시판 목록의 페이지당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	//게시판 목록의 페이지 이동 링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	@Autowired
	ChallengeService chser;

	@GetMapping("main")
	public String main() {
		return "/challenge/main";
	}

	@GetMapping({"write"})
	public String write() {

		return "/challenge/write";
	}

	@GetMapping({"challengelist"})
	public String challengelist(Model model
			, @RequestParam(name = "page", defaultValue = "1") int page
			, String chtype
			, String chsearchWord)
	{
		log.debug("challengeinner**************************");
		PageNavigator chnavi = chser.getchPageNavigator(pagePerGroup, countPerPage, page, chtype, chsearchWord);
		
		System.out.println(chnavi.getTotalRecordsCount());
		
		ArrayList<Challenge> challengelist = chser.challengelist(chnavi, chtype, chsearchWord);

		log.debug("challengelist : {}", challengelist);
		log.debug("chnavi : {}", chnavi);

		
		model.addAttribute("chnavi", chnavi);
		model.addAttribute("challengelist", challengelist);
		model.addAttribute("chtype", chtype);
		model.addAttribute("chsearchWord", chsearchWord);
		return "/challenge/challengelist";
	}

	@GetMapping({"contestlist"})
	public String contestlist(Model model
			, @RequestParam(name = "page", defaultValue = "1") int page
			, String cotype
			, String cosearchWord) {

		PageNavigator conavi = chser.getCoPageNavigator(pagePerGroup, countPerPage, page, cotype, cosearchWord);
		System.out.println(conavi.getTotalRecordsCount());

		ArrayList<Challenge> contestlist = chser.contestlist(conavi, cotype, cosearchWord);

		log.debug("contest : {}", contestlist);

		model.addAttribute("conavi", conavi);
		model.addAttribute("contestlist", contestlist);
		model.addAttribute("chtype", cotype);
		model.addAttribute("chsearchWord", cosearchWord);
		
		return "/challenge/contestlist";
	}
	
	@GetMapping({"contestwrite"})
	public String contestwrite()
	{
		return "/challenge/contestwrite";
	}
	
	
	@GetMapping({"challengewrite"})
	public String challengewrite()
	{
		return "/challenge/challengewrite";
	}

	
	@GetMapping({"challengeread"})
	public String challengeread(Model model
			, @RequestParam(name="boardnum", defaultValue = "0") int challengeNumber) { 

		// 도전과제를 불러오는 페이지
		Challenge challenge = chser.read(challengeNumber);
		if (challenge == null) {
			return "redirect:/challenge/challengelist"; //글이 없으면 목록으로
		}
		//관리자 계정에서만 뜨는페이지 접속할 시 해당인원에게 포인트 주기 기능
		Entrylist entrylist = chser.list();
		model.addAttribute("challenge", challenge);
		model.addAttribute("entrylist", entrylist);
		return "/challenge/challengeread";
	}	
	
	
	@GetMapping({"contestread"})
	public String contestread(Model model
			, @RequestParam(name="boardnum", defaultValue = "0") int challengeNumber) { 

		// 도전과제를 불러오는 페이지
		Challenge challenge = chser.read(challengeNumber);
		if (challenge == null) {
			return "redirect:/challenge/contestlist"; //글이 없으면 목록으로
		}
		//관리자 계정에서만 뜨는페이지 접속할 시 해당인원 등 수 부여
		
		model.addAttribute("challenge", challenge);
		model.addAttribute("entrylist", entrylist);

		
		return "/challenge/contestread";
	}
}
