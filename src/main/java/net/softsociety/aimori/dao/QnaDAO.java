package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Answer;
import net.softsociety.aimori.domain.Board;
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
	// 글 수정
	public int questionUpdate(Question question);
	// 글 삭제
	public int questionDelete(Question question);
	// 답변 등록
	public int answerInsert(Answer answer);
	// 답변 개수 +1
	public int answerPlus(int questionNumber);
	// 답변 목록 불러오기
	public ArrayList<Answer> answerList(int questionNumber);
	// 답변 삭제
	public int answerDelete(Answer answer);
	// 답변 개수 -1
	public int answerMinus(int questionNumber);
	// 답변 채택
	public int answerAccept(int questionNumber, int answerNumber);
	
	
	//메인 qna 출력 리스트
	public ArrayList<Question> qnaMainList();

}
