package sistinfo.servlets.usuario;

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
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.util.FormatChecker;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class EditarUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/*
		 * TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding
		 * todo el rato
		 */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		response.sendRedirect("errorInterno.html");
    	} else {
    		
			/*
			 * TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding
			 * todo el rato
			 */
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
	
			Map<String, String> errores = new HashMap<String, String>();
			UsuarioVO usuarioEditado = extractUsuarioFromHttpRequest(request, errores, usuario);
			if (usuarioEditado != null) {
				try {
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					boolean cambiaAlias = !usuarioEditado.getAlias().equals(usuario.getAlias());
					boolean cambiaEmail = !usuarioEditado.getEmail().equals(usuario.getEmail());
					if (usuarioDAO.updateUsuario(usuarioEditado, cambiaAlias, cambiaEmail)) {
						// Enviar al perfil y actualizar los datos del usuario en la sesión
						response.sendRedirect("perfil.jsp?alias=" + usuarioEditado.getAlias());
						request.getSession().setAttribute("usuario", usuarioEditado);
					} else {
						response.sendRedirect("errorInterno.html");
					}
				} catch (UsuarioYaExistenteException e) {
					RequestDispatcher req = request.getRequestDispatcher("editarPerfil.jsp");
					if (e.isAliasExistente()) {
						errores.put("alias", "Alias ya registrado");
					}
					if (e.isEmailExistente()) {
						errores.put("email", "Email ya existente");
					}
					request.setAttribute("errores", errores);
					req.include(request, response);
				} catch (ErrorInternoException e) {
					response.sendRedirect("errorInterno.html");
				}
			} else {
				RequestDispatcher req = request.getRequestDispatcher("editarPerfil.jsp");
				request.setAttribute("errores", errores);
				req.include(request, response);
			}

    	}
	}

	/**
     * Obtiene los datos de un usuario dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param errors
     * @return El usuario si se ha extraido correctamente, o null
     */
	public UsuarioVO extractUsuarioFromHttpRequest(HttpServletRequest request, Map<String, String> errors, UsuarioVO original) {
		
    	String alias = request.getParameter("alias");
        String nacimientoString = request.getParameter("nacimiento");
        java.sql.Date nacimiento = null;
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        
        // TODO usar FormatChecker
        
        boolean datosCorrectos = true;
        /* ALIAS */
        if (alias == null || alias.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("alias", "Campo obligatorio");
        } else if (alias.length() < 3) {
        	datosCorrectos = false;
        	errors.put("alias", "Demasiado corto");
        } else if (!FormatChecker.checkAlias(alias)) {
        	datosCorrectos = false;
        	errors.put("alias", "Formato incorrecto");
        }
        /* FECHA NACIMIENTO */
        if (nacimientoString == null || nacimientoString.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nacimiento", "Campo obligatorio");
        } else {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = sdf.parse(nacimientoString);
				java.util.Date dateBefore = sdf.parse("1900-01-01");
				java.util.Date dateAfter = new java.util.Date();
				if (date.before(dateBefore) || date.after(dateAfter)) {
		        	datosCorrectos = false;
		        	errors.put("nacimiento", "Valor inválido");
				} else {
		        	nacimiento = new java.sql.Date(date.getTime());
				}
			} catch (ParseException e) {
				datosCorrectos = false;
	        	errors.put("nacimiento", "Formato incorrecto");
			}
        }
        /* NOMBRE */
        if (nombre == null || nombre.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nombre", "Campo obligatorio");
        }
        /* APELLIDOS */
        if (apellidos == null || apellidos.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("apellidos", "Campo obligatorio");
        }
        /* EMAIL */
        if (email == null || email.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("email", "Campo obligatorio");
        } else if (!FormatChecker.checkEmail(email)) {
        	datosCorrectos = false;
        	errors.put("email", "Formato incorrecto");
        }
        
        if (datosCorrectos) {
    		return new UsuarioVO(original, alias, nombre, apellidos, nacimiento, email);
        }
        return null;
        
	}

}