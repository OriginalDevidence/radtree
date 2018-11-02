package sistinfo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;

public class CookieManager {

	public static void addLoginCookiesToResponse(UsuarioVO usuario, HttpServletResponse response) {
        Cookie aliasCookie = new Cookie("aliasUsuario", usuario.getAlias());
        Cookie claveCookie = new Cookie("claveUsuario", new String(usuario.getPasswordHash()).trim());
        response.addCookie(aliasCookie);
        response.addCookie(claveCookie);
	}
	
	public static String getAliasFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String alias = null;
		for (Cookie c : cookies) {
			if (c.getName().equals("aliasUsuario")) {
				alias = c.getValue();
				if (alias != null && !alias.trim().isEmpty()) {
					return alias;
				}
			}
		}
		return null;
	}
	
	public static String getClaveHashFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String claveHash = null;
		for (Cookie c : cookies) {
			if (c.getName().equals("claveUsuario")) {
				claveHash = c.getValue();
				if (claveHash != null && !claveHash.trim().isEmpty()) {
					return claveHash;
				}
			}
		}
		return null;
	}
	
}
