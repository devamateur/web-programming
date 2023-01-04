package termproject.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import termproject.demo.dto.BookDTO;
import termproject.demo.dto.ResponseDTO;
import termproject.demo.model.BookEntity;
import termproject.demo.service.BookService;

@RestController
@RequestMapping("book")  // book���� url ����
public class BookController {
	@Autowired
	private BookService service;
	
	// createBook(): å �߰�
	// @ResponseEntity: ���� ��, ��ü�Ӹ� �ƴ϶� status, header ���� ����
	@PostMapping
	public ResponseEntity<?> createBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto){
		try {
			//String userId = "Jo Mi Jeong";
			
			// BookEntity�� ��ȯ
			BookEntity entity = BookDTO.toEntity(dto);
			
			// id�� null�� �ʱ�ȭ(���� ��ÿ��� id�� ����� �ϱ� ����)
			entity.setId(null);
			
			// �ӽ� ����� id ����
			entity.setUserId(userId);
			
			// ���񽺸� �̿��� BookEntity ����
			List<BookEntity> entities = service.create(entity);
			
			// �ڹ� ��Ʈ���� �̿��� BookEntity ����Ʈ�� �����ϰ� 
			// �̸� BookDTO ����Ʈ�� ��ȯ
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			
			// BookDTO ����Ʈ�� �̿��� ResponseDTO �ʱ�ȭ
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			// status code�� ResponseDTO�� ����
			return ResponseEntity.ok().body(response);
			
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
		
	// retrieveBook(): å �˻�
	@GetMapping
	public ResponseEntity<?> retrieveBook(@AuthenticationPrincipal String userId){
	
		// �ش� ������ ����� ����
		List<BookEntity> entitiesByUserId = service.retrieve(userId);
		//entity.setUserId(userId);
		
		// ������ ����� ���� �� 
		// title���� �������� ����Ƽ ����Ʈ�� ������ 
		List<BookEntity> entities = new ArrayList<>();
		for(int i=0; i<entitiesByUserId.size(); i++) {
			entities.addAll(service.retrieveTitle(entitiesByUserId.get(i).getTitle()));
		}
		//System.out.println("å ������: " + entity.getTitle());
		//List<BookEntity> entities = service.retrieve(userId);
			
		// �ڹ� ��Ʈ���� �̿��� entity ����Ʈ�� BookDTO ����Ʈ�� ��ȯ
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
					
		// ResponseDTO�� BookDTO�� �ʱ�ȭ
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
		// status code�� ResponseDTO�� ����
		return ResponseEntity.ok().body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		//String userId = "Jo Mi Jeong";
		
		// dto�� entity�� ��ȯ
		BookEntity entity = BookDTO.toEntity(dto);
		
		// id�� temporary id�� �ʱ�ȭ
		entity.setUserId(userId);
		
		// ���񽺸� �̿��� entity ������Ʈ
		List<BookEntity> entities = service.update(entity);
		
		// �ڹ� ��Ʈ���� �̿��� ���� ����Ʈ�� TodoDTO ����Ʈ�� ��ȯ
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());

		// ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO �ʱ�ȭ
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();

		// ResponseDTO ����
		return ResponseEntity.ok().body(response);		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto){
		try {
			//String userId = "Jo Mi Jeong";
			
			// BookEntity�� ��ȯ
			BookEntity entity = BookDTO.toEntity(dto);
			
			// ����� id ����
			entity.setUserId(userId);
			
			// ���񽺸� �̿��� entity ����
			List<BookEntity> entities = service.delete(entity);
			
			// �ڹ� ��Ʈ���� �̿��� BookDTO ����Ʈ�� ��ȯ
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			
			// ResponseDTO �ʱ�ȭ
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		
		} catch(Exception e) {
			String error = e.getMessage();
			
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);

		}
	}
}
