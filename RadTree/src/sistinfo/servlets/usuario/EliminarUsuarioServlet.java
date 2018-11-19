package sistinfo.servlets.usuario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class EliminarUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Eliminar el usuario que ha iniciado sesión, invalidar su sesión y redirigirlo a la página de inicio
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		response.sendRedirect(request.getContextPath() + "/error-interno");
    	} else {
    		
			try {
				// Eliminar usuario y redirigir a la página de inicio
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.deleteUsuario(usuario.getIdUsuario());
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath());
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
    		
    	}
	}

}

