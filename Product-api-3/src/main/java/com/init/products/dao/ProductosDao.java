package com.init.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.products.entitys.Productos;

public interface ProductosDao extends JpaRepository <Productos, Integer>{
	
}
