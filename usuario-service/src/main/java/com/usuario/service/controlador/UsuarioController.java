package com.usuario.service.controlador;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/getall")
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") Long id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
		Usuario usuarioPorActualizar = usuarioService.getUsuarioById(id);
		if(usuarioPorActualizar == null) {
			return ResponseEntity.notFound().build();
		}
		usuarioPorActualizar.setEmail(usuario.getEmail());
		Usuario usuarioActualizado = usuarioService.save(usuarioPorActualizar);
		return ResponseEntity.ok(usuarioActualizado);
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id){
		Usuario usuarioPorEliminar = usuarioService.getUsuarioById(id);
		if(usuarioPorEliminar == null) {
			return ResponseEntity.notFound().build();
		}

		usuarioService.deleteUsuarioById(id);
		return ResponseEntity.ok(usuarioPorEliminar);
	}

	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") Long usuarioId, @RequestBody Carro carro){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}

	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") Long usuarioId){
		Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
}