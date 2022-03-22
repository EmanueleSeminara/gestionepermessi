package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nomefile")
	private String nomeFile;

	@Column(name = "contenttype")
	private String contentType;

	@Column(name = "payload")
	private byte[] payload;

	public Attachment() {
		super();
	}

	public Attachment(Long id, String nomeFile, String contentType, byte[] payload) {
		super();
		this.id = id;
		this.nomeFile = nomeFile;
		this.contentType = contentType;
		this.payload = payload;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

}
