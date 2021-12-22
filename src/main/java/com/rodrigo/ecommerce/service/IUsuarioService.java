package com.rodrigo.ecommerce.service;

import java.util.Optional;

import com.rodrigo.ecommerce.model.Usuario;

public interface IUsuarioService {
	
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	Optional<Usuario> findByEmail(String email);

}
