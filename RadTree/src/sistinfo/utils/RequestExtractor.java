package sistinfo.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestExtractor {

	public static Long extractLong(HttpServletRequest request, String name) {
		String stringValue = request.getParameter(name);
		if (stringValue == null || stringValue.trim().isEmpty()) {
			return null;
		}
		return Long.parseLong(stringValue);
	}
	
}
