package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.excepciones.ErrorInternoException;

public class ContenidoDAO {
	
	/**
	 * Incrementa en uno las visitas a un contenido
	 * @param id ID del contenido a actualizar
	 * @throws ErrorInternoException 
	 */
	public void incrementNumVisitas(Long idContenido) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Contenido SET numVisitas = numVisitas + 1 WHERE idContenido = ?");
        	stmt.setLong(1, idContenido);
        	stmt.executeUpdate();
        	
        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
	}
	
	/**
	 * Obtiene el número de contenidos que están en la cola de validación (estado = PENDIENTE)
	 * @return Número de elementos de la cola de validación
	 * @throws ErrorInternoException 
	 */
	public int getNumContenidosInColaValidacion() throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Statement stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Contenido WHERE estado='PENDIENTE'");
            
        	if (rs.last()) {
        		if (rs.getRow() == 1) {
        			return rs.getInt(1);
        		}
        	}
        	
        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return 0;
	}
	
	/**
	 * Obtiene una lista con los ID de contenidos que están en la cola de validación (estado = PENDIENTE)
	 * @return Número de elementos de la cola de validación
	 * @throws ErrorInternoException 
	 */
	public List<Long> getContenidosInColaValidacion() throws ErrorInternoException {
		List<Long> contenidos = new ArrayList<Long>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Statement stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery("SELECT idContenido FROM Contenido WHERE estado='PENDIENTE'");
            
        	while (rs.next()) {
        		contenidos.add(rs.getLong(1));
        	}

        	stmt.close();
			connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return contenidos;
	}
	
	/**
	 * Cambia el estado de un contenido según su id.
	 * @param id
	 * @param nuevoEstado
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean updateEstado(Long id, Estado nuevoEstado) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Contenido SET estado=? WHERE idContenido=?");
        	stmt.setString(1, nuevoEstado.toString());
        	stmt.setLong(2, id);
        	int result = stmt.executeUpdate();
            
        	if (result == 1) {
            	stmt.close();
    			connection.close();
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
	 * Búsqueda de contenido por su identificador interno.
	 * @param id
	 * @return El contenido si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	protected ContenidoVO getContenidoById(long id) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
    		
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
                	stmt.close();
        			connection.close();
            		return cont;
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
	 * Inserta un contenido en la base de datos
	 * @param contenido
	 * @return El idContenido del contenido recién insertado (mayor que 0 si es correcto, diferente o null si algo ha salido mal)
	 * @throws ErrorInternoException 
	 */
	protected Long insertContenido(ContenidoVO contenido) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
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

        	stmt.close();
			connection.close();
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
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Contenido SET idAutor=?, numVisitas=?, fechaRealizacion=?, estado=? WHERE idContenido=?");
        	stmt.setLong(1, contenido.getIdAutor());
        	stmt.setLong(2, contenido.getNumVisitas());
        	stmt.setDate(3, contenido.getFechaRealizacion());
        	stmt.setString(4, contenido.getEstado().toString());
        	stmt.setLong(5, contenido.getIdContenido());
        	int result = stmt.executeUpdate();
            
        	if (result == 1) {
            	stmt.close();
    			connection.close();
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
	 * Elimina un contenido de la base de datos por su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contario
	 * @throws ErrorInternoException 
	 */
	protected boolean deleteContenido(long id) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
        	int result = stmt.executeUpdate();
            
        	if (result == 1) {
            	stmt.close();
    			connection.close();
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
	
}
