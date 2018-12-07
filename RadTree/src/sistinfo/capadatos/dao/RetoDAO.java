package sistinfo.capadatos.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ContenidoVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

public class RetoDAO extends ContenidoDAO {
	
	/**
	 * Búsqueda de reto por su identificador interno.
	 * @param id
	 * @return El reto si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public RetoVO getRetoById(Long id) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reto NATURAL JOIN Contenido WHERE idContenido=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	RetoVO reto = extractRetoFromResultSet(rs);
    	            return reto;
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
	 * Búsqueda de retos por el identificador de su autor
	 * @param idAutor
	 * @return Lista de retos de ese autor
	 * @throws ErrorInternoException 
	 */
	public List<RetoVO> getRetosByAutor(Long idAutor) throws ErrorInternoException {
		List<RetoVO> retos = new ArrayList<RetoVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reto NATURAL JOIN Contenido WHERE idAutor=?");
        	stmt.setLong(1, idAutor);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	RetoVO reto = extractRetoFromResultSet(rs);
            	retos.add(reto);
            }

        	stmt.close();
        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return retos;
	}
	
	/**
	 * Búsqueda de hasta num retos validados que contienen search en su título o cuerpo, ordenados por su fecha de realización
	 * @param search
	 * @param num
	 * @return Lista con todas los retos
	 * @throws ErrorInternoException 
	 */
	public List<RetoVO> getRetosBySearch(String search, int num) throws ErrorInternoException {
		List<RetoVO> listReto = new ArrayList<RetoVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reto NATURAL JOIN Contenido WHERE estado='VALIDADO' AND titulo LIKE ? OR cuerpo LIKE ? ORDER BY fechaRealizacion DESC");
        	stmt.setString(1, "%" + search + "%");
        	stmt.setString(2, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next() && listReto.size() < num) {
            	RetoVO reto = extractRetoFromResultSet(rs);
            	listReto.add(reto);
            }

        	stmt.close();
        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listReto;
	}
	
	/**
	 * Búsqueda de hasta los últimos num retos validados ordenados por su fecha de realización
	 * @param num
	 * @return Lista de hasta num retos ordenados por fecha de realización
	 * @throws ErrorInternoException 
	 */
	public List<RetoVO> getRetosUltimos(int num) throws ErrorInternoException {
		List<RetoVO> listReto = new ArrayList<RetoVO>();
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reto NATURAL JOIN Contenido WHERE estado='VALIDADO' ORDER BY fechaRealizacion DESC");
            while (rs.next() && listReto.size() < num) {
            	RetoVO reto = extractRetoFromResultSet(rs);
            	listReto.add(reto);
            }

        	stmt.close();
        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return listReto;
	}
	
	/**
	 * Inserta un reto en la base de datos.
	 * @param reto
	 * @return ID de contenido si la inserción ha sido correcta, -1 si no
	 * @throws ErrorInternoException
	 */
	public Long insertReto(RetoVO reto) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	Long idContenido = insertContenido(reto);
        	
        	if (idContenido > 0) {
            	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Reto VALUES (?, ?, ?)");
            	stmt.setLong(1, idContenido);
            	stmt.setString(2, reto.getTitulo());
            	stmt.setString(3, reto.getCuerpo());
            	int result = stmt.executeUpdate();

            	stmt.close();
            	if (result == 1) {
            		return idContenido;
            	}
        	}

        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return -1L;
	}
	
	/**
	 * Actualiza los datos de un reto (asumiendo que ya existe un reto con ese ID).
	 * @param reto
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean updateReto(RetoVO reto) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	boolean resultContenido = updateContenido(reto);
        	
        	if (!resultContenido) {
        		return false;
        	}
        	
        	PreparedStatement stmt = connection.prepareStatement("UPDATE Reto SET titulo=?, cuerpo=? WHERE idContenido=?");
        	stmt.setString(1, reto.getTitulo());
        	stmt.setString(2, reto.getCuerpo());
        	stmt.setLong(3, reto.getIdContenido());
        	int result = stmt.executeUpdate();

        	stmt.close();
        	if (result == 1) {
        		return true;
        	}

        	connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Elimina a un reto de la base de datos según su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteReto(Long id) throws ErrorInternoException {
		return deleteContenido(id);
	}
	
	/**
	 * Extrae los datos de un reto dado un ResultSet.
	 * IMPORTANTE: El resultado de la consulta debe tener los atributos de Contenido además de Reto.
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
