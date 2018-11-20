package sistinfo.servlets.jsp.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

public class IncrementarVisitas {
	
	/**
	 * Incrementa en 1 las visitas del contenido con ID pasado por parámetro
	 * 
	 * TODO return
	 */
	public static boolean incrementarVisitasContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Encontrar un ID de contenido
		Long idContenido = RequestExtractor.getLong(request, "id");
		if (idContenido != null && idContenido > 0L) {
			ContenidoDAO contenidoDAO = new ContenidoDAO();
			try {
				contenidoDAO.incrementNumVisitas(idContenido);
				return true;
				
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
				return false;
			}
		} else {
			return true;
		}
	}
	
}
