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
@RequestMapping("book")  // book으로 url 매핑
public class BookController {
	@Autowired
	private BookService service;
	
	// createBook(): 책 추가
	// @ResponseEntity: 응답 시, 객체뿐만 아니라 status, header 등을 전달
	@PostMapping
	public ResponseEntity<?> createBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto){
		try {
			//String userId = "Jo Mi Jeong";
			
			// BookEntity로 변환
			BookEntity entity = BookDTO.toEntity(dto);
			
			// id를 null로 초기화(생성 당시에는 id가 없어야 하기 때문)
			entity.setId(null);
			
			// 임시 사용자 id 설정
			entity.setUserId(userId);
			
			// 서비스를 이용해 BookEntity 생성
			List<BookEntity> entities = service.create(entity);
			
			// 자바 스트림을 이용해 BookEntity 리스트를 리턴하고 
			// 이를 BookDTO 리스트로 변환
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			
			// BookDTO 리스트를 이용해 ResponseDTO 초기화
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			// status code와 ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
			
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
		
	// retrieveBook(): 책 검색
	@GetMapping
	public ResponseEntity<?> retrieveBook(@AuthenticationPrincipal String userId){
	
		// 해당 유저가 등록한 도서
		List<BookEntity> entitiesByUserId = service.retrieve(userId);
		//entity.setUserId(userId);
		
		// 유저가 등록한 도서 중 
		// title값을 기준으로 엔터티 리스트를 가져옴 
		List<BookEntity> entities = new ArrayList<>();
		for(int i=0; i<entitiesByUserId.size(); i++) {
			entities.addAll(service.retrieveTitle(entitiesByUserId.get(i).getTitle()));
		}
		//System.out.println("책 제목은: " + entity.getTitle());
		//List<BookEntity> entities = service.retrieve(userId);
			
		// 자바 스트림을 이용해 entity 리스트를 BookDTO 리스트로 변환
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
					
		// ResponseDTO를 BookDTO로 초기화
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
		// status code와 ResponseDTO를 리턴
		return ResponseEntity.ok().body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		//String userId = "Jo Mi Jeong";
		
		// dto를 entity로 변환
		BookEntity entity = BookDTO.toEntity(dto);
		
		// id를 temporary id로 초기화
		entity.setUserId(userId);
		
		// 서비스를 이용해 entity 업데이트
		List<BookEntity> entities = service.update(entity);
		
		// 자바 스트림을 이용해 위의 리스트를 TodoDTO 리스트로 변환
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());

		// 반환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();

		// ResponseDTO 리턴
		return ResponseEntity.ok().body(response);		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto){
		try {
			//String userId = "Jo Mi Jeong";
			
			// BookEntity로 변환
			BookEntity entity = BookDTO.toEntity(dto);
			
			// 사용자 id 설정
			entity.setUserId(userId);
			
			// 서비스를 이용해 entity 삭제
			List<BookEntity> entities = service.delete(entity);
			
			// 자바 스트림을 이용해 BookDTO 리스트로 변환
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			
			// ResponseDTO 초기화
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		
		} catch(Exception e) {
			String error = e.getMessage();
			
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);

		}
	}
}
