package sistinfo.servlets.jsp;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class UsuariosServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de la lista de usuarios: coloca en la request una lista de
     * UsuarioVO correspondiente a los usuarios encontrados por el parámetro búsqueda (si no existe
     * este parámetro, no muestra ningún usuario)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	RequestDispatcher req = request.getRequestDispatcher("/jsp/usuarios.jsp");
		
		// Comprobar si ya hay un usuario como atributo
		String busqueda = request.getParameter("busqueda");
		if (busqueda != null && !busqueda.trim().isEmpty()) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				List<UsuarioVO> usuarios = usuarioDAO.getUsuariosBySearch(busqueda, 30);
				request.setAttribute("usuarios", usuarios);
				req.forward(request, response);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
		} else {
			req.forward(request, response);
		}
		
    }
}
