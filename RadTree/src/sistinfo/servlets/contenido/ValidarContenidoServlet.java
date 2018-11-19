package sistinfo.servlets.contenido;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.util.RequestExtractor;

@SuppressWarnings("serial")
public class ValidarContenidoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8"); // TODO
        response.setCharacterEncoding("UTF-8");

        // Comprobar usuario administrador
        UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
        	response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
        } else {
        	// Modificar el estado si aprueba o deniega el contenido
            Long idContenido = RequestExtractor.getLong(request, "idContenido");
            String validacionString = request.getParameter("accion");
            Boolean validacion = null;
            if (validacionString.equals("aprobar")) {
            	validacion = true;
            } else if (validacionString.equals("denegar")) {
            	validacion = false;
            }
            
            if (idContenido != null && validacion != null) {
            	ContenidoDAO contenidoDAO = new ContenidoDAO();
            	contenidoDAO.updateEstado(idContenido, validacion ? Estado.VALIDADO : Estado.BORRADO);
            }
        	response.sendRedirect(request.getContextPath() + "/gestion-contenido/cola-validacion");
        }
        
        
    }

}
