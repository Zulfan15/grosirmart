package com.oopprojek.grosirmart.barang;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "produk")
public class Produk {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public Date getDitambahkanSaat() {
		return ditambahkanSaat;
	}
	public void setDitambahkanSaat(Date ditambahkanSaat) {
		this.ditambahkanSaat = ditambahkanSaat;
	}
	public String getNamaFileFoto() {
		return namaFileFoto;
	}
	public void setNamaFileFoto(String namaFileFoto) {
		this.namaFileFoto = namaFileFoto;
	}
	private String nama;
	private String merk;
	private String kategori;
	private double price;
	
	@Column(columnDefinition = "TEXT")
	private String deskripsi;
	private Date ditambahkanSaat;
	private String namaFileFoto;
	

}
