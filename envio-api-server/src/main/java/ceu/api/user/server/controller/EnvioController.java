package ceu.api.user.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.api.user.server.model.Envio;
import ceu.api.user.server.service.EnvioNoEncontradoException;
import ceu.api.user.server.service.EnviosService;
import ceu.api.user.server.service.EstadoEnvioInvalidoException;
import ceu.api.user.server.service.PoliticaTransporteInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/envios")
public class EnvioController {
	
	private final EnviosService service;

	EnvioController(EnviosService service) {
		this.service = service;
	}
	
	@Operation(summary = "Detectar envíos en riesgo térmico", description = "...")
	@GetMapping("/riesgo-termico")
	public List<Envio> detectarEnviosEnRiesgoTermico(@RequestParam double limiteTemperatura) {
    	return service.detectarEnviosEnRiesgoTermico(limiteTemperatura);
    }
	
	@Operation(summary = "Actualizar estado de lote reciente", description = "...")
	@PutMapping("/lote-reciente")
	public long actualizarEstadoLoteReciente(@RequestBody List<String> codigos, @RequestParam String nuevoEstado) throws EnvioNoEncontradoException {
		return service.actualizarEstadoLoteReciente(codigos, nuevoEstado);
	}
	
	@Operation(summary = "Registrar nuevo envío", description = "...")
	@PostMapping
	public Envio registrarNuevoEnvio(Envio nuevoEnvio) throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		return service.registrarNuevoEnvio(nuevoEnvio);
	}
	
	@Operation(summary = "Obtener envío pesado prioritario", description = "...")
	@GetMapping("/prioritario")
	public Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		return service.obtenerEnvioPesadoPrioritario();
	}

}
