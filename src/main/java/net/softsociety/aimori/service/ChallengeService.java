package net.softsociety.aimori.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

// import net.softsociety.aimori.controller.Entrylist;
import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.util.PageNavigator;


public interface ChallengeService {
	//대회 페이지 만들기
		ArrayList<Challenge> contestlist(PageNavigator conavi, String type, String searchWord);

		PageNavigator getCoPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

		
		//도전과제 페이지 만들기
		PageNavigator getchPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

		ArrayList<Challenge> challengelist(PageNavigator chnavi, String type, String searchWord);

		Challenge read(int challengeNumber);

		// Entrylist list();

	

}
