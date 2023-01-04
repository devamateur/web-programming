package termproject.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration  // ���� ���� �޼ҵ�� Bean ���� �޼ҵ带 �����ϴ� Ŭ�������� ���
public class MyWebMvcConfig implements WebMvcConfigurer{
	private final long MAX_AGE_SECS = 3600;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// ��� ��ο� ���
		registry.addMapping("/**")
			// Origin�� http:localhost:3000(����Ʈ����)�� ����
			.allowedOrigins("http://localhost:3000")
			// Get, POST, PUT, PATCH, DELETE, OPTIONS �޼ҵ带 ���
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

}