package ceu.dam.psp.examen.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
	
	@NotBlank
	private String identificador;
	
	@NotBlank
	private String contraseña;

}
