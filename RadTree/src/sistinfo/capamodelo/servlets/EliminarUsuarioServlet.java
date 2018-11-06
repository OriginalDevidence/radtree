package sistinfo.capamodelo.servlets;

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		response.sendRedirect("errorInterno.html");
    	} else {
    		
			try {
				// Eliminar usuario y redirigir a la página de inicio
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.deleteUsuario(usuario.getIdUsuario());
				request.getSession().invalidate();
				response.sendRedirect("index.jsp");
			} catch (ErrorInternoException e) {
				response.sendRedirect("errorInterno.html");
			}
    		
    	}
	}

}

