package ceu.api.test.server.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Producto {
	
	private String codBarras;
	private String descripcion;
	private BigDecimal precio;
	private LocalDate fechaCaducidad;

}
