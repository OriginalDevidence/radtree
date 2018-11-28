package sistinfo.servlets.contenido;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.util.RequestExtractor;
import sistinfo.capadatos.dao.PreguntaDAO;


@SuppressWarnings("serial")
public class EdicionPreguntaServlet extends HttpServlet {
	public static final int MAX_RESPUESTAS = 10;
	public static final int MIN_RESPUESTAS = 2;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	// Take 2 arguments, corresponding to HTTP request and response
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// Comprobar que hay un usuario válido
		UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
		if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
		} else {
			
			Long idContenido = RequestExtractor.getLong(request, "id");
			boolean editando = false;
			if (idContenido != null) {
				editando = true;
			}
			Map<String, String> erroresArriba = new HashMap<String, String>();
			
			int respuestasTotales;
	
			// Comprobamos el botón pulsado.
			String button = request.getParameter("button");
			if(button == null) {
				response.sendRedirect(request.getContextPath() + (editando ? "/gestion-contenido/editar-pregunta" : "/gestion-contenido/crear-pregunta"));
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
					request.getRequestDispatcher(editando ? "/gestion-contenido/editar-pregunta" : "/gestion-contenido/crear-pregunta").forward(request, response);
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
					request.getRequestDispatcher(editando ? "/gestion-contenido/editar-pregunta" : "/gestion-contenido/crear-pregunta").forward(request, response);
					break;
		
		        case "crearPregunta":
					Map<String, String> errores = new HashMap<String, String>();
					PreguntaVO pregunta = extractPreguntaFromHttpRequest(request, usuario.getIdUsuario(), errores);
					List<RespuestaVO> listaRespuestas = extractRespuestaListFromHttpRequest(request, errores, erroresArriba);
		
					if (pregunta != null && listaRespuestas != null) {
						try {
							PreguntaDAO preguntaDAO = new PreguntaDAO();
							if (editando) {
								// Actualizar la pregunta
								pregunta.setIdContenido(idContenido);
								preguntaDAO.updatePregunta(pregunta);
								// Borrar las respuestas viejas (y con ello las respuestas que tengan) e introducir las nuevas
								List<RespuestaVO> listaRespuestasViejas = preguntaDAO.getRespuestasByPregunta(idContenido);
								for (RespuestaVO respuesta : listaRespuestasViejas) {
									preguntaDAO.deleteRespuesta(respuesta.getIdRespuesta());
								}
								for (RespuestaVO respuesta : listaRespuestas) {
									respuesta.setIdPregunta(idContenido);
									preguntaDAO.insertRespuesta(respuesta);
								}
								response.sendRedirect(request.getContextPath() + "/gestion-contenido");
							} else {
								// Crear la pregunta
								preguntaDAO.insertPregunta(pregunta, listaRespuestas);
								response.sendRedirect(request.getContextPath() + "/gestion-contenido");
							}
						} catch (Exception e) {
							response.sendRedirect(request.getContextPath() + "/error-interno");
						}
					} else {
						request.setAttribute("respuestasTotales", RequestExtractor.getInteger(request, "respuestasTotales"));
						request.setAttribute("errores", errores);
						request.setAttribute("erroresArriba", erroresArriba);
						request.getRequestDispatcher(editando ? "/gestion-contenido/editar-pregunta" : "/gestion-contenido/crear-pregunta").forward(request, response);
					}
					break;
					
		        default:
					request.getRequestDispatcher(editando ? "/gestion-contenido/editar-pregunta" : "/gestion-contenido/crear-pregunta").forward(request, response);
			}
			
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
	public PreguntaVO extractPreguntaFromHttpRequest(HttpServletRequest request, Long idUsuario, Map<String, String> errors) {
		
		String enunciado = request.getParameter("enunciado");
		Date fechaRealizacion = new Date(new java.util.Date().getTime());
	
		boolean datosCorrectos = true;
		
		if (enunciado == null || enunciado.trim().isEmpty()) {
			datosCorrectos = false;
			errors.put("enunciado", "Introduzca un enunciado para la pregunta.");
		} else if (enunciado.length() > PreguntaVO.ENUNCIADO_MAX){
			datosCorrectos = false;
			errors.put("enunciado", "Máx " + PreguntaVO.ENUNCIADO_MAX + " caracteres");
		}
	
		if (datosCorrectos) {
			return new PreguntaVO(idUsuario, fechaRealizacion, enunciado);
		} else {
			return null;
    	}
	}
	
	/**
	 * Obtiene los datos de una Respuesta dado un HttpServletRequest y escribe los
	 * errores en errors y upErrors
	 * 
	 * @param request
	 * @param errors
	 * @param  upErrors
	 * @return pregunta si se ha extraido correctamente, o null
	 */
	public List<RespuestaVO> extractRespuestaListFromHttpRequest(HttpServletRequest request, Map<String, String> errors, Map<String, String> upErrors) {
		
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
			} else if (enunciado.length() > RespuestaVO.ENUNCIADO_MAX) {
				datosCorrectos = false;
				errors.put("respuesta" + i, "Máx " + RespuestaVO.ENUNCIADO_MAX + " caracteres");
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
			List<RespuestaVO> listaRespuestas = new ArrayList<RespuestaVO>();
			boolean correcta = false;
			
			for(int i = 1; i <= respuestasTotales; i++) {
    			enunciado = request.getParameter("res" + i);
    			esCorrecta = request.getParameter("correcta" + i);
    			if (esCorrecta != null) {
    				correcta = true;
    			} else {
    				correcta = false;
    			}
    			RespuestaVO res = new RespuestaVO(enunciado, correcta);
				listaRespuestas.add(res);
    		}
			
			return listaRespuestas;
		} else {
			return null;
		}
			
	}

}