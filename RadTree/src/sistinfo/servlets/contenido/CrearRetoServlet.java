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
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.util.RequestExtractor;
import sistinfo.capadatos.dao.RetoDAO;

@SuppressWarnings("serial")
public class CrearRetoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

	/**
	 * Inserta o actualiza un reto en la base de datos si los datos introducidos son correctos o muestra los errores que hayan ocurrido
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
			RetoVO reto = extractRetoFromHttpRequest(request, usuario.getIdUsuario(), errores);
	
			Long idContenido = RequestExtractor.getLong(request, "idContenido");
			boolean editando = false;
			if (idContenido != null) {
				editando = true;
			}
			
			if (reto != null) {
				RetoDAO retoDAO = new RetoDAO();
				try {
					// Intentar insertarlo o actualizarlo y ver si ha salido bien
					if (editando) {
						reto.setIdContenido(idContenido);
						if (retoDAO.updateReto(reto)) {
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						} else {
							response.sendRedirect(request.getContextPath() + "/error-interno");
						}
					} else {
						if (retoDAO.insertReto(reto)) {
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						} else {
							response.sendRedirect(request.getContextPath() + "/error-interno");
						}
					}
				} catch (Exception e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			} else {
				RequestDispatcher req = request.getRequestDispatcher(editando ? "/gestion-contenido/crear-reto" : "/gestion-contenido/editar-reto");
				request.setAttribute("errores", errores);
				req.include(request, response);
			}
		
    	}
		
	}

	/**
	 * Obtiene los datos de un reto dado un HttpServletRequest y el id de su autor y escribe los errores
	 * en errors
	 * @param request
	 * @param idUsuario
	 * @param errors
	 * @return El reto si se ha extraido correctamente, o null en caso contrario
	 */
	public RetoVO extractRetoFromHttpRequest(HttpServletRequest request, Long idUsuario, Map<String, String> errors) {
		
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
			// visitas iniciales = 0L
			Date fechaActual = new Date(new java.util.Date().getTime());
			return new RetoVO(idUsuario, 0L, fechaActual, Estado.PENDIENTE, titulo, cuerpo);
		} else {
			return null;
		}

	}
  
}
