package termproject.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import termproject.demo.dto.BookUserDTO;
import termproject.demo.dto.ResponseDTO;
import termproject.demo.model.BookUserEntity;
import termproject.demo.security.BookTokenProvider;
import termproject.demo.service.BookUserService;

@Slf4j
@RestController
@RequestMapping("/bookauth")
public class BookUserController {
	@Autowired
	private BookUserService userService;
	
	@Autowired
	private BookTokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	// ȸ������
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody BookUserDTO userDTO){
		try {
			// ��û�� �̿��� ������ ����� �����
			BookUserEntity user = BookUserEntity.builder()
					.email(userDTO.getEmail())
					.username(userDTO.getUsername())
					.password(passwordEncoder.encode(userDTO.getPassword()))
					.build();
			//System.out.println(user.getEmail());
			// ���񽺸� �̿��� �������丮�� ����� ����
			BookUserEntity registeredUser = userService.create(user);
			BookUserDTO responseUserDTO = BookUserDTO.builder()
					.email(registeredUser.getEmail())
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			
			return ResponseEntity.ok().body(responseUserDTO);
		} catch(Exception e) {
			
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	// �α���
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody BookUserDTO userDTO){
		BookUserEntity user = userService.getByCredentials(
				userDTO.getEmail(), 
				userDTO.getPassword(),
				passwordEncoder);
		
		if(user != null) {   // ������ ������� ���
			// ��ū ����
			final String token = tokenProvider.create(user);
			final BookUserDTO responseUserDTO = BookUserDTO.builder()
					.email(user.getEmail())
					.id(user.getId())
					.token(token)
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		}
		else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login failed.")
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
