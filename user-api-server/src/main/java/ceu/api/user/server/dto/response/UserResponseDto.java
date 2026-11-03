package ceu.api.user.server.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponseDto {
	
	private Long id;
	private String username;
	private String email;
	private String name;
	private LocalDate createdDate;

}
