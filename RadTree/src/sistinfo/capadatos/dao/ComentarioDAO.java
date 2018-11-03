package sistinfo.capadatos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ComentarioVO;
import sistinfo.excepciones.ErrorInternoException;

public class ComentarioDAO {
	
	/**
	 * Búsqueda de comentarios por un identificador de contenido.
	 * @param id
	 * @return Lista de comentarios pertenecientes al contenido idContenido
	 * @throws ErrorInternoException 
	 */
	public ArrayList<ComentarioVO> getComentariosFromContenido(long idContenido) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        ArrayList<ComentarioVO> comentarios = new ArrayList<ComentarioVO>();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT C1.idComentario, C1.idAutor, C1.idContenido, C1.cuerpo, C1.numLikes, C1.fecha, C1.respuestaDe, U1.alias \'autor\', U2.alias \'autorPadre\'"
	    			+ " FROM Comentario C1"
	    			+ " INNER JOIN Usuario U1 ON C1.idAutor = U1.idUsuario"
	    			+ " LEFT OUTER JOIN Comentario C2 ON C1.respuestaDe = C2.idComentario"
	    			+ " LEFT OUTER JOIN Usuario U2 on C2.idAutor = U2.idUsuario"
	    			+ " WHERE C1.idContenido = ?"
	    			+ " ORDER BY C1.fecha ASC");
        	stmt.setLong(1, idContenido);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	// Añadir el comentario
	        	ComentarioVO com = extractComentarioFromResultSet(rs);
	        	comentarios.add(com);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return comentarios;
	}
	
	/**
	 * Inserta un comentario en la base de datos, con valor 0 en numLikes y la fecha actual en fecha.
	 * @param comentario
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean insertComentario(ComentarioVO comentario) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Comentario VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
        	stmt.setLong(1, comentario.getIdAutor());
        	stmt.setLong(2, comentario.getIdContenido());
        	stmt.setString(3, comentario.getCuerpo());
        	stmt.setLong(4, 0); // numLikes
        	stmt.setDate(5, new java.sql.Date(Calendar.getInstance().getTime().getTime())); // fecha
        	stmt.setLong(6, comentario.getRespuestaDe());
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
	 * Actualiza los datos de un comentario (asumiendo que ya existe un comentario con ese ID).
	 * @param comentario
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateComentario(ComentarioVO comentario) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Comentario SET idAutor=?, idContenido=?, cuerpo=?, numLikes=?, fecha=?, respuestaDe=? WHERE idComentario=?");
        	stmt.setLong(1, comentario.getIdAutor());
        	stmt.setLong(2, comentario.getIdContenido());
        	stmt.setString(3, comentario.getCuerpo());
        	stmt.setLong(4, comentario.getNumLikes());
        	stmt.setDate(5, comentario.getFecha());
        	stmt.setLong(6, comentario.getRespuestaDe());
        	stmt.setLong(7, comentario.getIdComentario());
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
	 * Elimina un comentario de la base de datos por su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteComentario(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Comentario WHERE idComentario=?");
        	stmt.setLong(1, id);
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
         	rs.getInt("numLikes"),
         	rs.getDate("fecha"),
         	rs.getLong("respuestaDe"),
         	rs.getString("autor"),
         	rs.getString("autorPadre")
         );
         return user;
	}
	
}
