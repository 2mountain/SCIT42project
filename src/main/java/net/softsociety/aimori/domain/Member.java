package net.softsociety.aimori.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 (시큐리티를 사용한 회원 인증에 사용)
 * UserDetails 인터페이스를 implements
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
	private static final long serialVersionUID = 3222388532456457383L;

	String memberId;		// 회원 아이디
	String memberPassword;	// 회원 비밀번호
	String memberNickName;	// 회원 닉네임
	String memberPostCode;	// 회원 우편번호
	String memberAddress;	// 회원 주소
	String memberDetailAddress;	// 회원 상세주소
	String memberEmail; 	// 회원 이메일
	String memberBirthDay;	// 회원 생년월일
	int memberPoint;		// 회원 포인트
	String memberRoleName;	// 회원 역할
	int memberEnabled;		// 회원 차단 여부
	String memberJoinDate;	// 회원 가입일
	String memberImageOriginalFile;	// 대표 이미지의 원래 이름
	String memberImageSavedFile;	//이미지 첨부파일 서버에 저장된 이름
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
