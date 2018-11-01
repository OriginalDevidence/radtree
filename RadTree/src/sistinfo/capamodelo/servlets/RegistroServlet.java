package sistinfo.capamodelo.servlets;

import java.io.IOException;
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
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.utils.MD5Hash;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
    	Map<String, String> errores = new HashMap<String, String>();
    	UsuarioVO usuario = extractUsuarioFromHttpRequest(request, errores);
        if (usuario != null) {
        	try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.insertUsuario(usuario);
                
                RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
                request.setAttribute("usuario", usuario);
                req.include(request, response);
            } catch (UsuarioYaExistenteException e) {
                RequestDispatcher req = request.getRequestDispatcher("registro.jsp");
            	if (e.isAliasExistente()) {
            		errores.put("alias", "El alias ya está registrado");
            	}
            	if (e.isEmailExistente()) {
            		errores.put("email", "Ya existe una cuenta con ese email");
            	}
                request.setAttribute("errores", errores);
                req.include(request, response);
            } catch (Exception e) {
                response.sendRedirect("70_errorInterno.html");
            }
        } else {
            RequestDispatcher req = request.getRequestDispatcher("registro.jsp");
            request.setAttribute("errores", errores);
            req.include(request, response);
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
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = sdf.parse(nacimientoString);
				java.util.Date dateBefore = sdf.parse("1900-01-01");
				java.util.Date dateAfter = new java.util.Date();
				if (date.before(dateBefore) || date.after(dateAfter)) {
		        	datosCorrectos = false;
		        	errors.put("nacimiento", "Fecha inválida");
				} else {
		        	nacimiento = new java.sql.Date(date.getTime());
				}
			} catch (ParseException e) {
				datosCorrectos = false;
	        	errors.put("nacimiento", "Formato incorrecto");
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
        if (reclave == null || reclave.trim().isEmpty()) {
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
        		errors.put("reclave", "La clave no coincide");
        	}
        }
        return null;
	}
    
}