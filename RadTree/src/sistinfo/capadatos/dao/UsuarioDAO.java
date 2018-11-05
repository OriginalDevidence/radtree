package sistinfo.capadatos.dao;

import java.sql.*;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.excepciones.ErrorInternoException;

public class UsuarioDAO {
	
	/**
	 * Búsqueda de usuario por su identificador interno.
	 * @param id
	 * @return El usuario si el id existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public UsuarioVO getUsuarioById(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE idUsuario=?");
        	stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                    return extractUsuarioFromResultSet(rs);
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	
	/**
	 * Búsqueda de usuario por su alias.
	 * @param alias
	 * @return El usuario si el alias existe, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public UsuarioVO getUsuarioByAlias(String alias) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE alias=?");
        	stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                    return extractUsuarioFromResultSet(rs);
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Login de usuario por su alias y password.
	 * @param alias
	 * @param passwordHash
	 * @return El usuario si los datos de login son correctos, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public UsuarioVO getUsuarioByLoginAlias(String alias, String passwordHash) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE alias=?");
        	stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
            
            // Mover el cursor a la ultima posiciún y comprobar que el resultSet solamente ha devuelto un usuario
            // Si ha devuelto 0 no existe un usuario con ese alias, si devuelve mas de 1 algo raro ha pasado con el query
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                    UsuarioVO user = extractUsuarioFromResultSet(rs);
                    if (checkLogin(user, passwordHash)) {
                    	return user;
                    }
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Login de usuario por su email y password.
	 * @param email
	 * @param passwordHash
	 * @return El usuario si los datos de login son correctos, null en caso contrario
	 * @throws ErrorInternoException 
	 */
	public UsuarioVO getUsuarioByLoginEmail(String email, String passwordHash) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE email=?");
        	stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            // Mover el cursor a la ultima posición y comprobar que el resultSet solamente ha devuelto un usuario
            // Si ha devuelto 0 no existe un usuario con ese alias, si devuelve mas de 1 algo raro ha pasado con el query
            if (rs.last()) {
            	if (rs.getRow() == 1) {
                    UsuarioVO user = extractUsuarioFromResultSet(rs);
                    if (checkLogin(user, passwordHash)) {
                    	return user;
                    }
            	}
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return null;
	}
	
	/**
	 * Inserta un usuario en la base de datos.
	 * @param usuario
	 * @return El ID del usuario insertado o 0 si la inserción ha sido incorrecta
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException 
	 */
	public Long insertUsuario(UsuarioVO usuario) throws UsuarioYaExistenteException, ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	// Comprobar que no exista alguien con ese alias o email ya
    		if (checkAliasYEmailExistente(usuario.getAlias(), usuario.getEmail())) {
            	
            	PreparedStatement stmt = connection.prepareStatement("INSERT INTO Usuario VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)",
            															Statement.RETURN_GENERATED_KEYS);
            	stmt.setString(1, usuario.getAlias());
            	stmt.setString(2, usuario.getNombre());
            	stmt.setString(3, usuario.getApellidos());
            	stmt.setDate(4, usuario.getFechaNacimiento());
            	stmt.setString(5, usuario.getEmail());
            	stmt.setString(6, usuario.getPasswordHash());
            	stmt.setString(7, usuario.getTipoUsuario().toString());
            	stmt.setDouble(8, usuario.getPuntuacion());
            	int result = stmt.executeUpdate();
                
            	if (result == 1) {
            		// Devolver el ID del usuario insertado
            		ResultSet rs = stmt.getGeneratedKeys();
            		if (rs != null && rs.last()) {
            			return rs.getLong(1);
            		}
            	}
            	
    		}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return 0L;
	}
	
	/**
	 * Actualiza los datos de un usuario (asumiendo que ya existe un usuario con ese ID).
	 * @param usuario
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException 
	 */
	public boolean updateUsuario(UsuarioVO usuario, boolean cambiaAlias, boolean cambiaEmail) throws UsuarioYaExistenteException, ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	// Comprobar que no exista alguien con ese alias o email ya
    		if (checkAliasYEmailExistente(cambiaAlias ? usuario.getAlias() : "$", cambiaEmail ? usuario.getEmail() : "$")) {
        	
		    	PreparedStatement stmt = connection.prepareStatement("UPDATE Usuario SET alias=?, nombre=?, apellidos=?, fechaNacimiento=?, email=?, passwordHash=?, tipoUsuario=?, puntuacion=? WHERE idUsuario=?");
		    	stmt.setString(1, usuario.getAlias());
		    	stmt.setString(2, usuario.getNombre());
		    	stmt.setString(3, usuario.getApellidos());
		    	stmt.setDate(4, usuario.getFechaNacimiento());
		    	stmt.setString(5, usuario.getEmail());
		    	stmt.setString(6, usuario.getPasswordHash());
		    	stmt.setString(7, usuario.getTipoUsuario().toString());
		    	stmt.setDouble(8, usuario.getPuntuacion());
		    	stmt.setLong(9, usuario.getIdUsuario());
		
		    	return stmt.executeUpdate() == 1;
		    	
    		}
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return false;
	}
	
	/**
	 * Elimina a un usuario de la base de datos según su id.
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException 
	 */
	public boolean deleteUsuario(long id) throws ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM Usuario WHERE idUsuario=?");
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
	 * Extrae los datos de un usuario dado un ResultSet.
	 * @param rs
	 * @return Datos del usuario de la fila que apunta rs
	 * @throws SQLException
	 */
	private UsuarioVO extractUsuarioFromResultSet(ResultSet rs) throws SQLException {
         UsuarioVO user = new UsuarioVO(
         	rs.getLong("idUsuario"),
         	rs.getString("alias"),
         	rs.getString("nombre"),
         	rs.getString("apellidos"),
         	rs.getDate("fechaNacimiento"),
         	rs.getString("email"),
         	rs.getString("passwordHash"),
         	UsuarioVO.TipoUsuario.valueOf(rs.getString("tipoUsuario")),
         	rs.getDouble("puntuacion")
         );
         return user;
	}
	
	/**
	 * Comprueba si los datos de login para el usuario son correctos.
	 * @param usuario
	 * @param passwordHash
	 * @return true si las contraseúas coinciden, false en caso contrario
	 */
	private boolean checkLogin(UsuarioVO usuario, String passwordHash) {
		return usuario.getPasswordHash().equals(passwordHash);
	}
	
	/**
	 * Comprueba si ya existe un usuario en la base de datos con el alias o email pasados, y lanza un error en caso afirmativo.
	 * @param alias
	 * @param email
	 * @return true si no existe ningún usuario con ese alias o email
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException
	 */
	private boolean checkAliasYEmailExistente(String alias, String email) throws UsuarioYaExistenteException, ErrorInternoException {
		Connection connection = ConnectionFactory.getConnection();
        try {
        	
        	// Comprobación de alias
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE alias=?");
        	stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
        	boolean aliasExistente = rs.next();
            
            // Comprobación de email
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE email=?");
        	stmt.setString(1, email);
            rs = stmt.executeQuery();
        	boolean emailExistente = rs.next();
        	
        	if (aliasExistente || emailExistente) {
        		throw new UsuarioYaExistenteException(aliasExistente, emailExistente);
        	}
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErrorInternoException();
        }
        return true;
	}
	
}
