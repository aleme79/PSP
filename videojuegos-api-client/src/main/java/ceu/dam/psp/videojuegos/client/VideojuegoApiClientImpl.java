package ceu.dam.psp.videojuegos.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;


import ceu.dam.psp.videojuegos.exceptions.ApiException;
import ceu.dam.psp.videojuegos.exceptions.NotFoundException;
import ceu.dam.psp.videojuegos.model.Videojuego;
import tools.jackson.databind.ObjectMapper;

public class VideojuegoApiClientImpl implements VideojuegoApiClient{
	
	private String urlBase = "https://ca0a1e3fd17dbc62ecf3.free.beeceptor.com/api/videojuego";
	private HttpClient httpClient;
	
	public VideojuegoApiClientImpl(String uuidUrl) { 
		urlBase = String.format(urlBase, uuidUrl);
		httpClient = HttpClient.newHttpClient();
	}
	
	@Override
	public Videojuego findById(String id) throws NotFoundException, ApiException {
		try {
			//.GET --> Petición de obtener o leer
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + id)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();
			System.out.println("Codigo respuesta: " + status);
			if (status == 200) {

				return new ObjectMapper().readValue(response.body(), Videojuego.class);

			} else if (status == 404) {
				throw new NotFoundException("No existe ese coche");
			}
			throw new ApiException("Codigo de respuesta erroneo");

		} catch (Exception e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new ApiException("Error consultando API videojuegos", e);
		}
	}

	@Override
	public List<Videojuego> findByAñoPublicacion(Integer año) throws NotFoundException, ApiException {
		try {
			//.GET --> Petición de obtener o leer
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "?año=" + año)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			
			int status = response.statusCode();
			
			if(status == 200) {
				Videojuego[] array = new ObjectMapper().readValue(response.body(), Videojuego[].class);
				List<Videojuego> lista = Arrays.asList(array);
				
				if(lista.isEmpty()) {
					throw new NotFoundException("No existe ese videojuego");
				} else if (status == 404) {
					throw new NotFoundException("No existe ese videojuego con ese año");
				}
			}
			throw new ApiException("Error en la API. Código del estado: " + status);
			
		} catch (Exception e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new ApiException("Error consultando API videojuegos", e);
		}
	}

	@Override
	public String create(Videojuego videojuego) throws ApiException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(videojuego);
			
			//.POST --> Petición de enviar, crear o actualizar 
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			
			int status = response.statusCode();
			
			if(status == 200) {
				Videojuego videojuegoCreado = new ObjectMapper().readValue(response.body(), Videojuego.class);
				return videojuegoCreado.getId();
			}
			
			throw new NotFoundException("Error creando el videojuego. " + status);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException("Error consultando API videojuegos", e);
		}
	}

	@Override
	public void update(Videojuego videojuego) throws NotFoundException, ApiException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(videojuego);
			
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();
			
			if(status == 200) {
				return;
			}else if (status == 404) {
				throw new NotFoundException("No se ha encontrado el videojuego con ID " + videojuego.getId() + " para actualizar");
			}
			
			throw new NotFoundException("Error actualizando el videojuego. " + status);
			
		} catch (NotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ApiException("Error de comunicación intentando actualizar el videojuego", e);
	    }
	}

	@Override
	public void delete(String id) throws NotFoundException, ApiException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + id))
					.DELETE()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	        int status = response.statusCode();
	        
	        if(status == 200) {
				return;
			}else if (status == 404) {
				throw new NotFoundException("No se ha encontrado el videojuego con ID " + id + " para actualizar");
			}
	        
	        throw new ApiException("Error borrando el videojuego. Código del servidor: " + status);
	        
		} catch (NotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ApiException("Error de comunicación intentando borrar el videojuego", e);
	    }
	}

}
