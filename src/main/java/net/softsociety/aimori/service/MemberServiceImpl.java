package net.softsociety.aimori.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.aimori.dao.MemberDAO;
import net.softsociety.aimori.domain.Member;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public int signInMember(Member member) {
		String encodePassword = passwordEncoder.encode(member.getMemberPassword());
		member.setMemberPassword(encodePassword);
		
		int result = memberDAO.signInMember(member);
		
		return result;
	}
	
	@Override
	public boolean idCheck(String cId) {
		
		boolean result = false;
		
		Member member = memberDAO.selectOne(cId);
		result = member == null ? true : false;
		
		return result;
	}
	
	@Override
	public boolean nNCheck(String cNN) {
		
		boolean result = false;
		
		Member member = memberDAO.selectOne2(cNN);
		log.debug("?:{} ",member);
		result = member == null ? true : false;
		
		return result;
	}
}
