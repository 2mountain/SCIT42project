package net.softsociety.aimori.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 설정
 */

@Configuration // 설정값을 제공하는 클래스임을 알림 
//@AllArgsConstructor	// 환경설정 -- @가 있으므로 Spring이 구동시 먼저 해당 객체 생성
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource; // DataSource == DB와의 연결 정보를 가진 객체
    							   // 				(application.properties의 ORACLE 연결 정보)
    
    //설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// HttpSecurity 객체 사용
    	// 사용자가 지정해야 할 것 == 접근 가능 경로, 로그인 화면 경로 지정, 
    	//                        login Form 경로 지정 ,parameter 연결
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers(	// antMatchers( Mapping경로 ) + .permitAll() ==> 해당 경로에 모든 사용자 접근 가능
        		"/",	// home화면 경로
        		"/board/**",
        		"/member/signIn",
        		"/member/idCheck",
        		"/member/nNCheck",
        		"/member/logIn",
        		"/facilities/**",
        		"/weather/**",
        		"/mypage/**",
                "/img/**", // static 폴더의 img, css, js의 모든 폴더는 모든 사용자 접근 가능
                "/css/**",
                "/fonts/**",
                "/js/**",
                "/ckeditor/**/**").permitAll()		//설정한 리소스의 접근을 인증절차 없이 허용
        .antMatchers("/administrator/administrator").access("hasRole('ROLE_ADMIN')") // 관리자용 페이지, 권한 설정
        .anyRequest().authenticated()   	//위의 경로 외에는 모두 로그인을 해야 함
        .and() // 논리연산자의 && 와 같은 역할
        .formLogin()						//일반적인 폼을 이용한 로그인 처리/실패 방법을 사용할 것
        .loginPage("/member/logIn")			//시큐리티에서 제공하는 기본 폼이 아닌 사용자가 만든 폼 사용
        	//★ 로그인 화면의 경로 연결
        .loginProcessingUrl("/member/login").permitAll()	
        	//★ 인증 처리를 하는 URL을 설정. 로그인 폼의 action으로 지정
        	//★ 로그인 폼이 전송될 경로 --> loginForm.html의 form태그의 action으로 전송 받을 경로
        .usernameParameter("memberId")				//로그인폼의 아이디 입력란의 name
        	//★ loginForm.html의 form 태그 내의 input 들의 name 속성과 이름 같아야 함
        .passwordParameter("memberPassword")		//로그인폼의 비밀번호 입력란의 name
	        //★ loginForm.html의 form 태그 내의 input 들의 name 속성과 이름 같아야 함
        .and()
        .logout() // 로그아웃 --> 자동으로 로그인 데이터 삭제
        .logoutSuccessUrl("/").permitAll()	//로그아웃시에 이동할 경로
        .and()
        .cors()
        .and()
        .httpBasic();

        return http.build();
    }

    //인증을 위한 쿼리
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// loginProcessingUrl(form태그의 action을 받는 곳)에서 사용하는 method
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        // 인증 (로그인)
        .usersByUsernameQuery( // 사용자 정보를 DB에서 가져옴
						
			  "select memberId username, memberPassword password, memberEnabled enabled " +
        					// memeberid, memberpw는 사용자가 지정한 변수명에 따라 변경
        					// username, password는 고정 (사용자가 지정한 변수를 넣음)
                "from MEMBERS " + // table명도 사용자의 테이블명에 따라 변경
                "where memberId = ?") // DB에서 보내는 parameter ==> ?
        // 권한
        .authoritiesByUsernameQuery( // 조건에 해당하는 사용자에게 권한 부여
        		"select memberId username, memberRolename role_name " +
                "from MEMBERS " +
                "where memberId = ?");
    }

    // 단방향 비밀번호 암호화 --> 단방향이므로 DB관리자도 암호화된 비밀번호의 원래 값 모름
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
