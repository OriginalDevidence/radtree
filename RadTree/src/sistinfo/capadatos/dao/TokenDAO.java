package sistinfo.capadatos.dao;
import java.sql.*;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.TokenVO;
import sistinfo.excepciones.ErrorInternoException;

public class TokenDAO {
	
	/**
	 * Obtiene un token dado su identificador único
	 * @param token
	 * @return
	 * @throws ErrorInternoException
	 */
	public TokenVO getTokenByToken(String token) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Token WHERE token=?");
        	stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	TokenVO tokenObj = extractTokenFromResultSet(rs);
    	            return tokenObj;
            	}
            	stmt.close();
            	connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Obtiene un token dado su usuario asociado
	 * @param idUsuario
	 * @return
	 * @throws ErrorInternoException
	 */
	public TokenVO getTokenByUsuario(Long idUsuario) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Token WHERE idUsuario=?");
        	stmt.setLong(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.last()) {
            	if (rs.getRow() == 1) {
                	TokenVO tokenObj = extractTokenFromResultSet(rs);
    	            return tokenObj;
            	}
            	stmt.close();
            	connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Inserta un token en la base de datos.
	 * @param token
	 * @return true si la inserción ha ido bien, false si no
	 * @throws ErrorInternoException
	 */
	public boolean insertToken(TokenVO token) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Token VALUES (?, ?)");
        	stmt.setString(1, token.getToken());
        	stmt.setLong(2, token.getIdUsuario());
        	int result = stmt.executeUpdate();

        	stmt.close();
        	connection.close();
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
	 * Elimina a un token de la base de datos según su contenido.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteToken(TokenVO token) throws ErrorInternoException {
        try {
    		Connection connection = ConnectionFactory.getConnection();
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Token WHERE token=?");
        	stmt.setString(1, token.getToken());
        	int result = stmt.executeUpdate();

        	stmt.close();
        	connection.close();
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
	 * Extrae los datos de un token dado un ResultSet.
	 * @param rs
	 * @return Datos del token de la fila que apunta rs
	 * @throws SQLException
	 */
	private TokenVO extractTokenFromResultSet(ResultSet rs) throws SQLException {
         TokenVO token = new TokenVO (
            rs.getString("token"),
          	rs.getLong("idUsuario")
         );
         return token;
	}
	
}
