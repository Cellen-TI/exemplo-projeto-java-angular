package br.com.cellenti.site.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Properties;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import org.apache.commons.io.IOUtils;

/**
 * Formatacao e conversao de valores diversos.
 */
public final class FormatadorUtil {
	
	/**
	 * Retorna o valor de uma chave do arquivo "messages.properties".
	 */
	public static String getMessage(String key, String... args) {

		try {
			InputStream istream = FormatadorUtil.class.getClassLoader().getResourceAsStream("messages.properties");
			if (istream != null) {
				Properties props = new Properties();
				props.load(istream);
                                
                                String value = props.getProperty(key.trim());
                                
                                if(args!=null && args.length > 0){
                                        return MessageFormat.format(value, args);
                                }
                                
				return value;
			}
			return key;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getMessage(String file, String key) {

		try {
			InputStream istream = FormatadorUtil.class.getClassLoader().getResourceAsStream(file);
			if (istream != null) {
				Properties props = new Properties();
				props.load(istream);
				return props.getProperty(key.trim());
			}
			return key;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encryptMD5(String text) {
		return encryptMD5(text.getBytes());
	}
	
	public static String encryptMD5(byte[] text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text);
			BigInteger hash = new BigInteger(1, md.digest());
			return org.apache.commons.lang.StringUtils.leftPad(hash.toString(16), 32, '0');
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}
        
        public static String readResourceFile(String filename) throws IOException {
		InputStream in = FormatadorUtil.class.getResourceAsStream(filename);
		if (in == null) {
			throw new FileNotFoundException("Arquivo '" + filename + "' inexistente em '/class/resources/'.");
		}
		return IOUtils.toString(in, Charset.forName("UTF-8"));
	}

}
