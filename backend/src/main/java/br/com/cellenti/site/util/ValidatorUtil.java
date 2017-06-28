package br.com.cellenti.site.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

	public static boolean isNotNullAndNotEmpty(String str) {
		if (str != null) {
			str = str.trim();
		}

		if (str != null && str.length() > 0 && !str.equalsIgnoreCase("null")) {
			return true;
		}

		return false;
	}

	public static boolean isNullOrEmpty(String str) {
		return !isNotNullAndNotEmpty(str);
	}

	public static boolean isNullsAndInvalids(Object... objs) {

		for (Object obj : objs) {

			if (obj != null) {
				if (obj instanceof String) {
					if (isNotNullAndNotEmpty((String) obj)) {
						return false;
					}
				}
				if (obj instanceof Integer) {
					if (isNotNullAndGreaterThanZero((Integer) obj)) {
						return false;
					}
				}
				if (obj instanceof Long) {
					if (obj != null && (Long) obj > 0) {
						return false;
					}
				}
				if (obj instanceof Modelos) {
					if (isNotNullAndValid((Modelos) obj)) {
						return false;
					}
				}
				if (obj instanceof Date) {
					if ((Date) obj != null) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotNullAndPopulated(List lista) {
		if (lista != null && !lista.isEmpty()) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(List lista) {
		return !isNotNullAndPopulated(lista);
	}

	public static boolean isNotNullAndGreaterThanZero(Number number) {
		if (number != null && number.longValue() > 0) {
			return true;
		}

		return false;
	}

	public static boolean isNullOrLessIgualsToZero(Number number) {
		return !isNotNullAndGreaterThanZero(number);
	}

	public static boolean isNotNullAndValid(Modelos model) {
		if (model != null && model.getId() != null) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrInvalid(Modelos model) {
		return !isNotNullAndValid(model);
	}

	public static boolean cpfContemLetras(String cpf) {
		boolean retorno = false;

		if (cpf != null && !cpf.isEmpty()) {
			cpf = cpf.replace(".", "").replace("-", "").replace("/", "");
			retorno = !cpf.matches("^\\d+$");
		}

		return retorno;
	}

	public static boolean isValidCpfCnpjModulo11(String cpfCnpj) {
		if (cpfCnpj != null && !cpfContemLetras(cpfCnpj)) {
			if (cpfCnpj.length() == 14) {
				return validaCPF(cpfCnpj);
			} else if (cpfCnpj.length() == 18) {
				return validaCNPJ(cpfCnpj);
			}
		}
		return false;
	}

	private static boolean validaCNPJ(String strCNPJ) {
		int iSoma = 0, iDigito;
		char[] chCaracteresCNPJ;
		String strCNPJ_Calculado;

		if (!strCNPJ.substring(0, 1).equals("")) {
			try {
				strCNPJ = strCNPJ.replace('.', ' ');
				strCNPJ = strCNPJ.replace('/', ' ');
				strCNPJ = strCNPJ.replace('-', ' ');
				strCNPJ = strCNPJ.replaceAll(" ", "");
				strCNPJ_Calculado = strCNPJ.substring(0, 12);
				if (strCNPJ.length() != 14)
					return false;
				chCaracteresCNPJ = strCNPJ.toCharArray();
				for (int i = 0; i < 4; i++) {
					if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
						iSoma += (chCaracteresCNPJ[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if ((chCaracteresCNPJ[i + 4] - 48 >= 0) && (chCaracteresCNPJ[i + 4] - 48 <= 9)) {
						iSoma += (chCaracteresCNPJ[i + 4] - 48) * (10 - (i + 1));
					}
				}
				iDigito = 11 - (iSoma % 11);
				strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
				/* Segunda parte */
				iSoma = 0;
				for (int i = 0; i < 5; i++) {
					if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
						iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if ((chCaracteresCNPJ[i + 5] - 48 >= 0) && (chCaracteresCNPJ[i + 5] - 48 <= 9)) {
						iSoma += (chCaracteresCNPJ[i + 5] - 48) * (10 - (i + 1));
					}
				}
				iDigito = 11 - (iSoma % 11);
				strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
				return strCNPJ.equals(strCNPJ_Calculado);
			} catch (Exception e) {
				return false;
			}
		} else
			return false;
	}

	private static boolean validaCPF(String strCpf) {
		int iDigito1Aux = 0, iDigito2Aux = 0, iDigitoCPF;
		int iDigito1 = 0, iDigito2 = 0, iRestoDivisao = 0;
		String strDigitoVerificador, strDigitoResultado;

		if (!strCpf.substring(0, 1).equals("")) {
			try {
				strCpf = strCpf.replace('.', ' ');
				strCpf = strCpf.replace('-', ' ');
				strCpf = strCpf.replaceAll(" ", "");
				for (int iCont = 1; iCont < strCpf.length() - 1; iCont++) {
					iDigitoCPF = Integer.valueOf(strCpf.substring(iCont - 1, iCont)).intValue();
					iDigito1Aux = iDigito1Aux + (11 - iCont) * iDigitoCPF;
					iDigito2Aux = iDigito2Aux + (12 - iCont) * iDigitoCPF;
				}
				iRestoDivisao = (iDigito1Aux % 11);
				if (iRestoDivisao < 2) {
					iDigito1 = 0;
				} else {
					iDigito1 = 11 - iRestoDivisao;
				}
				iDigito2Aux += 2 * iDigito1;
				iRestoDivisao = (iDigito2Aux % 11);
				if (iRestoDivisao < 2) {
					iDigito2 = 0;
				} else {
					iDigito2 = 11 - iRestoDivisao;
				}
				strDigitoVerificador = strCpf.substring(strCpf.length() - 2, strCpf.length());
				strDigitoResultado = "" + iDigito1 + iDigito2;
				return strDigitoVerificador.equals(strDigitoResultado);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Compara duas string, retornando a segunda convertida para inteiro, caso sejam diferentes.
	 * 
	 * @param anom String inicial a ser comparada.
	 * @param real String com a alteracao, caso for diferente.
	 * @return Integer
	 */
	public static Integer compararRetornandoIntegerAlteradoOuNulo(String anom, String real) {

		if (ValidatorUtil.compararRetornandoAlteradoOuNulo(anom, real) != null) {
			return Integer.parseInt(real);
		}

		return null;
	}

	/**
	 * Compara duas string, retornando a segunda caso sejam diferentes.
	 * 
	 * @param anom String inicial a ser comparada.
	 * @param real String com a alteracao, caso for diferente.
	 * @return String
	 */
	public static String compararRetornandoAlteradoOuNulo(String anom, String real) {

		if (ValidatorUtil.isNullOrEmpty(anom) || !anom.equalsIgnoreCase(real)) {
			return real;
		}

		return null;
	}
	
	public static boolean validEmail(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	public static boolean containsLetters(String string) {
		if (string != null && !string.isEmpty()) {
			return !string.matches("^\\d+$");
		}
		return false;
	}
	
}
