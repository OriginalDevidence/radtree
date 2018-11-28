package sistinfo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ProfilePictureManager {
	
	/**
	 * TODO
	 * @throws ServletException 
	 * @throws IOException 
	 */
	public static boolean saveImageFromRequest(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException, ServletException {
		// Intentar obtener imagen de request
		Part filePart = request.getPart("imagen");
		if (filePart == null) {
			// No ha subido imagen
			return true;
		} else {
			// Si ha subido imagen, guardarla en su sitio
			boolean ok = true;
			OutputStream out = null;
			InputStream filecontent = null;

			try {
				out = new FileOutputStream(new File(getPathForId(id)));
				filecontent = filePart.getInputStream();

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (FileNotFoundException fne) {
				ok = false;
			} finally {
				if (out != null) {
					out.close();
				}
				if (filecontent != null) {
					filecontent.close();
				}
			}
			return ok;
		}
	}
	
	/**
	 * Devuelve la ruta a donde tendría que estar la foto de perfil del usuario con ese id
	 * @param id
	 * @return
	 */
	public static String getPathForId(Long id) {
		return "images/profile/" + id.toString() + ".jpg";
	}
	
	/**
	 * Devuelve la ruta a la foto de perfil por defecto
	 * @param id
	 * @return
	 */
	public static String getDefaultPath() {
		return "images/profile/default.jpg";
	}
	
}
