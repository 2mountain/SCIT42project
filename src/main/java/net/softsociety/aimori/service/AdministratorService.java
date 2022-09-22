package net.softsociety.aimori.service;

import java.util.List;

import net.softsociety.aimori.domain.Member;

public interface AdministratorService {
	/**
	 * 모든 회원들의 정보를 가져옴 
	 * @return 회원들 정보 목록
	 */
	public List<Member> getMemberList();
}
