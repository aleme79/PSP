package ceu.dam.psp.examen.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ArticuloResponseDto {
	
	private Long codigo;
	private String nombre;
	private String detalles;
	private LocalDate fechaPublicacion;
	private Double precio;
	private String categoria;
	private String idPropietario;
	private Integer estado;

}
