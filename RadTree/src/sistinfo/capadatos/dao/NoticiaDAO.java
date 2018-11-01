package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.LinkedList;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

public class NoticiaDAO extends ContenidoDAO {
	
	/**
	 * B�squeda de noticia por su identificador interno.
	 * @param id
	 * @return La noticia si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public NoticiaVO getNoticiaById(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
                    return noticia;
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * B�squeda de noticias que contienen search en su nombre nombre, cuerpo o URL, por orden de creaci�n (m�s recientes primero).
	 * @param search
	 * @return Lista con todas las noticias
	 * @throws ErrorInternoException 
	 */
	public LinkedList<NoticiaVO> getNoticiaBySearch(String search) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<NoticiaVO> listNoticia = new LinkedList<NoticiaVO>();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Noticia NATURAL JOIN Contenido WHERE titulo LIKE '%?%' OR cuerpo LIKE '%?%' OR url LIKE '%?%' ORDER BY fechaRealizacion DESC");
        	stmt.setString(1, search);
        	stmt.setString(2, search);
        	stmt.setString(3, search);
            ResultSet rs = stmt.executeQuery();
            
            do {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            } while (rs.next());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * B�squeda de hasta las �ltimas num noticias seg�n su fecha de realizaci�n.
	 * @param num
	 * @return Lista de hasta num noticias ordenadas por fecha de realizaci�n
	 * @throws ErrorInternoException 
	 */
	public LinkedList<NoticiaVO> getNoticiasUltimas(int num) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<NoticiaVO> listNoticia = new LinkedList<NoticiaVO>();
        try {
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Noticia NATURAL JOIN Contenido ORDER BY fechaRealizacion DESC");
            do {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            } while (rs.next() && listNoticia.size() < num);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * B�squeda de hasta las �ltimas num noticias seg�n su n�mero de visitas.
	 * @param num
	 * @return Lista de hasta num noticias ordenadas por su n�mero de visitas
	 * @throws ErrorInternoException 
	 */
	public LinkedList<NoticiaVO> getNoticiasPopulares(int num) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<NoticiaVO> listNoticia = new LinkedList<NoticiaVO>();
        try {
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Noticia NATURAL JOIN Contenido ORDER BY numVisitas DESC");
            do {
            	NoticiaVO noticia = extractNoticiaFromResultSet(rs);
            	listNoticia.add(noticia);
            } while (rs.next() && listNoticia.size() < num);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listNoticia;
	}
	
	/**
	 * Inserta una noticia en la base de datos.
	 * @param reto
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean insertNoticia(NoticiaVO noticia) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {

        	int idContenido = insertContenido(noticia);
        	
        	if (idContenido > 0) {
        		PreparedStatement stmt = connection.prepareStatement("INSERT INTO Noticia VALUES (?, ?, ?, ?)");
            	stmt.setLong(1, idContenido);
            	stmt.setString(2, noticia.getTitulo());
            	stmt.setString(3, noticia.getCuerpo());
            	stmt.setString(4, noticia.getUrl());
            	int result = stmt.executeUpdate();
            	
            	if (result == 1) {
            		return true;
            	}
        	}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Actualiza los datos de una noticia (asumiendo que ya existe una noticia con ese ID).
	 * @param noticia
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateNoticia(NoticiaVO noticia) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
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
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Elimina a una noticia de la base de datos seg�n su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteNoticia(long id) throws ErrorInternoException {
        return deleteContenido(id);
	}
	
	/**
	 * Extrae los datos de una noticia dado un ResultSet.
	 * IMPORTANTE: El resultado de la consulta debe tener los atributos de Contenido adem�s de Noticia.
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
