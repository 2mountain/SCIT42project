package net.softsociety.aimori.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import net.softsociety.aimori.domain.Board;
import net.softsociety.aimori.util.PageNavigator;

/**
 * 게시판 관련 매퍼 인터페이스
 */
@Mapper
public interface BoardDAO {
    // 글 저장
	public int insertBoard(Board board);
	// 현재 페이지 글 목록
	public ArrayList<Board> boardList(HashMap<String, String> map, RowBounds rb);
	// 전체 글 개수
	public int countBoard(HashMap<String, String> map);
	// 글 읽기
	public Board boardRead(int boardNumber);
	// 글 삭제
	public int boardDelete(Board board);
	// 글 수정
	public int boardUpdate(Board board);

}
