package sistinfo.servlets.jsp;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class UsuariosServlet extends FooterServlet {

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
     * 
     * Recibe un parámetro búsqueda (string) que filtra a los usuarios para mostarlos (si no existe, no muestra nada)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
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
