package com.oopprojek.grosirmart.barang;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

public class ProdukDto {
	@NotEmpty(message = "The name is required")
	private String nama;

	@NotEmpty(message = "The brand is required")
	private String merk;

	@NotEmpty(message = "The name is required")
	private String kategori;

	@Min(0)
	private double price;

	@Size(min = 10, message = "The description should be at least 10 characters")
	@Size(max = 2000, message = "The description cannot exceed 2000 characters")
	private String deskripsi;

	private MultipartFile gambar;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getMerk() {
		return merk;
	}

	public void setMerk(String merk) {
		this.merk = merk;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public MultipartFile getGambar() {
		return gambar;
	}

	public void setGambar(MultipartFile gambar) {
		this.gambar = gambar;
	}

	public Date getDitambahkanSaat() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamaFileFoto() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
