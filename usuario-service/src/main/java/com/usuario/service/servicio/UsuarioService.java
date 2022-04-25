package com.usuario.service.servicio;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CarroFeignClient carroFeignClient;

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}

	public void deleteUsuarioById(Long id){
		usuarioRepository.deleteById(id);
	}

	public Carro saveCarro(Long usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}

	public Map<String, Object> getUsuarioAndVehiculos(Long usuarioId){
		Map<String,Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

		if(usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}

		resultado.put("Usuario",usuario);
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}
		else {
			resultado.put("Carros", carros);
		}

		return resultado;
	}
}