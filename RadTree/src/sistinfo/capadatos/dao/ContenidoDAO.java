package sistinfo.capadatos.dao;
import java.sql.*;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.excepciones.ErrorInternoException;

public class ContenidoDAO {
	
	/**
	 * Obtiene el número de contenidos que están en la cola de validación (estado = PENDIENTE)
	 * @return Número de elementos de la cola de validación
	 * @throws ErrorInternoException 
	 */
	public int getNumContenidosInColaValidacion() throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	Statement stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Contenido WHERE estado='PENDIENTE'");
            
        	if (rs.last()) {
        		if (rs.getRow() == 1) {
        			return rs.getInt(1);
        		}
        	}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return 0;
	}
	
	/**
	 * Cambia el estado de un contenido según su id.
	 * @param id
	 * @param nuevoEstado
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 */
	public boolean updateEstado(Long id, ContenidoVO.Estado nuevoEstado) {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Contenido SET estado=? WHERE idContenido=?");
        	stmt.setString(1, nuevoEstado.toString());
        	stmt.setLong(2, id);
        	int result = stmt.executeUpdate();
            
        	if (result == 1) {
        		return true;
        	}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
	}
	
	/**
	 * Búsqueda de contenido por su identificador interno.
	 * @param id
	 * @return El contenido si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	protected ContenidoVO getContenidoById(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
            		ContenidoVO cont = new ContenidoVO(
            			rs.getLong("idContenido"),
            			rs.getLong("idAutor"),
            			rs.getLong("numVisitas"),
            			rs.getDate("fechaRealizacion"),
            			ContenidoVO.Estado.valueOf(rs.getString("estado"))
            		);
            		return cont;
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Inserta un contenido en la base de datos
	 * @param contenido
	 * @return El idContenido del contenido reci�n insertado (mayor que 0 si es correcto, menor o igual si ha salido mal)
	 * @throws ErrorInternoException 
	 */
	protected Long insertContenido(ContenidoVO contenido) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Contenido VALUES (NULL, ?, ?, ?, ?)",
																	Statement.RETURN_GENERATED_KEYS);
        	stmt.setLong(1, contenido.getIdAutor());
        	stmt.setLong(2, contenido.getNumVisitas()); 
        	stmt.setDate(3, contenido.getFechaRealizacion());
        	stmt.setString(4, contenido.getEstado().toString());
        	int result = stmt.executeUpdate();

        	if (result == 1) {
        		// Devolver el ID del usuario insertado
        		ResultSet rs = stmt.getGeneratedKeys();
        		if (rs != null && rs.last()) {
        			return rs.getLong(1);
        		}
        	}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Actualiza los datos de un contenido (asumiendo que ya existe un comentario con ese ID).
	 * @param contenido
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	protected boolean updateContenido(ContenidoVO contenido) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Contenido SET idAutor=?, numVisitas=?, fechaRealizacion=?, estado=? WHERE idContenido=?");
        	stmt.setLong(1, contenido.getIdAutor());
        	stmt.setLong(2, contenido.getNumVisitas());
        	stmt.setDate(3, contenido.getFechaRealizacion());
        	stmt.setString(4, contenido.getEstado().toString());
        	stmt.setLong(5, contenido.getIdContenido());
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
	 * Elimina un contenido de la base de datos por su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contario
	 * @throws ErrorInternoException 
	 */
	protected boolean deleteContenido(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Contenido WHERE idContenido=?");
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
	
}
