package net.softsociety.aimori.service;

import java.util.ArrayList;

import net.softsociety.aimori.domain.Answer;
import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.util.PageNavigator;

public interface QnaService {
	
	// 글 저장
	public int questionInsert(Question question);
	//페이징 정보
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);
	//현재 페이지 글 목록
	public ArrayList<Question> list(PageNavigator navi, String type, String searchWord);
	// 글읽기
	public Question questionRead(int questionNumber);
	// 글 수정
	public int questionUpdate(Question question);
	// 글 삭제
	public int questionDelete(Question question);
	// 답변 리스트 불러오기
	public ArrayList<Answer> answerList(int questionNumber);
	// 답변 등록
	public int answerInsert(Answer answer);
	// 답변 개수 +1
	public int answerPlus(int questionNumber);
	// 답변 삭제
	public int answerDelete(Answer answer);
	// 답변 개수 -1
	public int answerMinus(int questionNumber);
	// 답변 하나 불러오기
	public Answer answerSelect(int answerNumber);
	
	
	
	
	/* 답변 채택 관련*/
	// 답변 채택 게시글 기록
	public int answerAccept(int questionNumber);
	// 답변 채택 답변 기록
	public int answerSubAccept(int answerNumber);

	
	
	//메인 qna 출력
	public ArrayList<Question> qnaMainList();
	
	
	
	
}
