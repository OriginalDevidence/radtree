package sistinfo.capamodelo.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.dao.RetoDAO;

@SuppressWarnings("serial")
public class CrearRetoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> errores = new HashMap<String, String>();
		RetoVO reto = extractRetoFromHttpRequest(request, errores);

		if (reto != null) {
			RetoDAO retoDAO = new RetoDAO();
			try {
				retoDAO.insertReto(reto);
				response.sendRedirect("gestionarContenido.jsp");
			} catch (Exception e) {
				response.sendRedirect("errorInterno.html");
			}
		} else {
			RequestDispatcher req = request.getRequestDispatcher("crearReto.jsp");
			request.setAttribute("errores", errores);
			req.include(request, response);
		}
		
	}

	/**
	 * Obtiene los datos de un reto dado un HttpServletRequest y escribe los errores
	 * en errors
	 * @param request
	 * @param errors
	 * @return El reto si se ha extraido correctamente, o null en caso contrario
	 */
	public RetoVO extractRetoFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {

		// TODO: Como obtener la idAutor mirar DUDA
		long idAutor = 1L;
		Date fechaRealizacion = new Date(0L); // TODO fecha actual

		String titulo = request.getParameter("titulo");
		String cuerpo = request.getParameter("cuerpo");

		boolean datosCorrectos = true;
		/* TITULO */
		if (titulo == null || titulo.trim().isEmpty()) {
			datosCorrectos = false;
			errors.put("titulo", "Campo obligatorio");
		}
		/* CUERPO */
		if (cuerpo == null || cuerpo.trim().isEmpty()) {
			datosCorrectos = false;
			errors.put("cuerpo", "Campo obligatorio");
		}

		if (datosCorrectos) {
			return new RetoVO(idAutor, 0L, fechaRealizacion, Estado.PENDIENTE, titulo, cuerpo);
		} else {
			return null;
		}

	}
  
}
