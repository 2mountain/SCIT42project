package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.domain.Reply;

@Mapper
public interface BoardDAO {

	/**
	 * 글 쓰기
	 * @param board
	 * @return insert 결과 int ( 1 || 0 ) 
	 */
	public int insert(Board board);
	
	/**
	 * 전체 글의 개수 조회
	 * @param map
	 * @return 전체 글 개수
	 */
	public int count(HashMap<String, String> map);
	
	/**
	 * 전체 글 조회
	 * @param rb 
	 * @param searchWord 
	 * @param type 
	 * @return List<Board>
	 */
	public ArrayList<Board> selectAll(HashMap<String, String> map, RowBounds rb);

	/**
	 * 게시글 번호와 일치하는 글 1개 조회
	 * @param boardnum
	 * @return param과 일치하는 글 1개
	 */
	public Board selectOne(int boardnum);

	/**
	 * parameter로 받은 boardnum과 일치하는 글의 조회수 +1
	 * @param boardnum
	 * @return update 결과 int ( 1 || 0 )
	 */
	public int updateHits(int boardnum);

	/**
	 * 아이디, 글 정보가 일치하는 글 삭제 + 첨부파일 삭제
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
	 * @param reply
	 * @return
	 */
	public int replyWrite(Reply reply);

	/**
	 * 게시글에 해당하는 댓글 불러오기
	 * @param boardnum
	 * @return 댓글 목록
	 */
	public ArrayList<Reply> readReply(int boardnum);

	/**
	 * 댓글 번호에 해당하는 댓글 정보 불러오기
	 * @param replynum
	 * @return parameter와 일치하는 댓글의 정보
	 */
	public Reply selectReply(int replynum);

	/**
	 * 댓글번호에 해당하는 댓글 삭제
	 * @param reply
	 * @return
	 */
	public int deleteReply(Reply reply);

}
