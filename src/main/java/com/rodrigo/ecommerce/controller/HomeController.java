package com.rodrigo.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rodrigo.ecommerce.model.DetalleOrden;
import com.rodrigo.ecommerce.model.Orden;
import com.rodrigo.ecommerce.model.Producto;
import com.rodrigo.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	//Almacena los detalles de compra
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	
	//Datos de la orden
	Orden orden = new Orden();
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos",productoService.findAll());
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		LOG.info("Id enviado: {}",id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad) {
		DetalleOrden detalle = new DetalleOrden();
		Producto producto = new Producto();
		double subTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOG.info("Producto añadido: {}",optionalProducto.get());
		LOG.info("Cantidad: {}",cantidad);
		
		return "usuario/carrito";
	}

}
