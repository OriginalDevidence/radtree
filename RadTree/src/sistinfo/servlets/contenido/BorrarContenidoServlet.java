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
public class BorrarContenidoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cambiar el estado de un contenido BORRADO y volver a la página de redirect incluida
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Comprobar usuario administrador
        UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
        	response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
        } else {
        	// Modificar el estado si aprueba o deniega el contenido
            Long idContenido = RequestExtractor.getLong(request, "id");
            String redirect = request.getParameter("redirect");
            
            if (idContenido != null && redirect != null) {
            	ContenidoDAO contenidoDAO = new ContenidoDAO();
            	contenidoDAO.updateEstado(idContenido, Estado.BORRADO);
            }
        	response.sendRedirect(request.getContextPath() + "/" + redirect);
        }
        
        
    }

}
