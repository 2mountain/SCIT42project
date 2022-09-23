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


}
