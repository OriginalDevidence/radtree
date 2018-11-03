package sistinfo.utils;

public class UsuarioFormatChecker {

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
	 * @param alias
	 * @return
	 */
	public static boolean checkEmail(String email) {
		return email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
	}
	
}
