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
	
	// ��ū ���� �޼ҵ�
	public String create(BookUserEntity userEntity) {
		// ������ ���ݺ��� 1�Ϸ� ����
		Date expiryDate = Date.from(
				Instant.now()
				.plus(1, ChronoUnit.DAYS));
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				// payload�� �� ����
				.setSubject(userEntity.getId())
				.setIssuer("my book app")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}
	
	public String validateAndGetUserId(String token) {
		// Claim: ���̷ε忡 ��� �������� ��� ��ü
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
}
