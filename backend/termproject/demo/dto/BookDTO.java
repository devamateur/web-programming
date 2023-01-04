package termproject.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import termproject.demo.model.BookEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
	private String id;
	private String title;
	private String author;
	private String publisher;
	private String userId;
	
	public BookDTO(final BookEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor();
		this.publisher = entity.getPublisher();
		this.userId = entity.getUserId();
	}
	
	// BookDTO 객체를 BookEntity로 변환
	public static BookEntity toEntity(final BookDTO dto) {
		if(dto != null)
		return BookEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.author(dto.getAuthor())
				.publisher(dto.getPublisher())
				.userId(dto.getUserId())
				.build();
		return null;
	}
}
