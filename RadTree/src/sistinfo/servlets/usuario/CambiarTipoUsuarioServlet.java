package sistinfo.servlets.usuario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class CambiarTipoUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Edita los datos de un usuario ya registrado en el sistema según los datos pasados por la request
	 * También informa de los errores mediante el atributo errores (mapa)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// Comprobar que el usuario está logueado y es administrador
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
    		response.sendRedirect(request.getContextPath() + "/error-interno");
    	} else {
	
    		// Obtener alias del usuario a modificar
    		String alias = request.getParameter("alias");
    		String tipoUsuario = request.getParameter("tipoUsuario");
    		if (alias == null || alias.trim().isEmpty() || tipoUsuario == null || (!tipoUsuario.equals("PARTICIPANTE") && !tipoUsuario.equals("CREADOR"))) {
        		response.sendRedirect(request.getContextPath() + "/error-interno");
    		} else {
    			
    			// Obtener datos del usuario a modificar
    			UsuarioDAO usuarioDAO = new UsuarioDAO();
    			try {
    				// Modificar el usuario
					UsuarioVO usuarioModificar = usuarioDAO.getUsuarioByAlias(alias);
					if (usuarioModificar == null) {
		        		response.sendRedirect(request.getContextPath() + "/error-interno");
					} else {
						usuarioModificar.setTipoUsuario(TipoUsuario.valueOf(tipoUsuario));
						if (usuarioDAO.updateUsuario(usuarioModificar, false, false)) {
							request.getRequestDispatcher("/perfil").forward(request, response);
						}
					}
				} catch (ErrorInternoException | UsuarioYaExistenteException e) {
	        		response.sendRedirect(request.getContextPath() + "/error-interno");
				}
    			
    		}

    	}
	}
	
}