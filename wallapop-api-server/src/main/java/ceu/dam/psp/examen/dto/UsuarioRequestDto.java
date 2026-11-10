package ceu.dam.psp.examen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDto {
	
	@NotBlank(message = "El identificador no puede venir en blanco")
    @Size(max = 30, message = "El identificador tiene como máximo longitud 30")
    private String identificador;

    @NotBlank(message = "La descripción no puede venir en blanco")
    private String descripcion;

    @NotBlank(message = "La contraseña no puede venir en blanco")
    @Size(min = 10, message = "La contraseña tiene como mínimo longitud 10")
    private String contraseña;

}
