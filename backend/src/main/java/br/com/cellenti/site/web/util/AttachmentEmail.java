package br.com.cellenti.site.web.util;

/**
 * Classe generica para anexos de emails.
 */
public class AttachmentEmail {

	private String name;
	private byte[] bytes;

	public AttachmentEmail(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
	}

	public String getName() {
		return name;
	}

	public byte[] getBytes() {
		return bytes;
	}

}
