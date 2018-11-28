package sistinfo.servlets.jsp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.ComentarioVO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.NoticiaPictureManager;
import sistinfo.util.ProfilePictureManager;
import sistinfo.util.RequestExtractor;

public class IncludeInRequest {
	
	/**
     * Incluye en la request el número de elementos que existen en la cola de validación (estado pendiente)
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
	public static boolean includeNumInValidacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ContenidoDAO contenidoDAO = new ContenidoDAO();
		try {
			int numColaValidacion = contenidoDAO.getNumContenidosInColaValidacion();
			request.setAttribute("numInValidacion", numColaValidacion);
			return true;
		} catch (ErrorInternoException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error-interno");
			return false;
		}
	}
	
	/**
	 * Incluye en la request el contenido del tipo especificado en el parametro tipoContenido creado por el usuario logueado
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
	public static boolean includeMiContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// Comprobar que hay un usuario y obtener su ID, no hace falta comprobar su tipo ya que se hace en otro filter
		UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
		if (usuario == null) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			return false;
		} else {
			Long idUsuario = usuario.getIdUsuario();
		
			// Ver qué tipo de contenido cargar
			String contentType = request.getParameter("tipoContenido");
			if (contentType == null || (!contentType.equals("noticia") && !contentType.equals("pregunta") && !contentType.equals("reto"))) {
				// Default
				contentType = "noticia";
			}
			request.setAttribute("tipoContenido", contentType);
			
			// Cargar el contenido de ese tipo y colocarlo en la request
			if (contentType.equals("noticia")) {
				request.setAttribute("contenidoPathNameView", "noticias");
				request.setAttribute("contenidoPathNameEdit", "noticia");
				NoticiaDAO noticiaDAO = new NoticiaDAO();
				try {
					List<NoticiaVO> noticias = noticiaDAO.getNoticiasByAutor(idUsuario);
					request.setAttribute("noticias", noticias);
					return true;
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
					return false;
				}
				
			} else if (contentType.equals("pregunta")) {
				request.setAttribute("contenidoPathNameView", "preguntas");
				request.setAttribute("contenidoPathNameEdit", "pregunta");
				PreguntaDAO preguntaDAO = new PreguntaDAO();
				try {
					List<PreguntaVO> preguntas = preguntaDAO.getPreguntasByAutor(idUsuario);
					request.setAttribute("preguntas", preguntas);
					return true;
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
					return false;
				}
				
			} else if (contentType.equals("reto")) {
				request.setAttribute("contenidoPathNameView", "retos");
				request.setAttribute("contenidoPathNameEdit", "reto");
				RetoDAO retoDAO = new RetoDAO();
				try {
					List<RetoVO> retos = retoDAO.getRetosByAutor(idUsuario);
					request.setAttribute("retos", retos);
					return true;
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
					return false;
				}
				
			} else {
				// No sabemos qué tipo de contenido mostrar
				response.sendRedirect(request.getContextPath() + "/error-interno");
				return false;
			}
		}
		
	}
	
	/**
     * Devuelve los datos del elemento número "elemento" de la cola de validación bien en el atributo noticia, pregunta o reto.
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
	public static boolean includeElementoColaValidacion(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Integer elemento = RequestExtractor.getInteger(request, "elemento");
		ContenidoDAO contenidoDAO = new ContenidoDAO();
		try {
			// Obtener ID elemento a mostrar
			List<Long> idsValidacion = contenidoDAO.getContenidosInColaValidacion();
			if (idsValidacion.size() == 0) {
				return true;
			} else {
				if (elemento == null || elemento < 1 || elemento > idsValidacion.size()) {
					elemento = 1;
				}
				request.setAttribute("elemento", elemento);
				
				// Datos del contenido y de su usuario creador
				Long id = idsValidacion.get(elemento - 1);
				
				// Intentar extraer una noticia, reto o pregunta de el
				NoticiaDAO noticiaDAO = new NoticiaDAO();
				NoticiaVO noticia = noticiaDAO.getNoticiaById(id);
				Long idAutor = null;
				if (noticia != null) {
					idAutor = noticia.getIdAutor();
					request.setAttribute("contenido", noticia);
					request.setAttribute("noticia", noticia);
				} else {
					RetoDAO retoDAO = new RetoDAO();
					RetoVO reto = retoDAO.getRetoById(id);
					if (reto != null) {
						idAutor = reto.getIdAutor();
						request.setAttribute("contenido", reto);
						request.setAttribute("reto", reto);
					} else {
						PreguntaDAO preguntaDAO = new PreguntaDAO();
						PreguntaVO pregunta = preguntaDAO.getPreguntaById(id);
						if (pregunta != null) {
							idAutor = pregunta.getIdAutor();
							request.setAttribute("contenido", pregunta);
							request.setAttribute("pregunta", pregunta);
							List<RespuestaVO> respuestas = preguntaDAO.getRespuestasByPregunta(id);
							request.setAttribute("respuestas", respuestas);
						}
					}
				}
				
				// Datos del autor
				if (idAutor == null) {
					// El contenido a mostrar no es ni noticia, reto ni pregunta
					response.sendRedirect(request.getContextPath() + "/error-interno");
					return false;
				} else {
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					UsuarioVO usuario = usuarioDAO.getUsuarioById(idAutor);
					request.setAttribute("alias", usuario.getAlias());
					request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
					return true;
				}
			}
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
			return false;
		}
		
	}
	
	/**
     * Incluye en la request el usuario pasado por el parametro alias, en el atributo usuario (UsuarioVO)
     * Si no hay ningún usuario con el alias pasado, incluye el usuario que se encuentra en la sesión (si hay)
     * 
     * Recibe un parámetro alias de usuario
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
	public static boolean includeUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Comprobar si ya hay un usuario como atributo
		if (request.getAttribute("usuario") == null) {
			
			// Si no, buscar el usuario pasado como parámetro
			String alias = (String)request.getParameter("alias");
			if (alias != null && !alias.trim().isEmpty()) {
				// Mostrar el usuario pasado por la request
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				try {
					UsuarioVO usuario = usuarioDAO.getUsuarioByAlias(alias);
					if (usuario == null) {
			            response.sendRedirect(request.getContextPath() + "/error-interno");
						return false;
					} else {
						request.setAttribute("usuario", usuario);
						return true;
					}
				} catch (ErrorInternoException e) {
		            response.sendRedirect(request.getContextPath() + "/error-interno");
					return false;
				}
				
			// Si no hay alias en parámetro y hay un usuario logueado, mostrar ese usuario
			} else if (request.getSession().getAttribute("usuario") != null) {
				// No ha pasado ningun parámetro por request, mostrar su perfil por defecto
				UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
				request.setAttribute("usuario", usuario);
				return true;

			// No sabemos qué usuario mostrar
			} else {
		        response.sendRedirect(request.getContextPath() + "/error-interno");
				return false;
			}
		} else {
			return true;
		}
				
	}
	
	
	/**
     * Incluye en la request la noticia pasada por el parámetro ID, en el atributo noticia (NoticiaVO)
     * También incluye datos que tienen que ver con la carga de comentarios (urlContenido, id)
     * 
     * Recibe un parámetro ID de noticia
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
    public static boolean includeNoticia(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

		// Encontrar un ID de contenido
		Long idContenido = RequestExtractor.getLong(request, "id");
		if (idContenido == null || idContenido <= 0L) {
			// Buscar en atributos (despues de postear comentario) en lugar de parametros
			idContenido = (Long)request.getAttribute("id");
			if (idContenido == null || idContenido <= 0L) {
				// No sabemos qué reto mostrar
				response.sendRedirect(request.getContextPath() + "/noticias");
			}
		}
		if (idContenido != null && idContenido > 0L) {
			// Atributos para los comentarios
			request.setAttribute("urlContenido", "noticias");
			request.setAttribute("id", idContenido);
			// Cargar el reto con ese ID y el usuario autor
			NoticiaDAO noticiaDAO = new NoticiaDAO();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				NoticiaVO noticia = noticiaDAO.getNoticiaById(idContenido);
				if (noticia == null) {
					// El contenido no existe (o no debería ser mostrado)
		            response.sendRedirect(request.getContextPath() + "/noticias");
		            return false;
				} else {
					UsuarioVO usuario = usuarioDAO.getUsuarioById(noticia.getIdAutor());
					if (usuario == null) {
			            response.sendRedirect(request.getContextPath() + "/error-interno");
					} else {
						String path = NoticiaPictureManager.getPathForId(noticia.getIdContenido());
						if (request.getSession().getServletContext().getResource(path) != null) {
							noticia.setUrlImagen(request.getContextPath() + "/" + path);
						} else {
							noticia.setUrlImagen(request.getContextPath() + "/" + NoticiaPictureManager.getDefaultPath());
						}
						request.setAttribute("noticia", noticia);
						request.setAttribute("autorAlias", usuario.getAlias());
						request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
					}
					
					return true;
				}
			} catch (ErrorInternoException e) {
	            response.sendRedirect(request.getContextPath() + "/error-interno");
	            return false;
			}
		} else {
			return true;
		}
		
	}
    
    
	/**
     * Incluye en la request la pregunta pasada por el parámetro ID, en el atributo pregunta (PreguntaVO)
     * También incluye datos que tienen que ver con la carga de comentarios (urlContenido, id)
     * Finalmente se incluyen datos sobre las respuestas de la pregunta, si hay usuario registrado, si ha contestado a la pregunta y qué respuestas ha dado
     * 
     * Recibe un parámetro ID de pregunta
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
    public static boolean includePregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
		// Encontrar un ID de contenido
		Long idContenido = RequestExtractor.getLong(request, "id");
		if (idContenido == null || idContenido <= 0L) {
			// Buscar en atributos (despues de postear comentario) en lugar de parametros
			idContenido = (Long)request.getAttribute("id");
			if (idContenido == null || idContenido <= 0L) {
				// No sabemos qué pregunta mostrar
				response.sendRedirect(request.getContextPath() + "/preguntas");
			}
		}
		if (idContenido != null && idContenido > 0L) {
			// Atributos para los comentarios
			request.setAttribute("urlContenido", "preguntas");
			request.setAttribute("id", idContenido);
			// Cargar el pregunta con ese ID y el usuario autor
			PreguntaDAO preguntaDAO = new PreguntaDAO();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				
				PreguntaVO pregunta = preguntaDAO.getPreguntaById(idContenido);
				if (pregunta == null) {
					// El contenido no existe (o no debería ser mostrado)
		            response.sendRedirect(request.getContextPath() + "/preguntas");
		            return false;
				} else {
					// Obtener la lista de respuestas a la pregunta y el autor
					List<RespuestaVO> respuestas = preguntaDAO.getRespuestasByPregunta(pregunta.getIdContenido());
					request.setAttribute("respuestas", respuestas);
					UsuarioVO usuarioAutor = usuarioDAO.getUsuarioById(pregunta.getIdAutor());
					if (usuarioAutor == null) {
			            response.sendRedirect(request.getContextPath() + "/error-interno");
					} else {
						// Insertar datos del autor
						request.setAttribute("pregunta", pregunta);
						request.setAttribute("autorAlias", usuarioAutor.getAlias());
						request.setAttribute("autorCompleto", usuarioAutor.getNombre() + " " + usuarioAutor.getApellidos() + " (" + usuarioAutor.getAlias() + ")");
					
						// Comprobar si el usuario ha respondido ya a la pregunta
						UsuarioVO usuarioSesion = (UsuarioVO)request.getSession().getAttribute("usuario");
						boolean contestada = false;
						if(usuarioSesion != null) {
							// Marcar si está contestada o no
							contestada = preguntaDAO.haContestadoAPregunta(usuarioSesion.getIdUsuario(),pregunta.getIdContenido());
							request.setAttribute("contestada", contestada);
							request.setAttribute("usuarioNoReg", false);
						}
						else {
							// Usuario no registrado
							request.setAttribute("usuarioNoReg", true);
						}
						
						// Si la ha contestado, obtener la información sobre sus respuestas
						if(usuarioSesion != null && contestada) {
							Map<Long, Boolean> repuestasDelUsuario = preguntaDAO.getContestacionesAPregunta(usuarioSesion.getIdUsuario(), idContenido);
							
							int index = 1;
							for(RespuestaVO respuesta : respuestas) {
								request.setAttribute("resCorrecta" + index, respuesta.getCorrecta() == repuestasDelUsuario.get(respuesta.getIdRespuesta()));
								index++;
							}
						}
					}
					
					return true;
				}
				
			} catch (ErrorInternoException e) {
				e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/error-interno");
	            return false;
			}
		} else {
			return true;
		}
		
	}
    

	/**
     * Incluye en la request el reto pasada por el parámetro ID, en el atributo reto (RetoVO)
     * También incluye datos que tienen que ver con la carga de comentarios (urlContenido, id)
     * 
     * Recibe un parámetro ID de reto
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
    public static boolean includeReto(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Encontrar un ID de contenido
		Long idContenido = RequestExtractor.getLong(request, "id");
		if (idContenido == null || idContenido <= 0L) {
			// Buscar en atributos (despues de postear comentario) en lugar de parametros
			idContenido = (Long)request.getAttribute("id");
			if (idContenido == null || idContenido <= 0L) {
				// No sabemos qué reto mostrar
				response.sendRedirect(request.getContextPath() + "/retos");
			}
		}
		if (idContenido != null && idContenido > 0L) {
			// Atributos para los comentarios
			request.setAttribute("urlContenido", "retos");
			request.setAttribute("id", idContenido);
			// Cargar el reto con ese ID y el usuario autor
			RetoDAO retoDAO = new RetoDAO();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				RetoVO reto = retoDAO.getRetoById(idContenido);
				if (reto == null) {
					// El contenido no existe (o no debería ser mostrado)
		            response.sendRedirect(request.getContextPath() + "/retos");
		            return false;
				} else {
					UsuarioVO usuario = usuarioDAO.getUsuarioById(reto.getIdAutor());
					if (usuario == null) {
			            response.sendRedirect(request.getContextPath() + "/error-interno");
					} else {
						request.setAttribute("reto", reto);
						request.setAttribute("autorAlias", usuario.getAlias());
						request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
					}
					
					return true;
				}
			} catch (ErrorInternoException e) {
	            response.sendRedirect(request.getContextPath() + "/error-interno");
	            return false;
			}
		} else {
			return true;
		}
		
    }
		
	
    /**
     * Incluye en la request los comentarios correspondientes al contenido con ID pasado por parámetro ID
     * en una lista de ComentarioVO, junto con un mapa que relaciona ID de usuario con localización de foto de perfil
     * 
     * Recibe un parámetro ID para mostrar los comentarios de ese contenido
     * @return true si se ha incluido correctamente, false si ha habido algún error y por tanto es necesario un redirect
     */
    public static boolean includeComentarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
			
		// Obtener los comentarios para el contenido que se está viendo
		// Si no se obtienen no se redirige a error, simplemente no se muestran
		Long idContenidoIncluido = RequestExtractor.getLong(request, "id");
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		if (idContenidoIncluido != null && idContenidoIncluido > 0) {
			// Obtener los comentarios del contenido y añadirlos a la request
			try {
				List<ComentarioVO> comentarios = comentarioDAO.getComentariosFromContenido(idContenidoIncluido);
				request.setAttribute("comentarios", comentarios);
				// Crear un mapa idUsuario - path a su foto de perfil
				Map<Long, String> profileImages = new HashMap<Long, String>();
				for (ComentarioVO c : comentarios) {
					if (!profileImages.containsKey(c.getIdAutor())){
						String path = ProfilePictureManager.getPathForId(c.getIdAutor());
						if (request.getSession().getServletContext().getResource(path) != null) {
							profileImages.put(c.getIdAutor(), path);
						} else {
							profileImages.put(c.getIdAutor(), ProfilePictureManager.getDefaultPath());
						}
					}
				}
				request.setAttribute("profileImages", profileImages);
				return true;
				
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
				return false;
			}
		} else {
			return true;
		}
			
	}

}
