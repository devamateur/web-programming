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
	
	// repository�� entity�� save�ϰ� ����� ����Ƽ�� ����
	public List<BookEntity> create(final BookEntity entity){
		validate(entity);
		
		// repository�� ����
		repository.save(entity);
		log.info("Entity ID: {} is saved", entity.getId());
		
		// repository�� ����� entity�� ����
		return repository.findByUserId(entity.getUserId());
	}
	
	// entity�� �˻�
	public List<BookEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	// entity�� title������ �˻�
	public List<BookEntity> retrieveTitle(final String title){
		return repository.findByTitle(title);
	}
	
	// entity ����
	private void validate(final BookEntity entity) {
		if(entity == null) {  // entity�� null�� ���
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
	
	// ����
	public List<BookEntity> update(final BookEntity entity){
		validate(entity);
		
		// �Ѱܹ��� ����Ƽ id�� �̿��� BookEntity�� ������
		final Optional<BookEntity> original = repository.findById(entity.getId());
		
		// retrieve���� ���� �޼ҵ带 �̿��� ��� Book ����Ʈ�� ����
		original.ifPresent( book -> {
			// BookEntity ���� �����ϸ� ���ο� entity������ ����
			book.setTitle(entity.getTitle());
			book.setAuthor(entity.getAuthor());
			book.setPublisher(entity.getPublisher());
			
			// �����ͺ��̽��� �� �� ����
			repository.save(book);
		});
		
		return retrieveTitle(entity.getTitle());
	}

	// ����
	public List<BookEntity> delete(final BookEntity entity){
		validate(entity);
		
		try {
			
			// ����Ƽ ����
			repository.delete(entity);
		} catch(Exception e) {
			log.error("error deleting entity", entity.getId(), e);
			
			// ��Ʈ�ѷ��� exception�� ������
			throw new RuntimeException("error deleting entity" +  entity.getId());
		}
		
		// �� Book ����Ʈ�� �����ͼ� ����
		return retrieve(entity.getUserId());
	}
	/*public String testService() {
		// BookEntity ����
		BookEntity entity = BookEntity.builder().title("The Great Gatsby").build();
		// BookEntity ����
		repository.save(entity);
		// BookEntity �˻�
		BookEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}*/
}
