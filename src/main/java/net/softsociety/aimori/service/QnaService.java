package net.softsociety.aimori.service;

import java.util.ArrayList;

import net.softsociety.aimori.domain.Question;
import net.softsociety.aimori.util.PageNavigator;

public interface QnaService {
	
	// 글 저장
	public int questionInsert(Question question);
	//페이징 정보
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);
	//현재 페이지 글 목록
	public ArrayList<Question> list(PageNavigator navi, String type, String searchWord);

	

}
