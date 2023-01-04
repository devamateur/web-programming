package termproject.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import termproject.demo.model.BookUserEntity;

@Slf4j
@Service
public class BookTokenProvider {
private static final String SECRET_KEY = "NMA8JPctFuna59f5";
	
	// 토큰 생성 메소드
	public String create(BookUserEntity userEntity) {
		// 기한은 지금부터 1일로 설정
		Date expiryDate = Date.from(
				Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				// payload에 들어갈 내용
				.setSubject(userEntity.getId())
				.setIssuer("my book app")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}
	
	public String validateAndGetUserId(String token) {
		// Claim: 페이로드에 담긴 정보들을 담는 객체
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
}
