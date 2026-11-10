package ceu.dam.psp.examen.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticuloRequestDto {
	
	private Long codigo;
	
	@NotBlank
	@Size(max = 30)
	private String nombre;
	
	@NotBlank
	private String detalles;
	
	private LocalDate fechaPublicacion;
	
	@NotNull
	@Size(min = 1)
	private Double precio;
	
	@NotBlank
	@Size(max = 30)
	private String categoria;
	
	@NotBlank
	@Schema(description = "Id del propietario")
	private String idPropietario;
	
	private Integer estado;

}
