package termproject.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;
import termproject.demo.security.MyJwtAuthenticationFilter;

@EnableWebSecurity
@Slf4j
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private MyJwtAuthenticationFilter jwtAuthenticationFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		// http 시큐리티 빌더
		http.cors()          // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
			.and()
			.csrf()
				.disable()
			.httpBasic()   // 우리는 token을 사용하므로 basic 인증 disable 
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/", "/bookauth/**").permitAll()
			.anyRequest()
				.authenticated();
		
		// 필터 등록
		http.addFilterAfter(
			jwtAuthenticationFilter,
			CorsFilter.class
		);
	}
}