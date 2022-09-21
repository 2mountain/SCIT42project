package net.softsociety.aimori.service;

import net.softsociety.aimori.domain.Member;

public interface MemberService {
	// 회원가입
	public int signInMember(Member member);
	
	// ID 중복확인
	public boolean idCheck(String cId);
	
	// 닉네임 중복확인
	public boolean nNCheck(String cNN);
	
	// 마이페이지에 유저 정보 가져오기
	public Member getMemberInfo(String username);

	// 마이페이지 개인정보 수정
	public int updateMember(Member member);
	
	
}

