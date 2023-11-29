package com.simplepicpay.shared;

import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.swing.text.MaskFormatter;

public final class StringUtil {
	private StringUtil() {
		// Empty
	}

	public static String onlyNumbers(String str) {
		if (!StringUtil.isNullOrEmpty(str)) {
			return str.replaceAll("[^0123456789]", "");
		} else {
			return "";
		}
	}

	public static String firstCharacterToLower(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public static boolean isNumber(String n) {
		if (StringUtil.isNullOrEmpty(n)) {
			return false;
		}
		return n.trim().matches("[0-9]+");
	}

	/**
	 * Verify if the provided text is null or empty.
	 *
	 * @param text Text to be verified.
	 * @return boolean Returns <code>true</code> if the text is null or empty,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmpty(String text) {
		return (text == null || text.trim().length() == 0 || text.trim().isEmpty());
	}

	/**
	 * Verify if some of the texts is null or empty.
	 *
	 * @param text Text to be verified.
	 * @return boolean Returns <code>true</code> if some of the texts is null or empty,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmpty(String... texts) {
		for (String text : texts) {
			if (StringUtil.isNullOrEmpty(text)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verify if the provided text is equals to any position of the array.
	 *
	 * @param text  Text to be verified.
	 * @param texts Array containing the texts to verify.
	 * @return <code>true</code> if the text argument is equals to any text of the
	 *         array, <code>false</code> otherwise.
	 */
	public static boolean equalsIgnoreCase(String text, String... texts) {
		if (StringUtil.isNullOrEmpty(text) || texts == null || texts.length == 0) {
			return false;
		}
		for (String t : texts) {
			if (text.equalsIgnoreCase(t)) {
				return true;
			}
		}

		return false;
	}

	public static String randomString(int size) {
		String VALID_CHARS = "abcdefghijklmnopqrstuvwyz0123456789";
		StringBuffer toReturn = new StringBuffer();
		String temp = UUID.randomUUID().toString();
		for (int i = 0; i < temp.length(); i++) {
			String c = temp.substring(i, i + 1);
			if (VALID_CHARS.contains(c.toLowerCase())) {
				toReturn.append(c);
			}
			if (toReturn.length() >= size) {
				break;
			}
		}
		return toReturn.toString();
	}

	public static String randomString() {
		String toReturn = UUID.randomUUID().toString();

		return toReturn;
	}

	public static String fillString(String s, int quantity) {
		String toReturn = "";
		if (!isNullOrEmpty(s)) {
			for (int i = 0; i < quantity; i++) {
				toReturn += s;
			}
		}
		return toReturn;
	}

	public static String join(Collection<String> collection, String sep) {
		StringBuffer toReturn = new StringBuffer();
		boolean first = true;
		for (String item : collection) {
			if (!first) {
				toReturn.append(sep);
			}
			toReturn.append(item.toString());
			first = false;
		}

		return toReturn.toString();
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String formatCpf(String cpf) {
		try {
			return StringUtil.formatarString(StringUtil.onlyNumbers(cpf), "###.###.###-##");
		} catch (ParseException e) {
			return cpf;
		}
	}

	public static String formatCnpj(String cnpj) {
		try {
			return StringUtil.formatarString(StringUtil.onlyNumbers(cnpj), "##.###.###/####-##");
		} catch (ParseException e) {
			return cnpj;
		}
	}

	private static String formatarString(String texto, String mascara) throws ParseException {
		MaskFormatter mf = new MaskFormatter(mascara);
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(texto);
	}

	public static String abbreviate(String s, int maxSize) {
		StringBuilder toReturn = new StringBuilder("");
		if (!StringUtil.isNullOrEmpty(s)) {
			String[] wordsArray = s.split(" ");
			if (wordsArray.length > 1) {
				List<String> words = new ArrayList<String>(0);
				words.clear();
				for (String w : wordsArray) {
					words.add(w);
				}
				String firstWord = words.get(0);
				words.remove(0);
				String lastWord = words.get(words.size() - 1);
				words.remove(words.size() - 1);

				toReturn.append(firstWord);

				for (String w : words) {
					toReturn.append(" ");
//                    if (isStopWord(w)) {
					if (w.length() <= 3) {
						toReturn.append(w);
					} else {
						toReturn.append(w.substring(0, 1));
						toReturn.append(".");
					}
				}

				toReturn.append(" ");
				toReturn.append(lastWord);

			}
		}
		return toReturn.toString();
	}

	public static boolean isStopWord(String s) {
		final String stopWordsList = "dadedidodu";
		return stopWordsList.contains(s);
	}

	public static boolean contains(String s, String... values) {
		if (StringUtil.isNullOrEmpty(s)) {
			return false;
		}
		for (String v : values) {
			if (s.equals(v)) {
				return true;
			}
		}
		return false;
	}
}
