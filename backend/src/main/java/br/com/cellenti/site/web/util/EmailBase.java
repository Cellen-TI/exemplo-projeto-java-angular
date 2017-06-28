package br.com.cellenti.site.web.util;

import br.com.cellenti.site.util.FormatadorUtil;
import br.com.cellenti.site.util.ValidatorUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class EmailBase implements Serializable {

	private static final long serialVersionUID = -2229946654366392287L;

	/**
	 * Lista de destinatários do email.
	 */
	private final List<String> to = new ArrayList<>();

	/**
	 * Lista de destinatários em cópia oculta do email
	 */
	private final List<String> bcc = new ArrayList<>();

	/**
	 * Lista de destinatários em cópia do email
	 */
	private final List<String> cc = new ArrayList<>();

	/**
	 * Email para o qual será respondido o email enviado
	 */
	private String replyTo;

	/**
	 * Assunto do email. Este campo irá para o subject do email enviado.
	 */
	private String subject;

	/**
	 * Titulo do template (_head.html).
	 */
	private String title;

	/**
	 * Conteúdo do email
	 */
	private String page;

	/**
	 * Dados a serem substituidos no conteudo final do html.
	 */
	private Map<String, String> data = new LinkedHashMap<>();

	/**
	 * Lista de arquivos a serem anexados no email.
	 */
	private List<AttachmentEmail> attachments = new ArrayList<AttachmentEmail>();

	/**
	 * Lista de erros de email. Deve ser usado em desenvolvimento para garantir que a aplicação não tentará enviar emails sem informações mínimas como TO, Subject, etc.
	 */
	private final List<String> errors = new ArrayList<>();

	/**
	 * Busca por problemas no email que inviabilizariam o envio do mesmo.
	 */
	public void applyDefaultErrors() {
		if (ValidatorUtil.isNullOrEmpty(this.to) && ValidatorUtil.isNullOrEmpty(this.cc) && ValidatorUtil.isNullOrEmpty(this.bcc)) {
			this.errors.add("Email sem destinatários: " + this);
		}
		if (ValidatorUtil.isNullOrEmpty(this.subject)) {
			this.errors.add("Email sem subject: " + this);
		}
	}

	public boolean hasErrors() {
		return this.errors != null && !this.errors.isEmpty();
	}

	public String getContent(String urlApi) {
		try {
			String head = FormatadorUtil.readResourceFile("/email/_head.html");
			String footer = FormatadorUtil.readResourceFile("/email/_footer.html");
			String page = FormatadorUtil.readResourceFile("/email/" + this.page);

			String content = head + page + footer;
			for (String key : data.keySet()) {
				String value = data.get(key);
				content = content.replaceAll("\\{" + key + "\\}", value);
			}

			content = content.replaceAll("\\{urlBase\\}", urlApi);
			content = content.replaceAll("\\{title\\}", this.title);
			return content;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void addData(String key, String value) {
		data.put(key, value);
	}

	protected void addTo(String to) {
		this.to.add(to);
	}

	protected void addBcc(String bcc) {
		this.bcc.add(bcc);
	}

	protected void addCc(String cc) {
		this.cc.add(cc);
	}

	protected void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	protected void setSubject(String subject) {
		this.subject = subject;
	}

	protected void setPage(String page) {
		this.page = page;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getArrayTo() {
		return this.to.toArray(new String[this.to.size()]);
	}

	public List<String> getTo() {
		return to;
	}

	public String[] getArrayBcc() {
		return this.bcc.toArray(new String[this.bcc.size()]);
	}

	public List<String> getBcc() {
		return bcc;
	}

	public String[] getArrayCc() {
		return this.cc.toArray(new String[this.cc.size()]);
	}

	public List<String> getCc() {
		return cc;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getSubject() {
		return subject;
	}

	public List<String> getErrors() {
		return errors;
	}

	public List<AttachmentEmail> getAttachments() {
		return attachments;
	}

}
