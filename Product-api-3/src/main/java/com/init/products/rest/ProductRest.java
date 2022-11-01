package com.init.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductosDao;
import com.init.products.entitys.Productos;

@RestController // es para avisar q la clase va a ser servicio Rest
@RequestMapping("Productos") // en que url se van a exponer los serviciois y metodos de esta api

public class ProductRest {

	@Autowired
	private ProductosDao productosDao;

	@GetMapping
	public ResponseEntity<List<Productos>> getProductos() { // traer todos
		List<Productos> productos = productosDao.findAll(); // jpa
		return ResponseEntity.ok(productos);
	}

	@RequestMapping(value = "{productoPorId}", method = RequestMethod.GET) // traer 1
	public ResponseEntity<Productos> buscarProductoPorId(@PathVariable("productoPorId") Integer productoABuscar) {
		Optional<Productos> productoOptional = productosDao.findById(productoABuscar);
		if (productoOptional.isPresent()) {
			return ResponseEntity.ok(productoOptional.get());
		} else {
			System.out.println(noExiste());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Productos> crearUnProducto(@RequestBody Productos producto) { // postear
		Productos nuevoProducto = productosDao.save(producto);
		return ResponseEntity.ok(nuevoProducto);
	}

	@PutMapping
	public ResponseEntity<Productos> modificarUnProducto(@RequestBody Productos producto) { // modificar
		Optional<Productos> productoTemporal = productosDao.findById(producto.getId());
		if (productoTemporal.isPresent()) {
			Productos productoModificado = productoTemporal.get();
			productoModificado.setNombre(producto.getNombre());
			productosDao.save(productoModificado);
			return ResponseEntity.ok(productoModificado);
		} else {
			System.out.println(noExiste());
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Void> eliminarUnProducto(@RequestBody Productos productoABorrar) { // borrar
		Optional<Productos> buscarProducto = productosDao.findById(productoABorrar.getId());
		if (buscarProducto.isPresent()) {
			productosDao.deleteById(productoABorrar.getId());
			return ResponseEntity.ok(null);
		} else {
			System.out.println(noExiste());
			return ResponseEntity.notFound().build();
		}
	}

	// @GetMapping // localhost: 8080 - servicio disponible mediante el metodo Get
	// @RequestMapping(value = "hello", method= RequestMethod.GET) se define en que
	// url esta el servicio y que metodo responde a este, la dif con GetMapping se
	// ejecuta con la raiz requestmapping arriba, en cambio en requesmapping se hace
	// con '/hello'

	public String noExiste() {
		return "No existe ese producto";
	}
}
