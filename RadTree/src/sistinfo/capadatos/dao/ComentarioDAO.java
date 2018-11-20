package sistinfo.capadatos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ComentarioVO;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.excepciones.ErrorInternoException;

public class ComentarioDAO {

	/**
	 * Añade a cada contenido de la lista el número de comentarios correspondientes a ese contenido
	 * @param id
	 * @return Lista de comentarios pertenecientes al contenido idContenido
	 * @throws ErrorInternoException 
	 */
	public <T extends ContenidoVO> List<T> addNumComentariosToContenido(List<T> contenidos) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();
			
			for (T contenido : contenidos) {
				PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM Comentario WHERE idContenido=?");
	        	stmt.setLong(1, contenido.getIdContenido());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.last() && rs.getRow() == 1) {
	            	contenido.setNumComentarios(rs.getLong(1));
	            }
	            stmt.close();
			}
			
			connection.close();
		} catch (SQLException ex) {
	        ex.printStackTrace();
	        throw new ErrorInternoException();
	    }
	    return contenidos;
	}
	
	/**
	 * Búsqueda de comentarios por un identificador de contenido.
	 * @param id
	 * @return Lista de comentarios pertenecientes al contenido idContenido
	 * @throws ErrorInternoException 
	 */
	public List<ComentarioVO> getComentariosFromContenido(Long idContenido) throws ErrorInternoException {
		return getComentariosFromContenido(idContenido, null);
	}
	
	/**
	 * Inserta un comentario en la base de datos
	 * @param comentario
	 * @return true si la inserción ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean insertComentario(ComentarioVO comentario) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Comentario VALUES (NULL, ?, ?, ?, ?, ?)");
        	stmt.setLong(1, comentario.getIdAutor());
        	stmt.setLong(2, comentario.getIdContenido());
        	stmt.setString(3, comentario.getCuerpo());
        	stmt.setDate(4, comentario.getFecha());
        	if (comentario.getRespuestaDe() == null) {
            	stmt.setNull(5, java.sql.Types.BIGINT);
        	} else {
            	stmt.setLong(5, comentario.getRespuestaDe());
        	}
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
	 * Actualiza los datos de un comentario (asumiendo que ya existe un comentario con ese ID).
	 * @param comentario
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateComentario(ComentarioVO comentario) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Comentario SET idAutor=?, idContenido=?, cuerpo=?, fecha=?, respuestaDe=? WHERE idComentario=?");
        	stmt.setLong(1, comentario.getIdAutor());
        	stmt.setLong(2, comentario.getIdContenido());
        	stmt.setString(3, comentario.getCuerpo());
        	stmt.setDate(4, comentario.getFecha());
        	stmt.setLong(5, comentario.getRespuestaDe());
        	stmt.setLong(6, comentario.getIdComentario());
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
	 * Elimina un comentario de la base de datos por su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteComentario(long id) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Comentario WHERE idComentario=?");
        	stmt.setLong(1, id);
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
	 * Búsqueda de comentarios por un identificador de contenido y según a quién respondan
	 * @param id
	 * @return Lista de comentarios pertenecientes al contenido idContenido y respondiendo al comentario respuestaDe
	 * @throws ErrorInternoException 
	 */
	private List<ComentarioVO> getComentariosFromContenido(Long idContenido, Long respuestaDe) throws ErrorInternoException {
        List<ComentarioVO> comentarios = new ArrayList<ComentarioVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT idComentario, idAutor, idContenido, cuerpo, fecha, respuestaDe, alias \'autor\'"
	    			+ " FROM Comentario C"
	    			+ " INNER JOIN Usuario U ON C.idAutor = U.idUsuario"
	    			+ " WHERE idContenido = ? AND respuestaDe" + (respuestaDe == null ? " IS NULL" : "= ?")
	    			+ " ORDER BY fecha ASC");
        	stmt.setLong(1, idContenido);
        	if (respuestaDe != null) {
            	stmt.setLong(2, respuestaDe);
        	}
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	// Añadir el comentario
	        	ComentarioVO com = extractComentarioFromResultSet(rs);
	        	comentarios.add(com);
	        	List<ComentarioVO> hijos = getComentariosFromContenido(idContenido, com.getIdComentario());
	        	for (ComentarioVO c : hijos) {
	        		if (c.getAutorPadre() == null) {
		        		c.setAutorPadre(com.getAutor());
	        		}
	        	}
	        	comentarios.addAll(hijos);
            }

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return comentarios;
	}
	
	/**
	 * Extrae los datos de un comentario dado un ResultSet.
	 * @param rs
	 * @return Datos del comentario de la fila que apunta rs
	 * @throws SQLException
	 */
	private ComentarioVO extractComentarioFromResultSet(ResultSet rs) throws SQLException {
         ComentarioVO user = new ComentarioVO(
         	rs.getLong("idComentario"),
         	rs.getLong("idAutor"),
         	rs.getLong("idContenido"),
         	rs.getString("cuerpo"),
         	rs.getDate("fecha"),
         	rs.getLong("respuestaDe"),
         	rs.getString("autor")
         );
         return user;
	}
	
}
