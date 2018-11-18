package sistinfo.servlets.contenido;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.util.CookieManager;
import sistinfo.util.PBKDF2Hash;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class CrearPreguntaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	// Runs on a thread whenever there is HTTP GET request
	// Take 2 arguments, corresponding to HTTP request and response
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("respuestas", new Integer(5));

		// Comprobamos el botón pulsado.
		String button = request.getParameter("button");
		
		if ("annadirRespuesta".equals(button)) {

			int respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
			if (respuestasTotales < 10) {
				respuestasTotales += 1;
			}
			
			request.setAttribute("respuestasTotales", new Long(respuestasTotales));
			request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);

		} else if ("quitarRespuesta".equals(button)) {
			int respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
			if (respuestasTotales > 2) {
				respuestasTotales -= 1;
			}
			request.setAttribute("respuestasTotales", new Long(respuestasTotales));
			request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);

		} else {
			Map<String, String> errores = new HashMap<String, String>();
			PreguntaVO pregunta = extractPreguntaFromHttpRequest(request, errores);
			List<RespuestaVO> listaRespuestas = extractRespuestaListFromHttpRequest(request, errores, 1);

			if (pregunta != null && listaRespuestas != null) {
				try {
					PreguntaDAO PreguntaDAO = new PreguntaDAO();
					PreguntaDAO.insertPregunta(pregunta, listaRespuestas);

					response.sendRedirect(request.getContextPath() + "/gestion-contenido");

				} catch (Exception e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			} else {
				request.setAttribute("errores", errores);
				request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
			}
			
		}

	}

	/**
	 * Obtiene los datos de un Pregunta dado un HttpServletRequest y escribe los
	 * errores en errors
	 * 
	 * @param request
	 * @param errors
	 * @return pregunta si se ha extraido correctamente, o null
	 */
	public PreguntaVO extractPreguntaFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {
		
		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		errors.put("idAutor", "Debes haber iniciado sesión para someter una pregunta.");
    		return null;
    	} else {
		
			String enunciado = request.getParameter("enunciado");
			Date fechaRealizacion = new Date(new java.util.Date().getTime());
	
			boolean datosCorrectos = true;
			
			if (enunciado == null || enunciado.trim().isEmpty()) {
				datosCorrectos = false;
				errors.put("enunciado", "Introduzca un enunciado para la pregunta.");
			}
	
			if (datosCorrectos) {
				return new PreguntaVO(usuario.getIdUsuario(), fechaRealizacion, enunciado);
			}
    	}
		return null;
	}
	
	public List<RespuestaVO> extractRespuestaListFromHttpRequest(HttpServletRequest request, Map<String, String> errors, long idPregunta) {
		
		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		errors.put("idAutor", "Debes haber iniciado sesión para someter una pregunta.");
    		return null;
    	} else {
    		int respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
    		
    		boolean datosCorrectos = true;
    		boolean unaRespuestaCorrecta = false;
    		for(int i = 1; i <= respuestasTotales; i++) {
    			String enunciado = request.getParameter("res" + i);
    			if (enunciado == null || enunciado.trim().isEmpty()) {
    				datosCorrectos = false;
    				errors.put("enunciado", "Debes introducir todas las respuestas.");
    			}
    			String esCorrecta = request.getParameter("correcta" + i);
    			System.out.println(esCorrecta);
    			if (esCorrecta != null) {
    				unaRespuestaCorrecta = true;
    			} 
    		}
    		
    		if(!unaRespuestaCorrecta) {
    			errors.put("enunciado", "Debes introducir al menos una respuesta correcta.");
    			datosCorrectos = false;
    		}
	
			if (datosCorrectos) {
				List<RespuestaVO> listaRespuestas = new LinkedList<RespuestaVO>();
				boolean correcta = false;
				for(int i = 1; i <= respuestasTotales; i++) {
	    			String enunciado = request.getParameter("res" + i);
	    			String esCorrecta = request.getParameter("correcta" + i);
	    			if (esCorrecta != null) {
	    				correcta = true;
	    			} else {
	    				correcta = false;
	    			}
	    			RespuestaVO res = new RespuestaVO(idPregunta, enunciado, correcta);
					
					listaRespuestas.add(res);
	    		}
				
				return listaRespuestas;
			}
    	}
		return null;
	}

}