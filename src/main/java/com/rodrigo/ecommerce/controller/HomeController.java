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
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOG.info("Producto añadido: {}",optionalProducto.get());
		LOG.info("Cantidad: {}",cantidad);
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setProducto(producto);
		
		//Validar si el producto es el mismo
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		if(!ingresado) {
			detalles.add(detalleOrden);
		}
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	//Quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Integer id, Model model) {
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden: detalles) {
			if(detalleOrden.getProducto().getId() != id) {
				ordenesNueva.add(detalleOrden);
			}
		}
		
		//Crear la nueva lista con los productos restantes
		detalles=ordenesNueva;
		
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getcart")
	public String getCart(Model model) {
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}
	
	@GetMapping("/orden")
	public String orden() {
		return "usuario/resumenorden";
	}

}
