package sistinfo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;

public class CookieManager {

	/**
	 * Añade las cookies necesarias para almacenar el usuario logueado
	 * @param usuario
	 * @param response
	 */
	public static void addLoginCookiesToResponse(UsuarioVO usuario, HttpServletResponse response) {
		Cookie aliasCookie = new Cookie("aliasUsuario", usuario.getAlias());
		Cookie claveCookie = new Cookie("claveUsuario", usuario.getPasswordHash());
		response.addCookie(aliasCookie);
		response.addCookie(claveCookie);
	}

	/**
	 * Borra las cookies correspondientes al usuario logueado
	 * @param request
	 * @param response
	 */
	public static void removeLoginCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("aliasUsuario") || c.getName().equals("claveUsuario")) {
					c.setValue("");
					//c.setPath("/");
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
	}

	/**
	 * Obtiene el alias de las cookies del usuario logueado
	 * @param request
	 * @return
	 */
	public static String getAliasFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String alias = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("aliasUsuario")) {
					alias = c.getValue();
					if (alias != null && !alias.trim().isEmpty()) {
						return alias;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Obtiene la clave (hasheada) del usuario logueado
	 * @param request
	 * @return
	 */
	public static String getClaveHashFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String claveHash = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("claveUsuario")) {
					claveHash = c.getValue();
					if (claveHash != null && !claveHash.trim().isEmpty()) {
						return claveHash;
					}
				}
			}
		}
		return null;
	}

}
