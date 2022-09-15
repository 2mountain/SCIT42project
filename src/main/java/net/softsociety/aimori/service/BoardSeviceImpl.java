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

    //
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

	// 글목록 출력
	@Override
	public ArrayList<Board> list(PageNavigator navi, String type, String searchWord) {
		
		HashMap<String, String> map =new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		ArrayList<Board> boardlist = boardDAO.boardList(map, rb); 
		
		return boardlist;
	}

	// 글읽기
	@Override
	public Board boardRead(int boardNumber) {
//		int result = boardDAO.updateHits(boardNumber);
		Board board = boardDAO.boardRead(boardNumber);
		return board;
	}

	// 글삭제
	@Override
	public int boardDelete(Board board) {
		int result = boardDAO.boardDelete(board);
		return result;	}

	// 글수정
	@Override
	public int boardUpdate(Board board) {
		int result = boardDAO.boardUpdate(board);
		return result;
	}
	
}
