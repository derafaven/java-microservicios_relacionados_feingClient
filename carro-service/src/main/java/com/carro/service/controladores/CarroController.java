package com.carro.service.controladores;

import java.util.List;

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

import com.carro.service.entidades.Carro;
import com.carro.service.servicios.CarroService;

@RestController
@RequestMapping("/api/carro")
public class CarroController {

	@Autowired
	private CarroService carroService;
	
	@GetMapping("/getall")
	public ResponseEntity<List<Carro>> listarCarros(){
		List<Carro> carros = carroService.getAll();
		if(carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carros);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") Long id){
		Carro carro = carroService.getCarroById(id);
		if(carro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carro);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
		Carro nuevoCarro = carroService.save(carro);
		return ResponseEntity.ok(nuevoCarro);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") Long id){
		List<Carro> carros = carroService.byUsuarioId(id);
		if(carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carros);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id, @RequestBody Carro carro){
		Carro carroPorActualizar = carroService.getCarroById(id);
		if(carroPorActualizar == null) {
			return ResponseEntity.notFound().build();
		}
		carroPorActualizar.setMarca(carro.getMarca());
		carroPorActualizar.setModelo(carro.getModelo());
		Carro carroActualizado = carroService.save(carroPorActualizar);
		return ResponseEntity.ok(carroActualizado);
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<Carro> eliminarCarro(@PathVariable Long id){
		Carro carroPorEliminar = carroService.getCarroById(id);
		if(carroPorEliminar == null) {
			return ResponseEntity.notFound().build();
		}

		carroService.deleteCarroById(id);
		return ResponseEntity.ok(carroPorEliminar);
	}
}