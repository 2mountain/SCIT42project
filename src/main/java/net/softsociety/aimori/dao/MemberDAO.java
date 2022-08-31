package net.softsociety.aimori.dao;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.aimori.domain.Member;

@Mapper
public interface MemberDAO {
	/**
	 * 회원 가입
	 * @param member
	 * @return Member
	 */
	public int joinMember(Member member);

	/**
	 * String으로 1개 데이터 조회
	 * @param String id
	 * @return Member
	 */
	public Member selectOne(String id);

	/**
	 * 전달받은 객체로 회원정보 수정
	 * @param member / 수정할 회원정보 객체
	 * @return 1 || 0
	 */
	public int updateInfo(Member member);
}
