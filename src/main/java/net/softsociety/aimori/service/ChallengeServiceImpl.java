package net.softsociety.aimori.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softsociety.aimori.dao.ChallengeDAO;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.domain.Entrylist;
import net.softsociety.aimori.domain.Memberchallenge;
import net.softsociety.aimori.util.PageNavigator;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	ChallengeDAO chdao;
	
	@Override
	public ArrayList<Challenge> contestlist(PageNavigator conavi, String cotype, String cosearchWord) {
		HashMap<String, String> map =new HashMap<>();
		map.put("cotype", cotype);
		map.put("cosearchWord", cosearchWord);
		
		RowBounds rb = new RowBounds(conavi.getStartRecord(), conavi.getCountPerPage());
		ArrayList<Challenge> contestlist = chdao.contestList(map, rb); 
		
		return contestlist;
	}

	@Override
	public PageNavigator getCoPageNavigator(int pagePerGroup, int countPerPage, int page, String cotype,
			String cosearchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("cotype", cotype);
		map.put("cosearchWord", cosearchWord);
		
		int total = chdao.countContest(map);
		System.out.println(total);
		PageNavigator conavi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return conavi;
	}

	@Override
	public PageNavigator getchPageNavigator(int pagePerGroup, int countPerPage, int page, String chtype,
			String chsearchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("chtype", chtype);
		map.put("chsearchWord", chsearchWord);
		
		int total = chdao.countChallenge(map);
		System.out.println(total);
		PageNavigator chnavi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return chnavi;
	}

	@Override
	public ArrayList<Challenge> challengelist(PageNavigator chnavi, String chtype, String chsearchWord) {
		HashMap<String, String> map =new HashMap<>();
		map.put("chtype", chtype);
		map.put("chsearchWord", chsearchWord);
		
		RowBounds rb = new RowBounds(chnavi.getStartRecord(), chnavi.getCountPerPage());
		ArrayList<Challenge> challengelist = chdao.challengeList(map, rb); 
		
		return challengelist;
	}

	@Override
	public Challenge read(int challengeNumber) {
		// TODO Auto-generated method stub
		Challenge challenge = chdao.readChallenge(challengeNumber);
		String[] challengeCutPoint = challenge.getChallengeContents().split("/");
		challenge.setChallengeContents(challengeCutPoint[0]);
		int challpoint = Integer.valueOf(challengeCutPoint[1]);
		challenge.setChallPoint(challpoint);
		return challenge;
	}
	

	@Override
	public ArrayList<Entrylist> list(int challengeNumber) {
		// TODO Auto-generated method stub
		ArrayList<Entrylist> entrylist = chdao.getEntrylist(challengeNumber);
		return entrylist;
	}

	@Override
	public int givepoint(int point, String memberId,int entrylistNumber) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("point", point);
		map.put("entrylistNumber",entrylistNumber);
		
		int result = chdao.giveMemberPoint(map);
		int result2 = chdao.giveEntrylistPoint(map);

		
 				if(result!=0&&result!=0)
 				{
 					return result;
 				}
 				
 				return 0;
 				
	}

	@Override
	public int writechallenge(Challenge challenge) {
	
		String contents=challenge.getChallengeContents()+"/"+challenge.getChallPoint();
		challenge.setChallengeContents(contents);
		int result = chdao.writeChallenge(challenge);
		return result;
	}

	@Override
	public int entrychallenge(Memberchallenge memberchall) {
		// TODO Auto-generated method stub
		int result =chdao.entryChallenge(memberchall);
		return result;
	}

	@Override
	public ArrayList<Memberchallenge> mychallengelist(String memberId) {
		
		ArrayList<Memberchallenge> mychallengelist = chdao.mychallengeList(memberId); 
		
		return mychallengelist;
	}

	@Override
	public int delete(int challengeNumber) {
		int result=chdao.deleteChallenge(challengeNumber);
		return result;
	}


		
	

	

	
}
