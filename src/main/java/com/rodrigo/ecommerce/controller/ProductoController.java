package com.rodrigo.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rodrigo.ecommerce.model.Producto;
import com.rodrigo.ecommerce.model.Usuario;
import com.rodrigo.ecommerce.service.IProductoService;
import com.rodrigo.ecommerce.service.IUsuarioService;
import com.rodrigo.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private UploadFileService upload;

	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}

	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}

	@PostMapping("/save")
	public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
		LOGGER.info("Este es el objeto {}", producto);
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		producto.setUsuario(usuario);

		// Imagen
		if (producto.getId() == null) { // Cuando se crea un producto
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}

		productoService.save(producto);
		return "redirect:/productos";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		Optional<Producto> optional = productoService.get(id);
		producto = optional.get();

		LOGGER.info("Producto buscado: {}", producto);
		model.addAttribute("producto", producto);

		return "productos/edit";
	}

	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		Producto p = new Producto();
		p = productoService.get(producto.getId()).get();

		if (file.isEmpty()) { // Cuando editamos pero no cambiamos la imagen
			producto.setImagen(p.getImagen());
		} else { // Cuando se edita la imagen
			
			// Eliminar la imagen excepto default.jpg
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}

			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}

		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {

		Producto p = new Producto();
		p = productoService.get(id).get();

		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}

		productoService.delete(id);
		return "redirect:/productos";
	}

}
