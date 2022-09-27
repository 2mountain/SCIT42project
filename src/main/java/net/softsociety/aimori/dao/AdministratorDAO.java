package net.softsociety.aimori.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.ReportedBoard;

@Mapper
public interface AdministratorDAO {
	/**
	 * 모든 회원들의 정보를 가져옴 
	 * @return 회원들 정보 목록
	 */
	public List<Member> getMemberList();
	
	/**
	 * 해당 회원의 차단 여부 변경
	 * @param member
	 * @return boolean
	 */
	public int changeBlock(Member member);

	/**
	 * 해당 회원의 권한 변경
	 * @param member
	 * @return
	 */
	public int changeRole(Member member);

	/**
	 * 신고된 글 전체 출력
	 * @return 신고된 글 리스트
	 */
	public List<ReportedBoard> getReportedBoard();
}
