package br.com.cellenti.site.servicos;

import br.com.cellenti.site.web.util.AttachmentEmail;
import br.com.cellenti.site.web.util.EmailBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServico {
        
        @Autowired
        private ParametrosSistema parametrosSistema;
        
        @Autowired
	private JavaMailSender mailSender;

	public void send(final EmailBase emailBase) {
                if (emailBase == null) {
                        throw new IllegalStateException("Objeto email nulo!");
                }

                emailBase.applyDefaultErrors();

                if (emailBase.hasErrors()) {
                        throw new IllegalStateException("Email com problema de informações faltando: " + emailBase.getErrors());
                }

		if (parametrosSistema.isDesenvolvimento()) {
                        String to = emailBase.getTo() != null && !emailBase.getTo().isEmpty() && emailBase.getTo().size() == 1 ? "_" + emailBase.getTo().get(0) : "";
                        String filename = System.getProperty("user.home") + File.separator + "email_cellenti_" + (new Date()).getTime() + to + ".html";
                        System.out.println(" --> ARQUIVANDO EMAIL: " + filename);

                        try (FileOutputStream file = new FileOutputStream(filename, true);
                                OutputStreamWriter stream = new OutputStreamWriter(file, StandardCharsets.UTF_8);
                                PrintWriter out = new PrintWriter(stream, true);) {
                                out.println(emailBase.getContent(parametrosSistema.getUrlFront()));
                        } catch (IOException e) {
                                System.out.println("Erro na escrita do email para arquivo"+e.getMessage());
                        }
                } else {
                        MimeMessagePreparator preparator = this.prepare(emailBase);
                        try {
                                System.out.println("Enviando email para " + preparator);
                                mailSender.send(preparator);
                                System.out.println("Email enviado sem erros para " + preparator);
                        } catch (Exception e) {
                                System.out.println("Erro ao enviar email"+e.getMessage());
                                System.out.println(preparator.toString());
                                throw e;
                        }
                }
        }

	private MimeMessagePreparator prepare(final EmailBase emailBase) {
		return new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(emailBase.getArrayTo());
				message.setCc(emailBase.getArrayCc());
				message.setBcc(emailBase.getArrayBcc());
                                message.setText(emailBase.getContent(parametrosSistema.getUrlFront()), true);
				message.setSubject(emailBase.getSubject());
				message.setSentDate(new Date());

				if (StringUtils.isNotBlank(parametrosSistema.getEmailFromAddress())) {
					if (StringUtils.isNotBlank(parametrosSistema.getEmailFromName())) {
						message.setFrom(new InternetAddress(parametrosSistema.getEmailFromAddress(), parametrosSistema.getEmailFromName()));
					} else {
						message.setFrom(new InternetAddress(parametrosSistema.getEmailFromAddress()));
					}
				}

				if (StringUtils.isNotBlank(emailBase.getReplyTo())) {
					message.setReplyTo(emailBase.getReplyTo());
				}

				if (!emailBase.getAttachments().isEmpty()) {
					for (AttachmentEmail attachment : emailBase.getAttachments()) {
						message.addAttachment(attachment.getName(), new ByteArrayResource(attachment.getBytes()));
					}
				}
			}
		};
	}

}
