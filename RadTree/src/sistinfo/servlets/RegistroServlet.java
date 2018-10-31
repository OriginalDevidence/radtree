package sistinfo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.excepciones.AliasYaExistenteException;
import sistinfo.capadatos.excepciones.EmailYaExistenteException;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
    	response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    	
    	String alias = request.getParameter("alias");
        Date nacimiento = new Date(37288713); // TODO
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");
        
        if (clave.equals(reclave)) {
            // TODO hash de la contraseña

            UsuarioVO usuario = new UsuarioVO(alias, nombre, apellidos, nacimiento, email, clave, UsuarioVO.TipoUsuario.PARTICIPANTE);
            try {
                UsuarioDAO facade = new UsuarioDAO();
                facade.insertUsuario(usuario);
                /* TODO pasar datos del perfil */
                response.sendRedirect("10_perfil.html");
            } catch (AliasYaExistenteException | EmailYaExistenteException e) {
            	/* TODO avisar de alias o email ya existente */
                response.sendRedirect("02_registro.html");
            } catch (Exception e) {
                response.sendRedirect("paginaError.html");
            }
        } else {
        	/* TODO avisar de que las contraseñas no son iguales */
            response.sendRedirect("02_registro.html");
        }
    }
    
}