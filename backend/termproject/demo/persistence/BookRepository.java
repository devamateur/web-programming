package termproject.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import termproject.demo.model.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
	List<BookEntity> findByUserId(String userId);
	List<BookEntity> findByTitle(String title);
}
