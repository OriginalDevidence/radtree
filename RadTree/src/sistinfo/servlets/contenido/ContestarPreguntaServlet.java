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
		
		
		return null;
	}

}