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

import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.UsuarioVO;

import sistinfo.capadatos.dao.PreguntaDAO;


@SuppressWarnings("serial")
public class CrearPreguntaServlet extends HttpServlet {
	public static final int MAX_RESPUESTAS = 10;
	public static final int MIN_RESPUESTAS = 2;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	// Take 2 arguments, corresponding to HTTP request and response
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Map<String, String> erroresArriba = new HashMap<String, String>();
		
		int respuestasTotales;

		// Comprobamos el botón pulsado.
		String button = request.getParameter("button");
		if(button == null) {
			response.sendRedirect(request.getContextPath() + "/gestion-contenido/crear-pregunta");
			return;
		}
		switch (button) {
	        case "annadirRespuesta":
	        	respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
				if (respuestasTotales < MAX_RESPUESTAS) {
					respuestasTotales += 1;
				}
				else {
					erroresArriba.put("errorArriba", "El máximo de respuestas permitido es " + MAX_RESPUESTAS + ".");
					request.setAttribute("erroresArriba", erroresArriba);
				}
				request.setAttribute("respuestasTotales", new Long(respuestasTotales));
				request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
				break;
				
	        case "quitarRespuesta":
				respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
				if (respuestasTotales > MIN_RESPUESTAS) {
					respuestasTotales -= 1;
				}
				else {
					erroresArriba.put("errorArriba", "El mínimo de respuestas permitido es " + MIN_RESPUESTAS + ".");
					request.setAttribute("erroresArriba", erroresArriba);
				}
				request.setAttribute("respuestasTotales", new Long(respuestasTotales));
				request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
				break;
	
	        case "crearPregunta":
				Map<String, String> errores = new HashMap<String, String>();
				PreguntaVO pregunta = extractPreguntaFromHttpRequest(request, errores);
				List<RespuestaVO> listaRespuestas = extractRespuestaListFromHttpRequest(request, errores, erroresArriba, 1);
	
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
					request.setAttribute("erroresArriba", erroresArriba);
					request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
				}
				break;
				
	        default:
				request.getRequestDispatcher("/gestion-contenido/crear-pregunta").forward(request, response);
		}

	}

	/**
	 * Obtiene los datos de un Pregunta dado un HttpServletRequest y escribe los
	 * errores en errors
	 * 
	 * @param request
	 * @param errors
	 * @return pregunta si se ha extraido correctamente, o null en el caso contrario
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
	
	/**
	 * Obtiene los datos de un Pregunta dado un HttpServletRequest y escribe los
	 * errores en errors y upErrors
	 * 
	 * @param request
	 * @param errors
	 * @param  upErrors
	 * @return pregunta si se ha extraido correctamente, o null
	 */
	public List<RespuestaVO> extractRespuestaListFromHttpRequest(HttpServletRequest request, Map<String, String> errors, Map<String, String> upErrors, long idPregunta) {
		
		// Comprobar que el usuario está logueado por seguridad.
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		errors.put("idAutor", "Debes haber iniciado sesión para someter una pregunta.");
    		return null;
    	} 
    	
    	else {
    		int respuestasTotales = Integer.parseInt(request.getParameter("respuestasTotales"));
    		
    		if(respuestasTotales < MIN_RESPUESTAS || respuestasTotales > MAX_RESPUESTAS) {
    			errors.put("idAutor", "El número de respuestas introducido es menor o mayor del límite (2, 10).");
        		return null;
    		}
    		
    		boolean datosCorrectos = true;
    		boolean unaRespuestaCorrecta = false;
    		
    		String enunciado;
    		String esCorrecta;
    		
    		//Comprobamos que todas las respuestas que se van a introducir tienen enunciado.
    		for(int i = 1; i <= respuestasTotales; i++) {
    			enunciado = request.getParameter("res" + i);
    			if (enunciado == null || enunciado.trim().isEmpty()) {
    				datosCorrectos = false;
    				errors.put("respuesta" + i, "Debes introducir un enunciado para todas las respuestas.");
    			}
    			
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
				List<RespuestaVO> listaRespuestas = new LinkedList<RespuestaVO>();
				boolean correcta = false;
				
				for(int i = 1; i <= respuestasTotales; i++) {
	    			enunciado = request.getParameter("res" + i);
	    			esCorrecta = request.getParameter("correcta" + i);
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