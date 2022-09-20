package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.BoardLiked;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.util.PageNavigator;

/**
 * 게시판 관련 매퍼 인터페이스
 */
@Mapper
public interface BoardDAO {
    // 글 저장
	public int boardInsert(Board board);
	// 현재 페이지 글 목록
	public ArrayList<Board> boardList(HashMap<String, String> map, RowBounds rb);
	// 전체 글 개수
	public int countBoard(HashMap<String, String> map);
	// 글 읽기
	public Board boardRead(int boardNumber);
	//조회수 증가
	public int updateHits(int boardNumber);
	// 글 삭제
	public int boardDelete(Board board);
	// 글 수정
	public int boardUpdate(Board board);
	// 좋아요 추가
	public int boardRecommend(BoardLiked boardLiked);
	// 좋아요 조회
	public int boardSelectRecommend(int boardNumber);
	// 좋아요 불러와서 체크
	public BoardLiked getBoardLiked(BoardLiked boardLiked);
	// 좋아요 취소
	public void deleteRecommend(int boardLikedNumber);
	// 댓글 저장
	public int replyInsert(Reply reply);
	// 댓글 목록
	public ArrayList<Reply> replyList(int boardNumber);

}
