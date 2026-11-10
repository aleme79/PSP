package ceu.dam.psp.examen.dto;

import java.time.LocalDate;
import java.util.List;

import ceu.dam.psp.examen.model.Articulo;
import lombok.Data;

@Data
public class UsuarioResponseDto {
	
	private String identificador;
    private String descripcion;
    private LocalDate fechaAlta;
    private LocalDate fechaUltimoAcceso;
    private List<Articulo> catalogo;

}
