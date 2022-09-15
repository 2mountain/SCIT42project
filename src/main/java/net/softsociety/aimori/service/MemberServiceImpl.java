package net.softsociety.aimori.service;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	
	private final JavaMailSenderImpl JavaMailSenderImpl = new JavaMailSenderImpl();
	
	public void mailSend(HttpSession session, String memberEmail) {
		try {
			MailHandler mailHandler = new MailHandler(JavaMailSenderImpl);
			Random random = new Random(System.currentTimeMillis());
			long start = System.currentTimeMillis();
			
			int result = 100000 + random.nextInt( 900000);
			
			// 받는 사람
			mailHandler.setTo(memberEmail);
			// 보내는 사람
			mailHandler.setFrom("dirkgos1994@naver.com");
			// 제목
			mailHandler.setSubject("인증번호입니다.");
			// HTML Layout
			String htmlContent = "<p>인증번호 : + "+result+"<p>";
			mailHandler.setText(htmlContent, true);
			
			
			mailHandler.send();
			
			long end = System.currentTimeMillis();
			
			session.setAttribute(memberEmail, result);
			System.out.println("실행 시간 : " + (end - start)/1000.0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean emailCertification(HttpSession session, String memberEmail, int inputCode) {
		try {
			int generationCode = (int) session.getAttribute(memberEmail);
			
			if(generationCode == inputCode) {
				return true;
			}else {
				return false;
			}		
		
		}catch (Exception e) {
			throw e;
		}
	}
	
}
