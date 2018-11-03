package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.LinkedList;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

public class RetoDAO extends ContenidoDAO {
	
	/**
	 * B�squeda de reto por su identificador interno.
	 * @param id
	 * @return El reto si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public RetoVO getRetoById(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reto NATURAL JOIN Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	RetoVO reto = extractRetoFromResultSet(rs);
    	            return reto;
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * B�squeda de retos que contienen search en su nombre titulo o cuerpo, por orden de creaci�n (m�s recientes primero).
	 * @param search
	 * @return Lista con todas los retos
	 * @throws ErrorInternoException 
	 */
	public LinkedList<RetoVO> getRetoBySearch(String search) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<RetoVO> listReto = new LinkedList<RetoVO>();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reto NATURAL JOIN Contenido WHERE titulo LIKE '%?%' OR cuerpo LIKE '%?%' ORDER BY fechaRealizacion DESC");
        	stmt.setString(1, search);
        	stmt.setString(2, search);
            ResultSet rs = stmt.executeQuery();
            
            do {
            	RetoVO reto = extractRetoFromResultSet(rs);
            	listReto.add(reto);
            } while (rs.next());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listReto;
	}
	
	/**
	 * B�squeda de hasta los �ltimos num retos seg�n su fecha de realizaci�n.
	 * @param num
	 * @return Lista de hasta num retos ordenados por fecha de realizaci�n
	 * @throws ErrorInternoException 
	 */
	public LinkedList<RetoVO> getRetosUltimos(int num) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
		LinkedList<RetoVO> listReto = new LinkedList<RetoVO>();
        try {
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reto NATURAL JOIN Contenido ORDER BY fechaRealizacion DESC");
            do {
            	RetoVO reto = extractRetoFromResultSet(rs);
            	listReto.add(reto);
            } while (rs.next() && listReto.size() < num);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listReto;
	}
	
	/**
	 * Inserta un reto en la base de datos.
	 * @param reto
	 * @return true si la inserci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean insertReto(RetoVO reto) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	int idContenido = insertContenido(reto);
        	
        	if (idContenido > 0) {
            	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Reto VALUES (?, ?, ?)");
            	stmt.setLong(1, idContenido);
            	stmt.setString(2, reto.getTitulo());
            	stmt.setString(3, reto.getCuerpo());
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
	 * Actualiza los datos de un reto (asumiendo que ya existe un reto con ese ID).
	 * @param reto
	 * @return true si la actualizaci�n ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateReto(RetoVO reto) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	boolean resultContenido = updateContenido(reto);
        	
        	if (!resultContenido) {
        		return false;
        	}
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Reto SET titulo=?, cuerpo=? WHERE idContenido=?");
        	stmt.setString(1, reto.getTitulo());
        	stmt.setString(2, reto.getCuerpo());
        	stmt.setLong(3, reto.getIdContenido());
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
	 * Elimina a un reto de la base de datos seg�n su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteReto(long id) throws ErrorInternoException {
		return deleteContenido(id);
	}
	
	/**
	 * Extrae los datos de un reto dado un ResultSet.
	 * IMPORTANTE: El resultado de la consulta debe tener los atributos de Contenido adem�s de Reto.
	 * @param rs
	 * @return Datos del reto de la fila que apunta rs
	 * @throws SQLException
	 */
	private RetoVO extractRetoFromResultSet(ResultSet rs) throws SQLException {
         RetoVO reto = new RetoVO(
          	rs.getLong("idContenido"),
          	rs.getLong("idAutor"),
          	rs.getLong("numVisitas"),
          	rs.getDate("fechaRealizacion"),
          	ContenidoVO.Estado.valueOf(rs.getString("estado")),
         	rs.getString("titulo"),
         	rs.getString("cuerpo")
         );
         return reto;
	}
	
}
