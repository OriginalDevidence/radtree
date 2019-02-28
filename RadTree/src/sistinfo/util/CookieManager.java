package sistinfo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	/**
	 * Añade las cookies necesarias para recordar el identificador del usuario logueado
	 * @param usuario
	 * @param response
	 */
	public static void addIdCookiesToResponse(HttpServletResponse response, String identificador) {
		Cookie idCookie = new Cookie("userId", identificador);
		idCookie.setMaxAge(365 * 24 * 60 * 60);
		response.addCookie(idCookie);
	}
	
	/**
	 * Obtener el identificador del usuario logueado recordado (si existe)
	 * @param request
	 * @return Identificador si existe, null en caso contrario
	 */
	public static String getIdFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("userId")) {
					return c.getValue();
				}
			}
		}
		return null;
	}

}