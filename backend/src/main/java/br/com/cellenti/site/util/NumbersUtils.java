package br.com.cellenti.site.util;

import java.math.BigDecimal;

public class NumbersUtils {

    /**
	 * Converte um long para um BigDecimal, onde os dois ultimos numeros da direita eh representado pelo decimal.
	 * Ex: 
	 * <ul>
	 * 	<li>Long: "123456" --> BigDecimal: 1.234,56</li>
	 * 	<li>Long: "369" --> BigDecimal: 3,69</li>
	 *  <li>Long: "12" --> BigDecimal: 0,12</li>
	 *  <li>Long: "4" --> BigDecimal: 0,04</li>
	 * </ul>
	 */
	public static BigDecimal parseDecimal(Long valor) {
		return parseDecimal("" + valor);
	}

	/**
	 * Converte uma string numerica para um BigDecimal, onde os dois ultimos caracteres da direita eh representado pelo decimal.
	 * Ex: 
	 * <ul>
	 * 	<li>String: "123456" --> BigDecimal: 1.234,56</li>
	 * 	<li>String: "369" --> BigDecimal: 3,69</li>
	 *  <li>String: "12" --> BigDecimal: 0,12</li>
	 *  <li>String: "4" --> BigDecimal: 0,04</li>
	 * </ul>
	 * 
	 */
	public static BigDecimal parseDecimal(String valor) {
		return new BigDecimal(parseDecimalToString(valor));
	}

	private static String parseDecimalToString(String valor) {
		valor = StringUtils.replaceSpecial(valor);

		Integer tamanho = valor.length();
		String decimal = valor;
		String inteiro = "0";

		if (tamanho > 2) {
			Integer idxDecimal = tamanho - 2;

			inteiro = valor.substring(0, idxDecimal);
			decimal = valor.substring(idxDecimal);
		}

		if (decimal.length() == 1) {
			decimal = "0" + decimal;
		}

		return inteiro + "." + decimal;
	}

	/**
	 * Transoforma uma string em bigDecimal considerando que as ultimas duas casas são decimais.
	 * Exemplos de entradas e saídas:
	 * 		"123456" 	---> 1234.56
	 * 		"369" 		---> 3.69
	 * 		"12" 		---> 0.12
	 * 		"4" 		---> 0.04
	 * 		"0" 		---> 0.00
	 * 		"-1" 		---> 0.01
	 * 		"" 			---> 0.00
	 * 		"null" 		---> 0.00
	 * 		"abc" 		---> 0.00
	 */
	public static BigDecimal convertStringToBigDecimal(String str) {
		return convertStringToBigDecimal(str, 2);
	}

	public static BigDecimal convertStringToBigDecimal(String str, int numeroCasasDecimais) {

		if (numeroCasasDecimais < 1 || str == null)
			return BigDecimal.ZERO;

		str = str.replaceAll("\\D+", "");

		while (str.length() <= numeroCasasDecimais) {
			str = "0" + str;
		}

		str = str.substring(0, (str.length() - numeroCasasDecimais)) + "." + str.substring(str.length() - numeroCasasDecimais);

		BigDecimal bd = new BigDecimal(str);

		return bd;
	}

	/**
	 * Transforma um BigDecimal em String contendo somente digitos onde os dois ultimos digitos pertence à casa de decimais.
	 * Observação: Utilizado o modo BigDecimal.ROUND_HALF_UP de arredondamento.
	 * Exemplos de entradas e saídas:
	 * 		1234.56 ---> 123456
	 * 		3.69 ---> 369
	 * 		0.12 ---> 012
	 * 		0.04 ---> 004
	 * 		0.00 ---> 000
	 * 		0.01 ---> 001
	 * 		0.00 ---> 000
	 * 		0.00 ---> 000
	 * 		0.00 ---> 000
	 * 		100.2185452487509991215119953267276287078857421875 ---> 10022
	 */
	public static String convertBigDecimalToStringSemFormat(BigDecimal bd) {
		return convertBigDecimalToStringSemFormat(bd, 2);
	}

	public static String convertBigDecimalToStringSemFormat(BigDecimal bd, int numeroCasasDecimais) {
		if (bd == null)
			bd = BigDecimal.ZERO;
		bd = bd.setScale(numeroCasasDecimais, BigDecimal.ROUND_HALF_UP);
		return bd.toString().replaceAll("\\D+", "");
	}

	public static String convertBigDecimalToStringFormat(Double d) {
		String str = convertBigDecimalToStringSemFormat(new BigDecimal(""+d) );
		str = str.replaceAll("\\D+", "");
		str = formatarNumeroDecimal(str);
		return str;
	}
        
	public static String convertBigDecimalToStringFormat(BigDecimal bd) {
		String str = convertBigDecimalToStringSemFormat(bd);
		str = str.replaceAll("\\D+", "");
		str = formatarNumeroDecimal(str);
		return str;
	}

	public static String formatarNumeroDecimal(String str) {
		return formatarNumeroDecimal(str, 2);
	}

	/**
	 * Formata uma string de números adicionando as vírgulas e os pontos:
	 * Exemplo com entradas e saídas (com 2 e 4 casas decimais);
	 * 		123456		---> 1.234,56	---> 12,3456
	 * 		369			---> 3,69		---> 369
	 * 		012			---> 0,12		---> 012
	 * 		004			---> 0,04		---> 004
	 * 		101000		---> 1.010,00	---> 10,1000
	 * 		0000		---> 00,00		---> 0000
	 * 		000			---> 0,00		---> 000
	 * 		0			---> 0			---> 0
	 * 		2			---> 2			---> 2
	 * 		001			---> 0,01		---> 001
	 * 		78857421875	---> 788.574.218,75		---> 7.885.742,1875
	 */
	private static String formatarNumeroDecimal(String str, int numeroCasasDecimais) {
		String strF = "";
		int c = 1, m = 0;
		for (int i = str.length(); i >= 1; i--) {
			strF = str.charAt(i - 1) + strF;

			m++;

			// o número adicionado é o 2º da da direita para esquerda, então adicionar vircula.
			if (c == numeroCasasDecimais && i > 1) {
				strF = "," + strF;
				m = 0;
			}

			if (m == 3 && i > 1) {
				if (c > numeroCasasDecimais) //  Para evitar --> 707.885.742,1.875
					strF = "." + strF;
				m = 0;
			}
			c++;
		}
		return strF;
	}
        
        /**
	 * Coloca zeros (0) a esquerda
	 * 
	 * @param valor {@link String} original
	 * @param tam Tamanho da {@link String} final
	 * @return {@link String} final
	 */
	public static String leftFillZeros(String valor, int tam) {
		valor = valor.replaceAll(",", "");
		valor = valor.replaceAll("\\.", "");
		int size = valor.length();
		if (size < tam) {
			valor = generateZeros(tam - size) + valor;
		} else if (size > tam) {
			valor = valor.substring(0, tam);
		}
		return valor;
	}

	/**
	 * Gera uma {@link String} com uma certa quantidade de zeors
	 * 
	 * @param qtd
	 * @return {@link String}
	 */
	public static String generateZeros(int qtd) {
		String zeros = "";
		for (int i = 0; i < qtd; i++)
			zeros += "0";
		return zeros;
	}

	/**
	 * Forca uma conversao retirando possiveis zeros antes de um numero.
	 */
	public static String removeNumericalBlanks(String numero) {
		try {
			// forca uma conversao para retirar zeros antes
			numero = "" + (Long.parseLong(numero));
		} catch (NumberFormatException ex) {
			// continua com o mesmo numero string
		}
		return numero;
	}
}
