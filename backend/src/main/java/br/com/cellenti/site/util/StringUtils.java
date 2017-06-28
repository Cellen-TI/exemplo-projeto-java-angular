package br.com.cellenti.site.util;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String[] REPLACES_ACCENTS = { "a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C"};
	public static String[] REPLACES = { "a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C", " ", "", "", "", "" };

	public static Pattern[] PATTERNS_ACCENTS = null;
	public static Pattern[] PATTERNS = null;

	private StringUtils() {
	}

	private static void compilePatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[âãáàä]");
		PATTERNS[1] = Pattern.compile("[éèêë]");
		PATTERNS[2] = Pattern.compile("[íìîï]");
		PATTERNS[3] = Pattern.compile("[óòôõö]");
		PATTERNS[4] = Pattern.compile("[úùûü]");
		PATTERNS[5] = Pattern.compile("[ç]");
		PATTERNS[6] = Pattern.compile("[ÂÃÁÀÄ]");
		PATTERNS[7] = Pattern.compile("[ÉÈÊË]");
		PATTERNS[8] = Pattern.compile("[ÍÌÎÏ]");
		PATTERNS[9] = Pattern.compile("[ÓÒÔÕÖ]");
		PATTERNS[10] = Pattern.compile("[ÚÙÛÜ]");
		PATTERNS[11] = Pattern.compile("[Ç]");
		PATTERNS[12] = Pattern.compile("[\\n\\r]");
		PATTERNS[13] = Pattern.compile("[ªº\\´\\`\\?]");
		PATTERNS[14] = Pattern.compile("[\\&\\-\\?\\,\\?\\!]");
		PATTERNS[15] = Pattern.compile("[\\'/\"\\.\\,]");
		PATTERNS[16] = Pattern.compile("[\\[\\]\\#]");
                
		PATTERNS_ACCENTS = new Pattern[REPLACES_ACCENTS.length];
		PATTERNS_ACCENTS[0] = Pattern.compile("[âãáàä]");
		PATTERNS_ACCENTS[1] = Pattern.compile("[éèêë]");
		PATTERNS_ACCENTS[2] = Pattern.compile("[íìîï]");
		PATTERNS_ACCENTS[3] = Pattern.compile("[óòôõö]");
		PATTERNS_ACCENTS[4] = Pattern.compile("[úùûü]");
		PATTERNS_ACCENTS[5] = Pattern.compile("[ç]");
		PATTERNS_ACCENTS[6] = Pattern.compile("[ÂÃÁÀÄ]");
		PATTERNS_ACCENTS[7] = Pattern.compile("[ÉÈÊË]");
		PATTERNS_ACCENTS[8] = Pattern.compile("[ÍÌÎÏ]");
		PATTERNS_ACCENTS[9] = Pattern.compile("[ÓÒÔÕÖ]");
		PATTERNS_ACCENTS[10] = Pattern.compile("[ÚÙÛÜ]");
		PATTERNS_ACCENTS[11] = Pattern.compile("[Ç]");
	}

	private static synchronized Pattern[] getPatterns() {
		if (PATTERNS == null) {
			compilePatterns();
		}
		return PATTERNS;
	}
        
	private static synchronized Pattern[] getPatternsAccents() {
		if (PATTERNS_ACCENTS == null) {
			compilePatterns();
		}
		return PATTERNS_ACCENTS;
	}
        
        public static String removeCharacter(String text){
		text = StringUtils.trim(text.replaceAll("[^\\d./-]", ""));
                while(text.charAt(0) == '-'){
                        text = text.substring(1);
                }
                return text;
	}

	public static String removeAggregator(String text){
		text = StringUtils.trim(text.replaceAll("([\\(\\)]|[\\[\\]])", ""));
                return text;
	}
	
	/**
	 * Substitui os caracteres acentuados por nao acentuados.
	 */
	public static String replaceSpecial(String text) {
		String result = text;

		if (ValidatorUtil.isNotNullAndNotEmpty(result)) {
			for (int i = 0; i < getPatterns().length; i++) {
				Matcher matcher = getPatterns()[i].matcher(result);
				result = matcher.replaceAll(REPLACES[i]);
			}
		}

		return result;
	}
        
        public static String replaceAccentuation(String text) {
		String result = text;

		if (ValidatorUtil.isNotNullAndNotEmpty(result)) {
			for (int i = 0; i < getPatternsAccents().length; i++) {
				Matcher matcher = getPatternsAccents()[i].matcher(result);
				result = matcher.replaceAll(REPLACES_ACCENTS[i]);
			}
		}

		return result;
	}
        
        public static String replaceAllSpecialWithSpaces(String text) {
                text = StringUtils.replaceSpecial(text);
                text = StringUtils.stripAllWhitespaces(text);
                return replaceAllSpecial(text);
        }
        
        public static String replaceAllSpecial(String text) {
                text = text.replaceAll("=", "");
                text = text.replaceAll(":", "");
                text = text.replaceAll(",", "");
                text = text.replaceAll("\\.", "");
                text = text.replaceAll(";", "");
                text = text.replace("(", "");
                text = text.replace(")", "");
                text = text.replaceAll("\\{", "");
                text = text.replaceAll("\\}", "");
                return text;
        }
	
	public static String stripAllWhitespaces(String txt){
		txt = org.apache.commons.lang.StringUtils.strip(txt); //retira espacos em branco.
		txt = txt.replaceAll(" ", ""); //retira espacos em branco entre as palavras.
		return txt;
	}

	public static String toAscii(String text) {
		String result = text;

		if (result != null) {
			for (int i = 0; i < getPatterns().length - 2; i++) {
				Matcher matcher = getPatterns()[i].matcher(result);
				result = matcher.replaceAll(REPLACES[i]);
			}
		}

		return result;
	}

	public static String convertToUnicode(String str) {

		if (str == null) {
			return null;
		} else {
			StringBuffer ostr = new StringBuffer();

			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if ((ch >= 0x0020) && (ch <= 0x007e)) { // Does the char need to be converted to unicode?
					ostr.append(ch); // No.
				} else { // Yes.
					ostr.append("\\u"); // standard unicode format.
					String hex = Integer.toHexString(str.charAt(i) & 0xFFFF); // Get hex value of the char.
					for (int j = 0; j < 4 - hex.length(); j++)
						// Prepend zeros because unicode requires 4 digits
						ostr.append("0");
					ostr.append(hex.toLowerCase()); // standard unicode format.
					// ostr.append(hex.toLowerCase(Locale.ENGLISH));
				}
			}
			return ostr.toString();
		}

	}

	public static String getFirstNumbers(String palavra) {
		String digitos = "";
		char[] letras = palavra.toCharArray();
		for (char letra : letras) {
			if (Character.isDigit(letra)) {
				digitos += letra;
			} else {
				break;
			}
		}
		return digitos;
	}

	public static String getNumbers(String palavra) {
		String digitos = "";
		char[] letras = palavra.toCharArray();
		for (char letra : letras) {
			if (Character.isDigit(letra)) {
				digitos += letra;
			}
		}
		return digitos;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String removeHtmlSpaces(String input) {
		return input.replaceAll("&nbsp;", " ").replace((char)160, ' ').trim();
	}
        
        public static String trimAndSpecials(String s){
		if(ValidatorUtil.isNotNullAndNotEmpty(s)){
			s = s.trim();
			return StringUtils.replaceSpecial(s);
		}
		return s;
	}
	
	public static String trim(String s){
		if(ValidatorUtil.isNotNullAndNotEmpty(s)){
			return s.trim();
		}
		return s;
	}
        
        /**
	 * Completa a String com espaços até atingir o tamanho passado.
	 * Se o alinhamento não for o esperado lançará {@link InvalidParameterException}.
	 * 
	 * @param original
	 * @param tamanhoTotal
	 * @param alinhamento 1 = alinhar texto a esquerda, 2 = centralizar, 3 = direita
	 * @return Nova string alterada
	 */
	public static String completeWithBlanks(String original, int tamanhoTotal, int alinhamento) {
		if (original.length() >= tamanhoTotal) {
			return original;
		}

		int espacosEsquerda = 0, espacosDireita = 0;

		switch (alinhamento) {
		case 1:
			espacosDireita = tamanhoTotal - original.length();
			break;
		case 3:
			espacosEsquerda = tamanhoTotal - original.length();
			break;
		case 2:
			espacosDireita = (tamanhoTotal - original.length()) / 2;
			espacosEsquerda = espacosDireita + ((tamanhoTotal - original.length()) % 2 == 0 ? 0 : 1);
			break;
		default:
			throw new InvalidParameterException("Alinhamento inválido.");
		}

		StringBuilder builder = new StringBuilder();
		builder.append(multipliesText(" ", espacosEsquerda));
		builder.append(original);
		builder.append(multipliesText(" ", espacosDireita));
		return builder.toString();
	}

	public static String multipliesText(String texto, int vezes) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < vezes; i++) {
			builder.append(texto);
		}
		return builder.toString();
	}
        
}
