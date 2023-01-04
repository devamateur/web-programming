package termproject.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import termproject.demo.model.BookUserEntity;


public interface BookUserRepository extends JpaRepository<BookUserEntity, String>{
	BookUserEntity findByEmail(String email);
	Boolean existsByEmail(String email);
	BookUserEntity findByEmailAndPassword(String email, String password);
}
