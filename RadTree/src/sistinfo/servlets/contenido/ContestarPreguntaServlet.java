package sistinfo.servlets.contenido;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.excepciones.PreguntaYaRespondidaException;
import sistinfo.util.RequestExtractor;
import sistinfo.capadatos.dao.PreguntaDAO;

@SuppressWarnings("serial")
public class ContestarPreguntaServlet extends HttpServlet {
	public static final int MAX_RESPUESTAS = 10;
	public static final int MIN_RESPUESTAS = 2;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

    /**
     * Servlet para la respuesta de usuarios registrados a una pregunta, recibe por parámetros la pregunta y las respuestas dadas y
     * registra su respuesta, sumándole los puntos necesarios
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// Comprobar que el usuario está logueado por seguridad.
		UsuarioVO usuario = (UsuarioVO) request.getSession().getAttribute("usuario");
		if (usuario == null) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
		} else {

			// Obtener el ID de pregunta respondida y la acción a realizar
			Long idPregunta = RequestExtractor.getLong(request, "id");
			if (idPregunta == null) {
				response.sendRedirect(request.getContextPath() + "/preguntas");
			} else {
				
				// Insertar la respuesta de usuario en la base de datos
				Map<Long, Boolean> mapaRespuestas = extractRespuestaMapFromHttpRequest(request);
	
				if (mapaRespuestas != null) {
					try {
						
						PreguntaDAO preguntaDAO = new PreguntaDAO();
						preguntaDAO.insertContestacion(usuario.getIdUsuario(), idPregunta, mapaRespuestas);
						request.getRequestDispatcher("/preguntas/ver").forward(request, response);
						
					} catch (ErrorInternoException e) {
						response.sendRedirect(request.getContextPath() + "/error-interno");
					} catch (PreguntaYaRespondidaException e) {
						RequestDispatcher req = request.getRequestDispatcher("/preguntas/ver");
						request.setAttribute("errorArriba", "Ya has respondido a esta pregunta.");
						req.forward(request, response);
					}
				} else {
					RequestDispatcher req = request.getRequestDispatcher("/preguntas/ver");
					request.setAttribute("errorArriba", "Debes introducir al menos una respuesta correcta.");
					req.forward(request, response);
				}
			
			}
		}
	}

	/**
	 * Extrae las respuestas que ha dado un usuario a una pregunta por la HttpServletRequest
	 * @param request
	 * @return Mapa Long -> Boolean que relaciona ID de respuesta con la contestación que ha dado el usuario
	 */
	public Map<Long, Boolean> extractRespuestaMapFromHttpRequest(HttpServletRequest request) {

		boolean unaRespuestaCorrecta = false;

		Map<Long, Boolean> mapaRespuestas = new HashMap<Long, Boolean>();

		// Ir leyendo respuestas hasta que alguna sea nula
		int numRespuesta = 1;
		Long idRespuesta = RequestExtractor.getLong(request, "idRespuesta" + numRespuesta);
		while (idRespuesta != null) {
			
			String respuesta = request.getParameter("resCorrecta" + numRespuesta);
			if (respuesta != null) { // responde "on" por la checkbox
				mapaRespuestas.put(idRespuesta, true);
				unaRespuestaCorrecta = true;
			} else {
				mapaRespuestas.put(idRespuesta, false);
			}
			
			numRespuesta++;
			idRespuesta = RequestExtractor.getLong(request, "idRespuesta" + numRespuesta);
		}
		
		// Si ninguna de las respuestas ha sido marcada como correcta.
		if (!unaRespuestaCorrecta) {
			return null;
		} else {
			return mapaRespuestas;
		}

	}

}