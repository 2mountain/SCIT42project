package net.softsociety.aimori.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softsociety.aimori.dao.ChallengeDAO;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Challenge;
import net.softsociety.aimori.util.PageNavigator;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	ChallengeDAO chdao;
	
	@Override
	public ArrayList<Challenge> colist(PageNavigator conavi, String type, String searchWord) {
		HashMap<String, String> map =new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		RowBounds rb = new RowBounds(conavi.getStartRecord(), conavi.getCountPerPage());
		ArrayList<Challenge> confiencelist = chdao.confienceList(map, rb); 
		
		return confiencelist;
	}

	@Override
	public PageNavigator getCoPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		int total = chdao.countchallenge(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}

	@Override
	public PageNavigator getchPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		int total = chdao.countconfience(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}

	@Override
	public ArrayList<Challenge> chlist(PageNavigator chnavi, String type, String searchWord) {
		HashMap<String, String> map =new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		RowBounds rb = new RowBounds(chnavi.getStartRecord(), chnavi.getCountPerPage());
		ArrayList<Challenge> challengelist = chdao.challengeList(map, rb); 
		
		return challengelist;
	}

	
}
