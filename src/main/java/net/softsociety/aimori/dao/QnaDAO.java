package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.util.PageNavigator;

/**
 * 게시판 관련 매퍼 인터페이스
 */
@Mapper
public interface QnaDAO {

	// 글 저장
	public int questionInsert(Question question);
	// 현재 페이지 글 목록
	public ArrayList<Question> questionList(HashMap<String, String> map, RowBounds rb);
	// 전체 글 개수
	public int countQuestion(HashMap<String, String> map);
	// 글읽기
	public Question questionRead(int questionNumber);
	// 조회수 증가
	public int updateHits(int questionNumber);

}
