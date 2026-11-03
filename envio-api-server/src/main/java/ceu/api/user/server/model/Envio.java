package ceu.api.user.server.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "envios")
public class Envio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo_seguimiento")
	private String codSeguimiento;
	
	@Column(name = "fecha_registro")
	private LocalDate fechaRegistro;
	
	@Column(name = "peso_kg")
	private BigDecimal peso;
	
	
	private String estado;
	
	@Column(name = "temperatura_celsius")
	private BigDecimal temperatura;
	

}
