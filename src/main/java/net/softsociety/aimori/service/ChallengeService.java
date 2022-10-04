package net.softsociety.aimori.service;

import java.util.ArrayList;



import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.domain.Entrylist;
import net.softsociety.aimori.domain.Memberchallenge;
import net.softsociety.aimori.util.PageNavigator;


public interface ChallengeService {
	//대회 페이지 만들기
		ArrayList<Challenge> contestlist(PageNavigator conavi, String type, String searchWord);

		PageNavigator getCoPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

		
		//도전과제 페이지 만들기
		PageNavigator getchPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

		ArrayList<Challenge> challengelist(PageNavigator chnavi, String type, String searchWord);

		Challenge read(int challengeNumber);

		ArrayList<Entrylist> list(int challengeNumber);

		int givepoint(int point, String memberId,int entrylistNumbers);

		int writechallenge(Challenge challenge);

		int entrychallenge(Memberchallenge memberchall);

		ArrayList<Memberchallenge> mychallengelist(PageNavigator chnavi, String chtype, String chsearchWord);

		
		


	
	

}
