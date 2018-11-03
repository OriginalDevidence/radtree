package sistinfo.utils;

public class ProfilePictureManager {
	
	/**
	 * Devuelve la ruta a donde tendría que estar la foto de perfil del usuario con ese id
	 * @param id
	 * @return
	 */
	public static String getPathForId(Long id) {
		return "images/profile/" + id.toString() + ".jpg";
	}
	
	/**
	 * Devuelve la ruta a la foto de perfil por defecto
	 * @param id
	 * @return
	 */
	public static String getDefaultPath() {
		return "images/profile/default.jpg";
	}
	
}
