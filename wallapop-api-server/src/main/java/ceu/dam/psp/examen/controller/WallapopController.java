package ceu.dam.psp.examen.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.dam.psp.examen.dto.ArticuloRequestDto;
import ceu.dam.psp.examen.dto.ArticuloResponseDto;
import ceu.dam.psp.examen.dto.LoginRequestDto;
import ceu.dam.psp.examen.dto.UsuarioRequestDto;
import ceu.dam.psp.examen.dto.UsuarioResponseDto;
import ceu.dam.psp.examen.exceptions.ErrorPagoException;
import ceu.dam.psp.examen.exceptions.NoAutorizadoException;
import ceu.dam.psp.examen.exceptions.NoExisteException;
import ceu.dam.psp.examen.exceptions.UsuarioDuplicadoException;
import ceu.dam.psp.examen.model.Articulo;
import ceu.dam.psp.examen.model.Usuario;
import ceu.dam.psp.examen.service.WallapopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallapop")
public class WallapopController {
	
	private final WallapopService service;
	
	WallapopController(WallapopService service){
		this.service = service;
	}
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Operation(summary = "Creando usuario", description = "Usuario creado")
	@PostMapping("/usuarios")
    public UsuarioResponseDto crearUsuario(@Valid @RequestBody UsuarioRequestDto dto) throws UsuarioDuplicadoException {
		
		String passCifrada = DigestUtils.sha512_224Hex(dto.getContraseña());
		Usuario userCreado = service.crearUsuario(dto.getIdentificador(), passCifrada, dto.getDescripcion());		
		
        return modelMapper.map(userCreado, UsuarioResponseDto.class);
    }

	@Operation(summary = "Iniciando sesión al usuario", description = "Usuario Loggeado")
	@PostMapping("/login")
    public UsuarioResponseDto autenticarUsuario(@Valid @RequestBody LoginRequestDto dto) throws NoAutorizadoException {
		
		String passCifrada = DigestUtils.sha512_224Hex(dto.getContraseña());
		Usuario userLog = service.autenticarUsuario(dto.getIdentificador(), passCifrada);
		
        return modelMapper.map(userLog, UsuarioResponseDto.class);
    }

	@Operation(summary = "Consultando al usuario", description = "Usuario consultado")
	@GetMapping("/usuarios")
    public UsuarioResponseDto consultarUsuario(@Valid @RequestParam String id) throws NoExisteException {
        
		Usuario usuario = service.consultarUsuario(id);
		
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }
	
	@Operation(summary = "Consultando el articulo", description = "Articulo consultado")
	@GetMapping("/articulos")
	public ArticuloResponseDto consultarArticulo(@Valid @RequestParam Long codigo) throws NoExisteException {
		
		Articulo articulo = service.consultarArticulo(codigo);
		
		return modelMapper.map(articulo, ArticuloResponseDto.class);
	}
	
	@Operation(summary = "Creando el articulo", description = "Articulo creado")
	@PostMapping("/articulos")
    public Usuario crearArticulo(@Valid @RequestBody ArticuloRequestDto dto) throws NoExisteException {
        
		Usuario usuario = service.consultarUsuario(dto.getIdPropietario());
		
		Articulo artCreado = service.crearArticulo();
		
        return null;
    }

    @Override
    public List<Articulo> buscarArticulos(String filtroNombre, String filtroDescripcion, LocalDate filtroFechaAltaMinima) {
        
    	return null;
    }


    @Override
    public void comprarArticulo(Long codigo) throws ErrorPagoException {
        null;
    }
    
    
    private void cargarDatosPrueba() {
        null;
    }

    
	@Override
	public String consultarApiKey() {
		return null;
	}

}
