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
import net.softsociety.aimori.domain.BoardLiked;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.util.PageNavigator;

@Transactional
@Service
@Slf4j
public class BoardSeviceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Override
	public int boardInsert(Board board) {
		int result = boardDAO.boardInsert(board);
		return result;
	}

	//
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {

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

		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);

		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		ArrayList<Board> boardlist = boardDAO.boardList(map, rb);

		return boardlist;
	}

	// 인기글 보기
	@Override
	public ArrayList<Board> hotList(PageNavigator navi, String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);

		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		ArrayList<Board> boardlist = boardDAO.boardHotList(map, rb);

		return boardlist;
	}

	// 글읽기
	@Override
	public Board boardRead(int boardNumber) {
		int result = boardDAO.updateHits(boardNumber);
		Board board = boardDAO.boardRead(boardNumber);
		return board;
	}

	// 글삭제
	@Override
	public int boardDelete(Board board) {
		int result = boardDAO.boardDelete(board);
		return result;
	}

	// 글수정
	@Override
	public int boardUpdate(Board board) {
		int result = boardDAO.boardUpdate(board);
		return result;
	}

	// 좋아요 추가
	@Override
	public int boardRecommend(BoardLiked boardLiked) {
		int result = boardDAO.boardRecommend(boardLiked);

		log.debug("라이크 객체 : {}", boardLiked);

		return result;
	}

	// 좋아요 수 카운트
	@Override
	public int boardSelectRecommend(int boardNumber) {
		int result = boardDAO.boardSelectRecommend(boardNumber);
		return result;
	}

	// 좋아요 체크
	@Override
	public BoardLiked getBoardLiked(BoardLiked boardLiked) {
		BoardLiked boardliked = boardDAO.getBoardLiked(boardLiked);
		return boardliked;
	}

	// 좋아요 취소
	@Override
	public void deleteRecommend(int BoardLikedNumber) {
		boardDAO.deleteRecommend(BoardLikedNumber);
	}

	// 댓글 저장
	@Override
	public int replyInsert(Reply reply) {
		int result = boardDAO.replyInsert(reply);
		return result;
	}

	// 댓글 목록
	@Override
	public ArrayList<Reply> replyList(int boardNumber) {
		ArrayList<Reply> replylist = boardDAO.replyList(boardNumber);
		return replylist;
	}

	// 댓글 수정
	@Override
	public int replyUpdate(Reply reply) {
		int result = boardDAO.replyUpdate(reply);
		return result;
	}

	// 댓글 삭제
	@Override
	public int replyDelete(Reply reply) {
		int result = boardDAO.replyDelete(reply);
		return result;
	}

	// 게시글에 달린 댓글 개수
	@Override
	public int replyCount(int boardNumber) {
		int result = boardDAO.replyCount(boardNumber);
		return result;
	}

	// 댓글 개수 +1
	@Override
	public int replyPlus(int boardNumber) {
		int result = boardDAO.replyPlus(boardNumber);
		return result;
	}

	// 댓글 개수 -1
	@Override
	public int replyMinus(int boardNumber) {
		int result = boardDAO.replyMinus(boardNumber);
		return result;
	}

	// 홈 화면 출력
	@Override
	public ArrayList<Board> boardMainList() {
		ArrayList<Board> boardlist = boardDAO.boardMainList(); 
		
		return boardlist;
	}

}
