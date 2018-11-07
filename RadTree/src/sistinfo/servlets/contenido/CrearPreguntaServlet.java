package sistinfo.servlets.contenido;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.PreguntaVO;
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
			Integer respuestasTotales = (Integer)getServletContext().getAttribute("respuestasTotales");
			if (respuestasTotales == null) {
				request.setAttribute("respuestasTotales", new Integer(2));
				respuestasTotales = 2;
			}
			respuestasTotales += 1;
			
			request.setAttribute("respuestasTotales", new Integer(respuestasTotales));

		} else if ("quitarRespuesta".equals(button)) {
			Integer respuestasTotales = (Integer)getServletContext().getAttribute("respuestasTotales");
			if (respuestasTotales == null) {
				request.setAttribute("respuestasTotales", new Integer(2));
				respuestasTotales = 2;
			}
			respuestasTotales -= 1;
			
			request.setAttribute("respuestasTotales", new Integer(respuestasTotales));

		} else {
			Map<String, String> errores = new HashMap<String, String>();
			PreguntaVO pregunta = extractPreguntaFromHttpRequest(request, errores);

			if (pregunta != null) {
				try {
					PreguntaDAO PreguntaDAO = new PreguntaDAO();
					PreguntaDAO.insertPregunta(pregunta, null);

					response.sendRedirect("perfil.jsp");
					request.setAttribute("usuario", pregunta);

				} catch (Exception e) {
					response.sendRedirect("errorInterno.html");
				}
			} else {
				request.setAttribute("errores", errores);
			}
			
		}
		request.getRequestDispatcher("crearPregunta.jsp").forward(request, response);

		// Your servlet's logic here

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

		String enunciado = request.getParameter("enunciado");
		java.util.Date fechaRealizacion = new java.util.Date();

		long idAutor = CookieManager.getIdFromCookies(request);

		boolean datosCorrectos = true;
		if (idAutor != 0) {
			datosCorrectos = false;
			errors.put("idAutor", "Debes haber iniciado sesión para someter una pregunta.");
		}
		if (enunciado == null || enunciado.trim().isEmpty()) {
			datosCorrectos = false;
			errors.put("enunciado", "Introduzca un enunciado para la pregunta.");
		}

		if (datosCorrectos) {
			return new PreguntaVO(idAutor, (Date) fechaRealizacion, enunciado);

		}
		return null;
	}

}