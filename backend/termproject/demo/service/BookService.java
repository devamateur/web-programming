package termproject.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import termproject.demo.model.BookEntity;
import termproject.demo.persistence.BookRepository;

@Slf4j
@Service
public class BookService {
	@Autowired
	private BookRepository repository;
	
	// repository에 entity를 save하고 저장된 엔터티를 리턴
	public List<BookEntity> create(final BookEntity entity){
		validate(entity);
		
		// repository에 저장
		repository.save(entity);
		log.info("Entity ID: {} is saved", entity.getId());
		
		// repository에 저장된 entity를 리턴
		return repository.findByUserId(entity.getUserId());
	}
	
	// entity를 검색
	public List<BookEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	// entity를 title값으로 검색
	public List<BookEntity> retrieveTitle(final String title){
		return repository.findByTitle(title);
	}
	
	// entity 검증
	private void validate(final BookEntity entity) {
		if(entity == null) {  // entity가 null일 경우
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
	
	// 수정
	public List<BookEntity> update(final BookEntity entity){
		validate(entity);
		
		// 넘겨받은 엔터티 id를 이용해 BookEntity를 가져옴
		final Optional<BookEntity> original = repository.findById(entity.getId());
		
		// retrieve에서 만든 메소드를 이용해 모든 Book 리스트를 리턴
		original.ifPresent( book -> {
			// BookEntity 값이 존재하면 새로운 entity값으로 수정
			book.setTitle(entity.getTitle());
			book.setAuthor(entity.getAuthor());
			book.setPublisher(entity.getPublisher());
			
			// 데이터베이스에 새 값 저장
			repository.save(book);
		});
		
		return retrieveTitle(entity.getTitle());
	}

	// 삭제
	public List<BookEntity> delete(final BookEntity entity){
		validate(entity);
		
		try {
			
			// 엔터티 삭제
			repository.delete(entity);
		} catch(Exception e) {
			log.error("error deleting entity", entity.getId(), e);
			
			// 컨트롤러로 exception을 보낸다
			throw new RuntimeException("error deleting entity" +  entity.getId());
		}
		
		// 새 Book 리스트를 가져와서 리턴
		return retrieve(entity.getUserId());
	}
	/*public String testService() {
		// BookEntity 생성
		BookEntity entity = BookEntity.builder().title("The Great Gatsby").build();
		// BookEntity 저장
		repository.save(entity);
		// BookEntity 검색
		BookEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}*/
}
