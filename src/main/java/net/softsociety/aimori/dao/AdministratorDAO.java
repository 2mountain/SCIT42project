package net.softsociety.aimori.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.DateJoinNumber;
import net.softsociety.aimori.domain.Member;
import net.softsociety.aimori.domain.PetTypeNNumber;
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

	/**
	 * 관리자 페이지의 차트를 위한 일자와 가입자 수를 반환
	 * @return 가입일자 / 일자별 가입자 수
	 */
	public List<DateJoinNumber> getJoinNumber();

	/**
	 * 회원들의 견종을 반환하는 메소드
	 * @return 회원들의 견종 List
	 */
	public List<PetTypeNNumber> getDogType();
}
