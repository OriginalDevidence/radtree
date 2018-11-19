package sistinfo.servlets.contenido;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.ContestaVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.UsuarioVO;

import sistinfo.capadatos.dao.PreguntaDAO;

@SuppressWarnings("serial")
public class ContestarPreguntaServlet extends HttpServlet {
	public static final int MAX_RESPUESTAS = 10;
	public static final int MIN_RESPUESTAS = 2;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	// Take 2 arguments, corresponding to HTTP request and response
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String button = request.getParameter("button");
		if (button == null) {
			response.sendRedirect(request.getContextPath() + "/gestion-contenido/crear-pregunta");
			return;
		}
		switch (button) {
		case "validarRespuesta":
			
			break;

		case "pasarOtraPregunta":
			
			break;

		default:
			request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
		}
	}

	/**
	 * Obtiene los datos de un Pregunta dado un HttpServletRequest y escribe los
	 * errores en errors y upErrors
	 * 
	 * @param request
	 * @param errors
	 * @param upErrors
	 * @return pregunta si se ha extraido correctamente, o null
	 */
	public List<ContestaVO> extractRespuestaListFromHttpRequest(HttpServletRequest request, Map<String, String> errors,
			Map<String, String> upErrors, long idPregunta) {

		// Comprobar que el usuario está logueado por seguridad.
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		errors.put("idAutor", "Debes haber iniciado sesión para someter una pregunta.");
    		return null;
    	} 
    	
    	else {
    		int respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
    		
    		boolean datosCorrectos = true;
    		boolean unaRespuestaCorrecta = false;
    	
    		String esCorrecta;
    		
    		//Comprobamos que todas las respuestas que se van a introducir tienen enunciado.
    		for(int i = 1; i <= respuestasTotales; i++) {
    			esCorrecta = request.getParameter("correcta" + i);
    			if (esCorrecta != null) {
    				unaRespuestaCorrecta = true;
    			}
    		}
    		
    		//Si ninguna de las respuestas ha sido marcada como correcta.
    		if(!unaRespuestaCorrecta) {
    			upErrors.put("errorArriba", "Debes introducir al menos una respuesta correcta.");
    			datosCorrectos = false;
    		}
	
			if (datosCorrectos) {
				List<ContestaVO> listaRespuestas = new LinkedList<ContestaVO>();
				boolean correcta = false;
				long idRespuesta;
				
				for(int i = 1; i <= respuestasTotales; i++) {
	    			esCorrecta = request.getParameter("resCorrecta" + i);
	    			idRespuesta = Long.parseLong(request.getParameter("idRespuesta" + i), 10);
	    			if (esCorrecta != null) {
	    				correcta = true;
	    			} else {
	    				correcta = false;
	    			}
	    			
	    			ContestaVO contesta = new ContestaVO(idPregunta, idRespuesta, correcta);
					listaRespuestas.add(contesta);
	    		}
				
				return listaRespuestas;
			}
    	}
		return null;
	}

}