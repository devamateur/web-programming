package termproject.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration  // 설정 관련 메소드와 Bean 생성 메소드를 제공하는 클래스임을 명시
public class MyWebMvcConfig implements WebMvcConfigurer{
	private final long MAX_AGE_SECS = 3600;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 모든 경로에 대애
		registry.addMapping("/**")
			// Origin이 http:localhost:3000(프론트엔드)에 대해
			.allowedOrigins("http://localhost:3000")
			// Get, POST, PUT, PATCH, DELETE, OPTIONS 메소드를 허용
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

}