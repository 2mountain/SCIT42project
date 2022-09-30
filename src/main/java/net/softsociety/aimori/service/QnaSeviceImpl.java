package net.softsociety.aimori.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.QnaDAO;
import net.softsociety.aimori.domain.Answer;
import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.util.PageNavigator;

@Transactional
@Service
@Slf4j
public class QnaSeviceImpl implements QnaService {

    @Autowired
    private QnaDAO qnaDAO;
    
    // 글 저장
	@Override
	public int questionInsert(Question question) {
		int result = qnaDAO.questionInsert(question);
		return result;
	}

    //
	@Override
	public PageNavigator getPageNavigator(
			int pagePerGroup, int countPerPage, int page, String type, String searchWord) {
		
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		int total = qnaDAO.countQuestion(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}

	// 글목록 출력
	@Override
	public ArrayList<Question> list(PageNavigator navi, String type, String searchWord) {
		
		HashMap<String, String> map =new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		ArrayList<Question> questionlist = qnaDAO.questionList(map, rb); 
		
		return questionlist;
	}

	// 글읽기
	@Override
	public Question questionRead(int questionNumber) {
		int result = qnaDAO.updateHits(questionNumber);
		Question question = qnaDAO.questionRead(questionNumber);
		return question;
	}
	
	// 글수정
	@Override
	public int questionUpdate(Question question) {
		int result = qnaDAO.questionUpdate(question);
		return result;
	}
	
	// 글삭제
	@Override
	public int questionDelete(Question question) {
		int result = qnaDAO.questionDelete(question);
		return result;	}

	// 답변 등록
	@Override
	public int answerInsert(Answer answer) {
		int result = qnaDAO.answerInsert(answer);
		return result;
	}

	// 답변 개수 +1
	@Override
	public int answerPlus(int questionNumber) {
		int result = qnaDAO.answerPlus(questionNumber);
		return result;
	}

	// 답변 목록 불러오기
	@Override
	public ArrayList<Answer> answerList(int questionNumber) {
		ArrayList<Answer> answerList = qnaDAO.answerList(questionNumber);
		return answerList;
	}

	// 답변 삭제
	@Override
	public int answerDelete(Answer answer) {
		int result = qnaDAO.answerDelete(answer);
		return result;
	}

	// 답변 개수 -1
	@Override
	public int answerMinus(int questionNumber) {
		int result = qnaDAO.answerMinus(questionNumber);
		return result;
	}

	// 답변 채택
	@Override
	public int answerAccept(int questionNumber, int answerNumber) {
		int result = qnaDAO.answerAccept(questionNumber, answerNumber);
		return result;
	}
	


    
	
	
	
	
}
