package sistinfo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        //Como obtener la idAutor mirar DUDA
        long idAutor = new long(1);
        Date fechaRealizacion = new Date(37288713); // TODO

        Estado estado = new Estado(PENDIENTE);
        long numVisitas = new long(0);
        String titulo = request.getParameter("titulo");
        String cuerpo = request.getParameter("cuerpo");

        if (clave.equals(reclave)) {
            // TODO hash de la contraseña
            // DUDA :
            NoticiaVO reto = new RetoVO(idAutor,numVisitas,fechaRealizacion,estado,titulo,cuerpo);
            try {
                NoticiaDAO facade = new RetoDAO();
                facade.insertUsuario(reto);
                /* TODO pasar datos del perfil */

                HttpSession session = request.getSession();
                session.setAttribute("profile", alias);
                response.sendRedirect("perfil.jsp");
            } catch (AliasYaExistenteException | EmailYaExistenteException e) {
            	/* TODO avisar de alias o email ya existente */
                response.sendRedirect("42_crearReto.html");
            } catch (Exception e) {
                response.sendRedirect("paginaError.html");
            }
        } else {
        	/* TODO avisar de que las contraseñas no son iguales */
            response.sendRedirect("42_crearReto.html");
        }
    }

}
