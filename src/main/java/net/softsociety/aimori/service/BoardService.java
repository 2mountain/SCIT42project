package net.softsociety.aimori.service;

import java.util.ArrayList;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.BoardLiked;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.util.PageNavigator;

public interface BoardService {

	// 글 저장
	public int boardInsert(Board board);
	//페이징 정보
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);
	//현재 페이지 글 목록
	public ArrayList<Board> list(PageNavigator navi, String type, String searchWord);
	// 글읽기
	public Board boardRead(int boardNumber);
	// 글삭제
	public int boardDelete(Board board);
	// 글 수정
	public int boardUpdate(Board board);
	// 좋아요 추가
	public int boardRecommend(BoardLiked boardLiked);
	// 좋아요 조회
	public int boardSelectRecommend(int boardNumber);
	// 좋아요 기록 찾아보기
	public BoardLiked getBoardLiked(BoardLiked boardLiked);
	// 좋아요 취소
	public void deleteRecommend(int BoardLikedNumber);
	// 댓글 목록
	public ArrayList<Reply> replyList(int boardNumber);
	// 댓글 저장
	public int replyInsert(Reply reply);
	// 댓글 수정
	// 댓글 삭제
	public int replyDelete(Reply reply);
	// 게시글에 달린 댓글 개수
	public int replyCount(int boardNumber);
	// 댓글 개수 +1
	public int replyPlus(int boardNumber);
	// 댓글 개수 -1
	public int replyMinus(int boardNumber);

}
