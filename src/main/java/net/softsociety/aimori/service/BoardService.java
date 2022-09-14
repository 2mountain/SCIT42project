package net.softsociety.aimori.service;

import java.util.ArrayList;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.util.PageNavigator;

public interface BoardService {

	//페이징 정보
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);
	//현재 페이지 글 목록
	public ArrayList<Board> list(PageNavigator navi, String type, String searchWord);
	// 글읽기
	public Board boardRead(int boardNumber);

}
