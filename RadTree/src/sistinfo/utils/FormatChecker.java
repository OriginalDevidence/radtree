package sistinfo.utils;

public class FormatChecker {

	/**
	 * Comprueba que el alias sigue con las normas de formato: solo se permiten
	 * letras, números o carácteres especiales (_-)
	 * @param alias
	 * @return
	 */
	public static boolean checkAlias(String alias) {
		return alias.matches("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$");
	}

	/**
	 * Comprueba que el email tiene el formato correcto
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		return email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
	}
	
	/**
	 * Comprueba que la url tiene formato correcto
	 * @param url
	 * @return
	 */
	public static boolean checkUrl(String url) {
		return url.matches("^(?:http(s)?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$");
	}
	
}
