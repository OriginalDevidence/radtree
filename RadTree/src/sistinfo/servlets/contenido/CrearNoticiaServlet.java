package sistinfo.servlets.contenido;

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
import sistinfo.util.FormatChecker;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.dao.NoticiaDAO;

@SuppressWarnings("serial")
public class CrearNoticiaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> errores = new HashMap<String, String>();
		NoticiaVO noticia = extractNoticiaFromHttpRequest(request, errores);

		if (noticia != null) {
			NoticiaDAO noticiaDAO = new NoticiaDAO();
			try {
				noticiaDAO.insertNoticia(noticia);
				response.sendRedirect("gestionarContenido.jsp");
			} catch (Exception e) {
				response.sendRedirect("errorInterno.html");
			}
		} else {
			RequestDispatcher req = request.getRequestDispatcher("crearNoticia.jsp");
			request.setAttribute("errores", errores);
			req.include(request, response);
		}
		
	}

	/**
     * Obtiene los datos de una noticia dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param errors
     * @return La noticia si ha sido extraida correctamente, o null en caso contrario
     */
	public NoticiaVO extractNoticiaFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {

        //TODO: Como obtener la idAutor mirar DUDA
        long idAutor = 1L;
        Date fechaRealizacion = new Date(0L); // TODO fecha actual

        String titulo = request.getParameter("titulo");
        String cuerpo = request.getParameter("cuerpo");
        String url = request.getParameter("url");

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
        /* URL */
        if (url == null || url.trim().isEmpty()) {
            datosCorrectos = false;
            errors.put("url", "Campo obligatorio");
        } else if (!FormatChecker.checkUrl(url)) {
        	datosCorrectos = false;
        	errors.put("url", "URL inválida. Debe tener formato 'http://www.example.com'");
		}

        if (datosCorrectos) {
        	return new NoticiaVO(idAutor, 0L, fechaRealizacion, Estado.PENDIENTE, titulo, cuerpo, url);
        } else {
            return null;
        }
        
  	}
}
