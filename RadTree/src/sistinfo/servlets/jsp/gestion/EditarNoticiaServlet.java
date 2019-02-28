package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class EditarNoticiaServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de editarNoticia.jsp incluyendo en sus atributos la noticia (NoticiaVO)
     * 
     * Recibe como parámetro el ID de la noticia a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeNoticia(request, response)) {
        	request.getRequestDispatcher("/jsp/gestion/editarNoticia.jsp").forward(request, response);
    	}
			
	}

}
