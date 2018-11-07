package sistinfo.util;

import javax.servlet.http.HttpServletRequest;

public class RequestExtractor {

	/**
	 * Obtiene un dato Long de los parámetros de la request
	 * @param request
	 * @param name
	 * @return
	 */
	public static Long getLong(HttpServletRequest request, String name) {
		String stringValue = request.getParameter(name);
		if (stringValue == null || stringValue.trim().isEmpty()) {
			return null;
		}
		return Long.parseLong(stringValue);
	}
	
}
