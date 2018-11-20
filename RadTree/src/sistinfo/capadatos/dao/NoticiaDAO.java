package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

public class NoticiaDAO extends ContenidoDAO {
	
	/**
	 * Búsqueda de noticia por su identificador interno.
	 * @param id
	 * @return La noticia si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public NoticiaVO getNoticiaById(long id) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
    		
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
                    return noticia;
            	}
            }

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Búsqueda de noticias por el identificador de su autor
	 * @param idAutor
	 * @return Lista de noticias de ese autor
	 * @throws ErrorInternoException 
	 */
	public List<NoticiaVO> getNoticiasByAutor(Long idAutor) throws ErrorInternoException {
		List<NoticiaVO> noticias = new ArrayList<NoticiaVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE idAutor=?");
        	stmt.setLong(1, idAutor);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	noticias.add(noticia);
            }

        	stmt.close();
        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return noticias;
	}
	
	/**
	 * Búsqueda de noticias validadas que contienen search en su nombre nombre, cuerpo o URL, por orden de creación (más recientes primero).
	 * @param search
	 * @return Lista con todas las noticias
	 * @throws ErrorInternoException 
	 */
	public List<NoticiaVO> getNoticiasBySearch(String search, int num) throws ErrorInternoException {
		List<NoticiaVO> listNoticia = new ArrayList<NoticiaVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE estado='VALIDADO' AND titulo LIKE ? OR cuerpo LIKE ? OR url LIKE ? ORDER BY fechaRealizacion DESC");
        	stmt.setString(1, "%" + search + "%");
        	stmt.setString(2, "%" + search + "%");
        	stmt.setString(3, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next() && listNoticia.size() < num) {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            }

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * Búsqueda de hasta las últimas num noticias según su fecha de realización.
	 * @param num
	 * @return Lista de hasta num noticias ordenadas por fecha de realización
	 * @throws ErrorInternoException 
	 */
	public List<NoticiaVO> getNoticiasUltimas(int num) throws ErrorInternoException {
		List<NoticiaVO> listNoticia = new ArrayList<NoticiaVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE estado = 'VALIDADO' ORDER BY fechaRealizacion DESC");
            while (rs.next() && listNoticia.size() < num) {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            }

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * Búsqueda de hasta las últimas num noticias según su número de visitas.
	 * @param num
	 * @return Lista de hasta num noticias ordenadas por su número de visitas
	 * @throws ErrorInternoException 
	 */
	public List<NoticiaVO> getNoticiasPopulares(int num) throws ErrorInternoException {
		List<NoticiaVO> listNoticia = new ArrayList<NoticiaVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE estado='VALIDADO' ORDER BY numVisitas DESC");
            while (rs.next() && listNoticia.size() < num) {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            }

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * Inserta una noticia en la base de datos.
	 * @param noticia
	 * @return ID de contenido si la inserción ha sido correcta, -1 si no
	 * @throws ErrorInternoException
	 */
	public Long insertNoticia(NoticiaVO noticia) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();

        	Long idContenido = insertContenido(noticia);
        	
        	if (idContenido > 0) {
        		PreparedStatement stmt = connection.prepareStatement("INSERT INTO Noticia VALUES (?, ?, ?, ?)");
            	stmt.setLong(1, idContenido);
            	stmt.setString(2, noticia.getTitulo());
            	stmt.setString(3, noticia.getCuerpo());
            	stmt.setString(4, noticia.getUrl());
            	int result = stmt.executeUpdate();
            	
            	if (result == 1) {
            		return idContenido;
            	}
            	stmt.close();
        	}

			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return -1L;
	}
	
	/**
	 * Actualiza los datos de una noticia (asumiendo que ya existe una noticia con ese ID).
	 * @param noticia
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateNoticia(NoticiaVO noticia) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	boolean resultContenido = updateContenido(noticia);
        	
        	if (!resultContenido) {
        		return false;
        	}
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Noticia SET titulo=?, cuerpo=?, url=? WHERE idContenido=?");
        	stmt.setString(1, noticia.getTitulo());
        	stmt.setString(2, noticia.getCuerpo());
        	stmt.setString(3, noticia.getUrl());
        	stmt.setLong(4, noticia.getIdContenido());
        	int result = stmt.executeUpdate();
            
        	if (result == 1) {
        		return true;
        	}
        	
        	stmt.close();
        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Elimina a una noticia de la base de datos según su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteNoticia(long id) throws ErrorInternoException {
        return deleteContenido(id);
	}
	
	/**
	 * Extrae los datos de una noticia dado un ResultSet.
	 * IMPORTANTE: El resultado de la consulta debe tener los atributos de Contenido además de Noticia.
	 * @param rs
	 * @return Datos de la noticia de la fila que apunta rs
	 * @throws SQLException
	 */
	private NoticiaVO extractNoticiaFromResultSet(ResultSet rs) throws SQLException {
         NoticiaVO noticia = new NoticiaVO(
     		rs.getLong("idContenido"),
     		rs.getLong("idAutor"),
     		rs.getLong("numVisitas"),
     		rs.getDate("fechaRealizacion"),
     		ContenidoVO.Estado.valueOf(rs.getString("estado")),
     		rs.getString("titulo"),
     		rs.getString("cuerpo"),
     		rs.getString("url")
         );
         return noticia;
	}
	
}
