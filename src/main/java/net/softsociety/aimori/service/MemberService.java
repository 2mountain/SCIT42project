package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Member;

public interface MemberService {
	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public int joinMember(Member member);

	/**
	 * String을 parameter로 1개 데이터 조회
	 * @param String id
	 * @return Member
	 */
	public Member selectOne(String id);

	/**
	 * ID 중복 확인
	 * @param idCheck
	 * @return boolean (return true if DB has same ID)
	 */
	public boolean idCheck(String idCheck);

	/**
	 * 전달받은 ID로 회원정보 조회
	 * @param id / 조회할 ID
	 * @return 해당 회원 정보
	 */
	public Member getMemberInfo(String id);

	/**
	 * 전달받은 객체로 회원정보 수정
	 * @param member / 수정할 회원정보 객체
	 * @return 1 || 0
	 */
	public int updateInfo(Member member);
	

}
