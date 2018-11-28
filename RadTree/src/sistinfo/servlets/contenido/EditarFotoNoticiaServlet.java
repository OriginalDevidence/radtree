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

import sistinfo.capadatos.vo.NoticiaVO;
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
		NoticiaVO noticia = (NoticiaVO) request.getAttribute("noticia");
		if (noticia == null || usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		} else {

			final int MEMORY_THRESHOLD = 1024 * 1024;
			final int MAX_FILE_SIZE = 1024 * 1024 * 5;
			final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			try {
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(MAX_FILE_SIZE);
				upload.setSizeMax(MAX_REQUEST_SIZE);
				List<FileItem> formItems;
				formItems = upload.parseRequest(request);
				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							String filePath =
								getServletContext().getRealPath("")
								+ "/"
								+ NoticiaPictureManager.getPathForId(noticia.getIdContenido());
							File storeFile = new File(filePath);
							item.write(storeFile);
							
							// Todo OK, ir a gestionar contenido
							response.sendRedirect(request.getContextPath() + "/gestion-contenido");
						}
					}
				}
			} catch (Exception e) {
				// Ha ocurrido algún problema
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}

		}
	}

}