package sistinfo.servlets.contenido;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.util.NoticiaPictureManager;

@SuppressWarnings("serial")
public class EditarFotoNoticiaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Edita la foto de perfil de un usuario, obtenida en la request, y vuelve a su perfil
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// Comprobar que el usuario está logueado
		UsuarioVO usuario = (UsuarioVO) request.getSession().getAttribute("usuario");
		if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		} else {

			final int MEMORY_THRESHOLD = 1024 * 1024;
			final int MAX_FILE_SIZE = 1024 * 1024 * 5;
			final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			try {
				// No es el método más limpio, pero la idea es que es posible subir imágenes para las noticias
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(MAX_FILE_SIZE);
				upload.setSizeMax(MAX_REQUEST_SIZE);
				List<FileItem> formItems;
				formItems = upload.parseRequest(request);
				if (formItems != null && formItems.size() > 0) {
					Long idContenido = null;
					// Obtener ID contenido pasado
					for (FileItem item : formItems) {
						if (item.isFormField()) {
							String stringValue = item.getString();
							if (stringValue == null || stringValue.trim().isEmpty()) {
								response.sendRedirect(request.getContextPath() + "/error-interno");
								return;
							}
							try {
								idContenido = Long.parseLong(stringValue);
							} catch (NumberFormatException e) {
								response.sendRedirect(request.getContextPath() + "/error-interno");
								return;
							}
						}
					}
					// Almacenar la imagen
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							String filePath =
								getServletContext().getRealPath("")
								+ "/"
								+ NoticiaPictureManager.getPathForId(idContenido);
							File storeFile = new File(filePath);
							item.write(storeFile);
							
							// Todo OK, ir a gestionar contenido
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						}
					}
					// Cambiar el estado a pendiente
					ContenidoDAO contenidoDAO = new ContenidoDAO();
					contenidoDAO.updateEstado(idContenido, Estado.PENDIENTE);
				} else {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			} catch (Exception e) {
				// Ha ocurrido algún problema
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}

		}
	}

}