package net.softsociety.aimori.service;

import java.util.ArrayList;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Reply;
import net.softsociety.aimori.util.PageNavigator;

public interface BoardService {
	
	/**
	 * 게시글 저장
	 * @param board
	 * @return 1 || 0
	 */
	public int write(Board board);
	
	
	/**
	 * 전체 게시글 조회
	 * @param navi 
	 * @param searchWord 
	 * @param type 
	 * @return List<Board>
	 */
	public ArrayList<Board> selectAll(PageNavigator navi, String type, String searchWord);

	/**
	 * 게시글 1개 조회
	 * @param boardnum
	 * @return Board
	 */
	public Board read(int boardnum);
	
	/**
	 * 페이지 정보 생성
	 * @param pagePerGroup
	 * @param countPerPage
	 * @param page
	 * @param type
	 * @param searchWord
	 * @return
	 */
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);


	/**
	 * 글 삭제 + 첨부파일 삭제
	 * @param map
	 * @return
	 */
	public int delete(Board board);

	/**
	 * 글 수정
	 * @param board
	 * @return
	 */
	public int update(Board board);

	/**
	 * 댓글 저장
	 * @return
	 */
	public int replyWrite(Reply reply);

	/**
	 * 글에 해당하는 댓글 가져오기
	 * @param boardnum
	 * @return 댓글 목록
	 */
	public ArrayList<Reply> readReply(int boardnum);

	/**
	 * 댓글 번호와 일치하는 댓글 데이터 가져오기
	 * @param replynum
	 * @return
	 */
	public Reply selectReply(int replynum);

	/**
	 * 댓글번호에 해당하는 댓글 삭제
	 * @param reply
	 * @return
	 */
	public int deleteReply(Reply reply);

	
}
