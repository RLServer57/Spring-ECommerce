package com.rodrigo.ecommerce.service;

import java.util.List;

import com.rodrigo.ecommerce.model.Orden;

public interface IOrdenService {

	List<Orden> findAll();
	Orden save(Orden orden);
	
}
