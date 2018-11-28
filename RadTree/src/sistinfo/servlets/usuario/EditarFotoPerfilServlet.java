package sistinfo.servlets.usuario;

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

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.util.ProfilePictureManager;

@SuppressWarnings("serial")
public class EditarFotoPerfilServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * TODO
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// Comprobar que el usuario está logueado
		UsuarioVO usuario = (UsuarioVO) request.getSession().getAttribute("usuario");
		if (usuario == null) {
			System.out.println("usuario no log");
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
								+ ProfilePictureManager.getPathForId(usuario.getIdUsuario());
							File storeFile = new File(filePath);
							item.write(storeFile);
							
							// Todo OK, ir al perfil
							response.sendRedirect(request.getContextPath() + "/perfil");
						}
					}
				}
			} catch (Exception e) {
				// Ha ocurrido algún problema
				System.out.println("problema: " + e);
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}

		}
	}

}