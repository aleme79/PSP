package ceu.api.user.server.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceu.api.user.server.model.Envio;
import ceu.api.user.server.repository.EnvioRepository;

@Service
public class EnviosServiceImpl implements EnviosService{
	
	private final EnvioRepository repo;

	EnviosServiceImpl(EnvioRepository repo) {
		this.repo = repo;
	}
	
	@Override
    public List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura) {
    	
    	if(limiteTemperatura < -50 || limiteTemperatura > 50) {
    		throw new IllegalArgumentException("Error, límite de temperatura superado");
    	}
    	
    	return repo.findByEstadoAndTemperaturaCelsiusGreaterThan(limiteTemperatura);
    }
	
	@Override
	@Transactional(rollbackFor = EnvioNoEncontradoException.class)
	public long actualizarEstadoLoteReciente(List<String> codigos, String nuevoEstado)
			throws EnvioNoEncontradoException {
		
		if(codigos.isEmpty()) {
			throw new IllegalArgumentException("Error, la lista de códigos está vacía");
		}
		
		Integer contadorAct = 0;
		//LocalDate hace24Horas = LocalDate.now().minusDays(1);
		LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);
		
		for (String codigo : codigos) {
			Envio envio = repo.findByCodigoSeguimiento(codigo)
					.orElseThrow(() -> new EnvioNoEncontradoException("No existe ningún envío con el código: " + codigo));
			
			if (envio.getFechaRegistro() != null && envio.getFechaRegistro().isAfter(hace24Horas)) {
                envio.setEstado(nuevoEstado);
                repo.save(envio);
                contadorAct++;
            }
		}
		return contadorAct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Envio registrarNuevoEnvio(Envio nuevoEnvio)
			throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		
		if(nuevoEnvio.getPeso().compareTo(BigDecimal.valueOf(1500)) > 0) {
			throw new PoliticaTransporteInvalidaException("Error, límite máximo de los camiones ligeros de la flota");
		}
		
		if(!nuevoEnvio.getEstado().equals("REGISTRADO") && !nuevoEnvio.getEstado().equals("EN_ALMACEN")) {
			throw new EstadoEnvioInvalidoException("Error, estado no válido");
		}
		
		String codigoGenerado = "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		nuevoEnvio.setCodSeguimiento(codigoGenerado);
		if(nuevoEnvio.getFechaRegistro() == null) {
			nuevoEnvio.setFechaRegistro(LocalDateTime.now());
		}
		
		return repo.save(nuevoEnvio);
	}
	
	@Override
	public Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		
		return repo.findFirstByEstadoOrderByPesoKgDesc("PENDIENTE")
				.orElseThrow(() -> new EstadoEnvioInvalidoException("No existen envíos en estado PENDIENTE"));
	}

}
