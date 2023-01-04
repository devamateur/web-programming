package termproject.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Book")
public class BookEntity {
	@Id 
	// id자동으로 생성
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")  // system-uuid라는 generator를 생성, uuid라는 문자열 이용
	private String id;
	private String title;
	private String author;
	private String publisher;
	private String userId;
}
