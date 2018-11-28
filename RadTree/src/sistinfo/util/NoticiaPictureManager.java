package sistinfo.util;

public class NoticiaPictureManager {
	
	/**
	 * Devuelve la ruta a donde tendría que estar la foto de la noticia con ese id
	 * @param id
	 * @return
	 */
	public static String getPathForId(Long id) {
		return "images/noticias/" + id.toString() + ".jpg";
	}
	
	/**
	 * Devuelve la ruta a la foto de noticia por defecto
	 * @param id
	 * @return
	 */
	public static String getDefaultPath() {
		return "images/noticias/default.jpg";
	}
	
}
