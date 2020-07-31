package com.myapp.pojo;

public class SchedulazioneModel {

	private int id;
	private String giorno;
	private String mese;
	private String anno;

	private int userid;
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "SchedulazioneModel: id=" + id + ", giorno=" + giorno + ", mese=" + mese + ", anno=" + anno + " userid=" + userid + ", username=" + username;
	}
}
