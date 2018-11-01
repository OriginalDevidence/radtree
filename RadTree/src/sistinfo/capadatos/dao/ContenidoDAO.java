package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.Calendar;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.excepciones.ErrorInternoException;

public abstract class ContenidoDAO {
	
	/**
	 * Cambia el estado de un contenido seg�n su id.
	 * @param id
	 * @param nuevoEstado
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 */
	public boolean updateEstado(long id, ContenidoVO.Estado nuevoEstado) {
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
	 * B�squeda de contenido por su identificador interno.
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
	 * Inserta un contenido en la base de datos, con valor 0 en numVisitas, la fecha actual en fechaRealizacion, y "pendiente" en estado.
	 * @param contenido
	 * @return El idContenido del contenido reci�n insertado (mayor que 0 si es correcto, menor o igual si ha salido mal)
	 * @throws ErrorInternoException 
	 */
	protected int insertContenido(ContenidoVO contenido) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Reto VALUES (NULL, ?, ?, ?, ?)");
        	stmt.setLong(1, contenido.getIdAutor());
        	stmt.setLong(2, 0); // numVisitas
        	stmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        	stmt.setString(4, "pendiente");
        	int result = stmt.executeUpdate(stmt.toString(), Statement.RETURN_GENERATED_KEYS);
            
        	return result;
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
	}
	
	/**
	 * Actualiza los datos de un contenido (asumiendo que ya existe un comentario con ese ID).
	 * @param contenido
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
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
