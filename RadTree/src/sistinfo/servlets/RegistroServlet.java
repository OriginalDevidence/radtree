package sistinfo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.AliasYaExistenteException;
import sistinfo.excepciones.EmailYaExistenteException;
import sistinfo.utils.MD5Hash;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, String> errores = new HashMap<String, String>();
        
    	UsuarioVO usuario = extractUsuarioFromHttpRequest(request, errores);
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.insertUsuario(usuario);
            
            RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
            request.setAttribute("usuario", usuario);
            req.include(request, response);
        } catch (AliasYaExistenteException e) {
        	errores.put("Alias", "El alias ya está registrado");
        } catch (EmailYaExistenteException e) {
        	errores.put("Email", "Ya existe una cuenta con ese email");
        } catch (Exception e) {
        	System.out.println("whoops");
            response.sendRedirect("70_errorInterno.html");
        }

        /* TODO tratamiento de errores, presentarlos en la pagina en lugar de por consola */
    	for (String k : errores.keySet()) {
        	System.out.println("Error " + k + ": " + errores.get(k));
    	}
    	
    }
	
    /**
     * Obtiene los datos de un usuario dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param errors
     * @return El usuario si se ha extraido correctamente, o null
     */
	public UsuarioVO extractUsuarioFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {
    	String alias = request.getParameter("alias");
        String nacimientoString = request.getParameter("nacimiento");
        java.sql.Date nacimiento = null;
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");
        
        boolean datosCorrectos = true;
        if (alias == null || alias.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("alias", "Alias inválido");
        }
        if (nacimientoString == null || nacimientoString.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nacimiento", "Fecha inválida");
        } else {
        	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = sdf1.parse(nacimientoString);
	        	nacimiento = new java.sql.Date(date.getTime());
			} catch (ParseException e) {
				datosCorrectos = false;
	        	errors.put("nacimiento", "Formato incorrecto de fecha");
			}
        }
        if (nombre == null || nombre.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nombre", "Nombre inválido");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("apellidos", "Apellidos inválidos");
        }
        if (email == null || email.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("email", "Email inválido");
        }
        if (clave == null || clave.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("clave", "Clave inválida");
        }
        if (reclave == null || alias.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("reclave", "Clave inválida");
        }
        
        if (datosCorrectos) {
        	if (clave.equals(reclave)) {
        		String claveHash = MD5Hash.getMD5Hash(clave);
        		if (claveHash != null) {
        			return new UsuarioVO(alias, nombre, apellidos, nacimiento, email, claveHash, UsuarioVO.TipoUsuario.PARTICIPANTE);
        		}
        	} else {
        		errors.put("reclave", "Las contraseñas no coinciden");
        	}
        }
        return null;
	}
    
}