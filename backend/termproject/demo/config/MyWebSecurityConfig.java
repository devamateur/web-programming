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
		// http ��ť��Ƽ ����
		http.cors()          // WebMvcConfig���� �̹� ���������Ƿ� �⺻ cors ����
			.and()
			.csrf()
				.disable()
			.httpBasic()   // �츮�� token�� ����ϹǷ� basic ���� disable 
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/", "/bookauth/**").permitAll()
			.anyRequest()
				.authenticated();
		
		// ���� ���
		http.addFilterAfter(
			jwtAuthenticationFilter,
			CorsFilter.class
		);
	}
}