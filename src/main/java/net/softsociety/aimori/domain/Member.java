package net.softsociety.aimori.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails { 
						// UserDetails 인터페이스 -- Security가 이 VO를 사용할 수 있게 하는 역할
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4457112272113477571L; // 데이터 직렬화
	// 파일을 불러올 때 저장된 데이터와 불러오는 데이터가 같은 데이터인지, 수정되지 않았는지 확인 위한 것
	// 클래스를 구별하기 위한 것
	
	String memberid; 	// 사용자 식별 ID, 유일한 값
	String memberpw; 	// 로그인 시 비밀번호, 필수 입력
	String membername;  // 사용자 이름, 필수 입력
	String email; 		// 이메일 주소
	String phone; 		// 전화번호
	String address; 	// 집 주소
	
	// 인증정보를 담는 변수
	boolean enabled; 	// 계정 상태를 나타냄, 숫자 1자리 ==> but 1, 0은 boolean으로 자동 변환 가능
				 		// 1(true)은 사용 가능한 아이디(기본값)
				 		// 0(false)은 사용 불가능한 아이디
	String rolename; 	// 사용자 종류(일반 사용자 / 관리자)
	
	
// Security가 VO의 변수에 접근하는 메소드들 -- Security는 일반 getter/setter로는 데이터에 접근 못함	
	@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		return this.memberpw;
	}
	@Override
	public String getUsername() {
		return this.memberid;
	}
	
// 계정 상태 구분(ex) 로그인 n일 후 계정 삭제)
	@Override
	public boolean isAccountNonExpired() { 
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
}
