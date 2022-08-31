package net.softsociety.aimori.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.MemberDAO;
import net.softsociety.aimori.domain.Member;

@Slf4j
@Transactional
@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO mDAO;
	
	/**
	 * 회원 가입
	 */
	@Override
	public int joinMember(Member member) {
		log.debug("[MemberServiceImpl] joinMember : {}", member);
		
		// paramerter로 insert
		int result = mDAO.joinMember(member);
		
		return result;
	}

	@Override
	public Member selectOne(String check) {
		log.debug("[MemberServiceImpl] selectOne - parameter : {}", check);

		// parameter에 해당하는 객체의 데이터 조회
		Member result = mDAO.selectOne(check);
		log.debug("[MemberServiceImpl] selectOne - 조회 결과 : {}", result);
		
		if(result == null) {
			log.debug("[MemberServiceImpl] selectOne - 실패");
		}
		
		return result;
	}

	@Override
	public boolean idCheck(String idCheck) { // selectOne의 결과를 받아오는 method이므로 DAO 필요없음
		log.debug("[MemberServiceImpl] idCheck - parameter : {}", idCheck);

		boolean result;
		
		// parameter에 해당하는 객체의 데이터 조회
		Member member = mDAO.selectOne(idCheck); // 중복 없으면 null
		log.debug("[MemberServiceImpl] idCheck - 조회 결과 : {}", member);
		
		result = member == null ? true : false;
		// 중복이 아니면 return true
		
		return result;
	}

	@Override
	public Member getMemberInfo(String id) {
		log.debug("[MemberServiceImpl] getMemberInfo - 실행됨");
		
		// parameter에 해당하는 객체의 데이터 조회
		return mDAO.selectOne(id);
	}

	@Override
	public int updateInfo(Member member) {
		log.debug("[MemberServiceImpl] updateInfo - parameter : {}", member);
		
		// 전달받은 Memeber와 ID가 일치하는 회원 정보 수정
		int result = mDAO.updateInfo(member);
		
		log.debug("[MemberServiceImpl] updateInfo - update 실패");
		
		return result;
	}
	
	
}
