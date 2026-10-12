package ceu.dam.psp.videojuegos.test;


import java.util.List;

import ceu.dam.psp.videojuegos.client.VideojuegoApiClientImpl;
import ceu.dam.psp.videojuegos.exceptions.ApiException;
import ceu.dam.psp.videojuegos.exceptions.NotFoundException;
import ceu.dam.psp.videojuegos.model.Videojuego;

public class Test {

	public static void main(String[] args) {
		VideojuegoApiClientImpl api = new VideojuegoApiClientImpl("test-uuid");

		try {
			System.out.println("--- INICIANDO PRUEBAS DE LA API ---");

			// 1. Probar CREATE
			System.out.println("\n1. Creando nuevo videojuego...");
			Videojuego juegoNuevo = new Videojuego();
			juegoNuevo.setNombre("The Last of Us Part I");
			juegoNuevo.setAñoPublicacion(2022);
			juegoNuevo.setPaisOrigen("EEUU");
			juegoNuevo.setValoracion(9.5);

			// Enviamos por POST y recogemos el ID
			String idGenerado = api.create(juegoNuevo);
			juegoNuevo.setId(idGenerado); // Se lo asignamos al objeto local para futuras operaciones
			System.out.println("✅ Éxito. ID asignado por el servidor: " + idGenerado);

			
			// 2. Probar FIND BY ID
			System.out.println("\n2. Buscando videojuego por ID (" + idGenerado + ")...");
			Videojuego juegoBuscado = api.findById(idGenerado);
			System.out.println("✅ Encontrado: " + juegoBuscado.getNombre() + " (Valoración: " + juegoBuscado.getValoracion() + ")");

			
			// 3. Probar UPDATE
			System.out.println("\n3. Actualizando videojuego...");
			juegoNuevo.setValoracion(10.0); // Le subimos la nota
			api.update(juegoNuevo);
			System.out.println("✅ Videojuego actualizado correctamente.");

			
			// 4. Probar FIND BY AÑO
			System.out.println("\n4. Buscando videojuegos del año 2022...");
			List<Videojuego> lista = api.findByAñoPublicacion(2022);
			System.out.println("✅ Se encontraron " + lista.size() + " videojuegos de ese año.");
			for (Videojuego v : lista) {
				System.out.println("   - " + v.getNombre());
			}

			
			// 5. Probar DELETE
			System.out.println("\n5. Borrando el videojuego...");
			api.delete(idGenerado);
			System.out.println("✅ Videojuego borrado correctamente.");

			
			// 6. Comprobar que realmente se ha borrado
			System.out.println("\n6. Comprobando el borrado (debería lanzar NotFoundException)...");
			api.findById(idGenerado);
			
			// Si llega a esta línea, es que NO ha lanzado la excepción de borrado (algo fallaría en Beeceptor)
			System.out.println("❌ ERROR: El videojuego sigue existiendo.");

		} catch (NotFoundException e) {
			// Si el paso 6 lanza esta excepción, es que el programa funciona a la perfección
			System.out.println("✅ Comprobación exitosa, excepción capturada: " + e.getMessage());
			
		} catch (ApiException e) {
			System.err.println("❌ Fallo en la comunicación con la API: " + e.getMessage());
			e.printStackTrace();
			
		} catch (Exception e) {
			System.err.println("❌ Fallo inesperado: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
