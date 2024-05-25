package com.oopprojek.grosirmart.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopprojek.grosirmart.barang.Produk;

public interface ProdukRepository extends JpaRepository<Produk, Integer> {
	 
}
