package sistinfo.servlets.jsp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
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
import sistinfo.util.ProfilePictureManager;
import sistinfo.util.RequestExtractor;

public class IncludeInRequest {
	
	/**
     * Incluye en la request la noticia pasada por el parámetro ID, en el atributo noticia (NoticiaVO)
     * 
     * Recibe un parámetro ID de noticia
     */
    public static boolean includeNoticia(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
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
						String path = "images/noticias/" + noticia.getIdContenido() + ".jpg";
						if (request.getSession().getServletContext().getResource(path) != null) {
							noticia.setUrlImagen(request.getContextPath() + "/" + path);
						} else {
							noticia.setUrlImagen(request.getContextPath() + "/images/noticias/default.jpg");
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
     * TODO incluye más cosas
     * Recibe un parámetro ID de pregunta
     */
    public static boolean includePregunta(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
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
     * TODO todos los return
     * Recibe un parámetro ID de reto
     */
    public static boolean includeReto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

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
     */
    public static boolean includeComentarios(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
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
