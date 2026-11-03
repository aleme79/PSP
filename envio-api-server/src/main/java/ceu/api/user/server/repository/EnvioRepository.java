package ceu.api.user.server.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.api.user.server.model.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long>{
	
    List<Envio> findByEstadoAndTemperaturaCelsiusGreaterThan(String estado, BigDecimal limiteTemperatura);

    Optional<Envio> findByCodigoSeguimiento(String codigoSeguimiento);

    Optional<Envio> findFirstByEstadoOrderByPesoKgDesc(String estado);

}
