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
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.util.FormatChecker;
import sistinfo.util.RequestExtractor;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.dao.NoticiaDAO;

@SuppressWarnings("serial")
public class EdicionNoticiaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Inserta o actualiza una noticia en la base de datos si los datos introducidos son correctos o muestra los errores que hayan ocurrido
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// Comprobar que hay un usuario válido
		UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
		if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
		} else {
			
			Map<String, String> errores = new HashMap<String, String>();
			NoticiaVO noticia = extractNoticiaFromHttpRequest(request, usuario.getIdUsuario(), errores);

			Long idContenido = RequestExtractor.getLong(request, "id");
			boolean editando = false;
			if (idContenido != null) {
				editando = true;
			}
			
			if (noticia != null) {
				NoticiaDAO noticiaDAO = new NoticiaDAO();
				try {
					// Intentar insertarlo o actualizarlo y ver si ha salido bien
					if (editando) {
						noticia.setIdContenido(idContenido);
						if (noticiaDAO.updateNoticia(noticia)) {
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						} else {
							response.sendRedirect(request.getContextPath() + "/error-interno");
						}
					} else {
						// Intentar insertarlo y ver si ha salido bien
						Long idNoticia = noticiaDAO.insertNoticia(noticia);
						if (idNoticia >= 0L) {
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						} else {
							response.sendRedirect(request.getContextPath() + "/error-interno");
						}
					}
				} catch (Exception e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			} else {
				RequestDispatcher req = request.getRequestDispatcher(editando ? "/gestion-contenido/editar-noticia" : "/gestion-contenido/crear-noticia");
				request.setAttribute("errores", errores);
				req.forward(request, response);
			}
		
		}
		
	}

	/**
     * Obtiene los datos de una noticia dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param idAutor
     * @param errors
     * @return La noticia si ha sido extraida correctamente, o null en caso contrario
     */
	public NoticiaVO extractNoticiaFromHttpRequest(HttpServletRequest request, Long idAutor, Map<String, String> errors) {

        // Datos del campo
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
			// visitas iniciales = 0L
			Date fechaActual = new Date(new java.util.Date().getTime());
        	return new NoticiaVO(idAutor, 0L, fechaActual, Estado.PENDIENTE, titulo, cuerpo, url);
        } else {
            return null;
        }
        
  	}
}
