package ceu.api.user.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ceu.api.user.server.model.Envio;
import ceu.api.user.server.repository.EnvioRepository;

@Service
public class EnviosServiceImpl {
	
	private final EnvioRepository repo;

	EnviosServiceImpl(EnvioRepository repo) {
		this.repo = repo;
	}
	
	/**
     * 1. PROCESAMIENTO DE ALERTAS CLIMÁTICAS
     * Busca todos los envíos en tránsito que hayan superado un límite de temperatura crítico.
     * 
     * CASUÍSTICA DE EXCEPCIÓN:
     * - Si el límite de temperatura proporcionado es absurdo para la cadena de frío 
     *   (ej. inferior a -50°C o superior a 50°C), lanza IllegalArgumentException.
     * 
     * @param limiteTemperatura Grados Celsius máximos permitidos.
     * @return Lista de envíos que están en riesgo.
     * @throws IllegalArgumentException si la temperatura está fuera de rangos lógicos de control.
     */
    public List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura) {
    	
    	if(limiteTemperatura < -50 || limiteTemperatura > 50) {
    		throw new IllegalArgumentException("Error, límite de temperatura superado");
    	}
    	
    	return repo.findByEstadoAndTemperaturaCelsiusGreaterThan(null, null)
    }
    
    

}
