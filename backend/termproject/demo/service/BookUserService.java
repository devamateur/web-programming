package termproject.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import termproject.demo.model.BookUserEntity;
import termproject.demo.persistence.BookUserRepository;

@Slf4j
@Service
public class BookUserService {
	@Autowired
	private BookUserRepository userRepository;
	
	public BookUserEntity create(final BookUserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String email = userEntity.getEmail();
		
		// 동일한 이메일 주소가 db에 저장되어 있는지 확인
		if(userRepository.existsByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("Email already exists");
		}
		
		return userRepository.save(userEntity);
	}
	
	// email과 password로 BookUserEntity를 검색
	public BookUserEntity getByCredentials(final String email, final String password,
			final PasswordEncoder encoder) {
		final BookUserEntity originalUser = userRepository.findByEmail(email); // db에 저장된 user
		if(originalUser != null && 
				encoder.matches(password, originalUser.getPassword()))  // encoder.matches로 기존 user인지 확인
			return originalUser;
		return null;
	}
}
