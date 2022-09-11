package net.softsociety.aimori.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.BoardDAO;
import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.util.PageNavigator;

@Transactional
@Service
@Slf4j
public class BoardSeviceImpl implements BoardService {

    @Autowired
    private BoardDAO boardDAO;

	@Override
	public PageNavigator getPageNavigator(
			int pagePerGroup, int countPerPage, int page, String type, String searchWord) {
		
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		int total = boardDAO.countBoard(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}

	@Override
	public ArrayList<Board> list(PageNavigator navi, String type, String searchWord) {
		
		HashMap<String, String> map =new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		ArrayList<Board> boardlist = boardDAO.boardList(map, rb); 
		
		return boardlist;
	}
	
}
