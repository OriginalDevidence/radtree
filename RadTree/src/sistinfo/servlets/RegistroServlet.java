package sistinfo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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

        Map<String, String> errores = new HashMap<String, String>(); /* TODO */
        
    	/* TODO quitar? */
    	response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    	
    	String alias = request.getParameter("alias");
        Date nacimiento = new Date(37288713); // TODO
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");
        
        /* TODO comprobar campos no vacios */
        
        if (clave.equals(reclave)) {
            // TODO hash de la contraseña

            UsuarioVO usuario = new UsuarioVO(alias, nombre, apellidos, nacimiento, email, clave, UsuarioVO.TipoUsuario.PARTICIPANTE);
            try {
                UsuarioDAO facade = new UsuarioDAO();
                //facade.insertUsuario(usuario);
                /* TODO pasar datos del perfil */
                
                RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
                System.out.println(usuario.getAlias());
                request.setAttribute("usuario", usuario);
                req.include(request, response);
                response.sendRedirect("perfil.jsp");
            /*} catch (AliasYaExistenteException e) {
            errores.add("Alias", "Alias ya existente");
            	} catch (EmailYaExistenteException e) {
            errores.add("Email", "Email ya existente");*/
            } catch (Exception e) {
                response.sendRedirect("paginaError.html");
            }
        } else {
        	/* TODO avisar de que las contraseñas no son iguales */
            response.sendRedirect("02_registro.html");
        }
    }
    
}