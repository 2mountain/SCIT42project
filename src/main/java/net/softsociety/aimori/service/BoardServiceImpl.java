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
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.util.PageNavigator;

@Slf4j
@Transactional
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO bDAO;
	
	@Override
	public int write(Board board) {
		log.debug("[BoardServiceImpl] write - parameter : {}", board);
		
		int result = bDAO.insert(board);
		
		if(result == 0) {
			log.debug("[BoardServiceImpl] write : 실패");
		}
		
		return result;
	}
	
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		
		// XML의 parameterType은 1개의 값만 받을 수 있는데 type, searchWord 2개의 parameter를
		// XML로 보내려고 하면 Map으로 묶어서 보내야 함
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		// 조건에 해당하는 글의 개수
		int total = bDAO.count(map);
		
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}
	
	@Override
	public ArrayList<Board> selectAll(PageNavigator navi, String type, String searchWord) {
		// parmeter로 받은 변수들을 묶어서 전달할 때 MAP || 해당 parameter들을 담은 Class 에 담아 보냄
		// parameter를 묶어서 보내는 이유 ==> XML의 parameterType에는 객체 타입 1개만 쓸 수 있으므로
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
				
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage()); 
		// 숫자 2개를 DAO로 보내서 지정한만큼의 결과만 가져오게 함
		
		ArrayList<Board> list = bDAO.selectAll(map, rb);
		log.debug("[BoardServiceImpl] selectAll - list : {}", list);
		
		return list;
	}

	@Override
	public Board read(int boardnum) {
		
		log.debug("[BoardServiceImpl] read - parameter : {}", boardnum);
		
		// boardnum에 해당하는 글의 조회수 1 update
		int result = bDAO.updateHits(boardnum);
		
		Board board = bDAO.selectOne(boardnum);
		
		log.debug("[BoardServiceImpl] read - board : {}", board);
		
		return board;
	}

	@Override
	public int replyWrite(Reply reply) {
		
		int result = bDAO.replyWrite(reply);
		
		return result;
	}
	

	@Override
	public ArrayList<Reply> readReply(int boardnum) {
		
		ArrayList<Reply> replylist = bDAO.readReply(boardnum);
		
		return replylist;
	}

	@Override
	public int delete(Board board) {
		
		log.debug("[BoardServiceImpl] delete - board : {}", board);
		int result = bDAO.delete(board);
		
		return result;
	}

	@Override
	public int update(Board board) {
		
		log.debug("[BoardServiceImpl] update - board : {}", board);
		int result = bDAO.update(board);
		
		return result;
	}

	@Override
	public Reply selectReply(int replynum) {
		
		Reply reply = bDAO.selectReply(replynum);
		
		return reply;
	}

	@Override
	public int deleteReply(Reply reply) {
		
		int result = bDAO.deleteReply(reply);
		
		return result;
	}
	
	
}
