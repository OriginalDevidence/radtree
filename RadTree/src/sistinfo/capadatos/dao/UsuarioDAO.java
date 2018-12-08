package sistinfo.capadatos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sistinfo.capadatos.jdbc.ConnectionFactory;
import sistinfo.capadatos.vo.ClasificacionVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.servlets.jsp.UsuariosServlet.FiltroUsuario;
import sistinfo.excepciones.ErrorInternoException;

public class UsuarioDAO {

	/**
	 * Obtiene una lista de los num primeros usuarios de la clasificación, con
	 * información de alias, preguntas contestadas y puntuación
	 * 
	 * @param num Número de usuarios a mostrar
	 * @return Lista con los datos de la clasificación
	 * @throws ErrorInternoException
	 */
	public List<ClasificacionVO> getClasificacion(int num) throws ErrorInternoException {
		List<ClasificacionVO> clasificacion = new ArrayList<ClasificacionVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT U.idUsuario, alias, puntuacion, COUNT(DISTINCT idPregunta) 'contestadas' FROM Contesta C NATURAL JOIN Respuesta RIGHT OUTER JOIN Usuario U ON U.idUsuario = C.idUsuario GROUP BY U.idUsuario ORDER BY puntuacion DESC, contestadas ASC, alias ASC");
			while (rs.next() && clasificacion.size() < num) {
				clasificacion.add(new ClasificacionVO(rs.getString("alias"), rs.getLong("contestadas"),
						rs.getLong("puntuacion")));
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return clasificacion;
	}

	/**
	 * Búsqueda de usuario por su identificador interno.
	 * 
	 * @param id
	 * @return El usuario si el id existe, null en caso contrario
	 * @throws ErrorInternoException
	 */
	public UsuarioVO getUsuarioById(Long id) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE idUsuario=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.last()) {
				if (rs.getRow() == 1) {
					return extractUsuarioFromResultSet(rs);
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
	 * Búsqueda de usuario por su alias.
	 * 
	 * @param alias
	 * @return El usuario si el alias existe, null en caso contrario
	 * @throws ErrorInternoException
	 */
	public UsuarioVO getUsuarioByAlias(String alias) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE alias=?");
			stmt.setString(1, alias);
			ResultSet rs = stmt.executeQuery();

			if (rs.last()) {
				if (rs.getRow() == 1) {
					return extractUsuarioFromResultSet(rs);
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
	 * Búsqueda de hasta num usuarios por un string de búsqueda (alias, nombre,
	 * apellidos, email)
	 * 
	 * @param search
	 * @param num
	 * @return Lista de usuarios que cumplen con las condiciones nombradas
	 * @throws ErrorInternoException
	 */
	public List<UsuarioVO> getUsuariosBySearch(String search, int num) throws ErrorInternoException {
		List<UsuarioVO> usuarios = new ArrayList<UsuarioVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Usuario WHERE alias LIKE ? OR nombre LIKE ? OR apellidos LIKE ? OR email LIKE ?");
			stmt.setString(1, "%" + search + "%");
			stmt.setString(2, "%" + search + "%");
			stmt.setString(3, "%" + search + "%");
			stmt.setString(4, "%" + search + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next() && usuarios.size() < num) {
				UsuarioVO usuario = extractUsuarioFromResultSet(rs);
				usuarios.add(usuario);
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return usuarios;
	}

	/**
	 * Búsqueda de hasta num usuarios por un string de búsqueda (alias, nombre,
	 * apellidos, email)
	 * 
	 * @param search
	 * @param num
	 * @return Lista de usuarios que cumplen con las condiciones nombradas
	 * @throws ErrorInternoException
	 */
	public List<UsuarioVO> getUsuariosBySearchByPag(String search, int usuariosPorPagina, int pagina,
			FiltroUsuario filtro) throws ErrorInternoException {
		List<UsuarioVO> usuarios = new ArrayList<UsuarioVO>();
		try {
			Connection connection = ConnectionFactory.getConnection();

			// Apilcar filtros de búsqueda
			String filtroString = "";
			int numFiltros = 0;
			if (filtro.getFiltrarAlias()) {
				filtroString += "alias LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarNombre()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "nombre LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarApellidos()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "apellidos LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarCorreo()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "email LIKE ? ";
				numFiltros++;
			}

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE " + filtroString);
			for (int i = 0; i < numFiltros; i++) {
				stmt.setString(i + 1, "%" + search + "%");
			}
			ResultSet rs = stmt.executeQuery();

			rs.absolute(usuariosPorPagina * (pagina - 1));
			while (rs.next() && usuarios.size() < usuariosPorPagina) {
				UsuarioVO usuario = extractUsuarioFromResultSet(rs);
				usuarios.add(usuario);
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return usuarios;
	}

	/**
	 * Búsqueda de hasta num usuarios por un string de búsqueda (alias, nombre,
	 * apellidos, email)
	 * 
	 * @param search
	 * @param num
	 * @return Lista de usuarios que cumplen con las condiciones nombradas
	 * @throws ErrorInternoException
	 */
	public int getNumUsuariosBySearch(String search, int num, FiltroUsuario filtro) throws ErrorInternoException {
		int numUsuarios;

		try {
			Connection connection = ConnectionFactory.getConnection();

			// Apilcar filtros de búsqueda
			String filtroString = "";
			int numFiltros = 0;
			if (filtro.getFiltrarAlias()) {
				filtroString += "alias LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarNombre()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "nombre LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarApellidos()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "apellidos LIKE ? ";
				numFiltros++;
			}
			if (filtro.getFiltrarCorreo()) {
				if (numFiltros > 0) {
					filtroString += "OR ";
				}
				filtroString += "email LIKE ? ";
				numFiltros++;
			}
			
			PreparedStatement stmt = connection
					.prepareStatement("SELECT COUNT(idUsuario) AS numUsuarios FROM Usuario WHERE " + filtroString);
			for (int i = 0; i < numFiltros; i++) {
				stmt.setString(i + 1, "%" + search + "%");
			}
			
			// Ejecutar y coger los resultados de la página pagina
			ResultSet rs = stmt.executeQuery();

			rs.first();
			numUsuarios = rs.getInt("numUsuarios");
			stmt.close();
			connection.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return numUsuarios;
	}

	/**
	 * Login de usuario por su alias y password.
	 * 
	 * @param alias
	 * @param passwordHash
	 * @return El usuario si los datos de login son correctos, null en caso
	 *         contrario
	 * @throws ErrorInternoException
	 */
	public UsuarioVO getUsuarioByLoginAlias(String alias, String passwordHash) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE alias=?");
			stmt.setString(1, alias);
			ResultSet rs = stmt.executeQuery();

			// Mover el cursor a la ultima posiciún y comprobar que el resultSet solamente
			// ha devuelto un usuario
			// Si ha devuelto 0 no existe un usuario con ese alias, si devuelve mas de 1
			// algo raro ha pasado con el query
			if (rs.last()) {
				if (rs.getRow() == 1) {
					UsuarioVO user = extractUsuarioFromResultSet(rs);
					if (checkLogin(user, passwordHash)) {
						stmt.close();
						connection.close();
						return user;
					}
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
	 * Login de usuario por su email y password.
	 * 
	 * @param email
	 * @param passwordHash
	 * @return El usuario si los datos de login son correctos, null en caso
	 *         contrario
	 * @throws ErrorInternoException
	 */
	public UsuarioVO getUsuarioByLoginEmail(String email, String passwordHash) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE email=?");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			// Mover el cursor a la ultima posición y comprobar que el resultSet solamente
			// ha devuelto un usuario
			// Si ha devuelto 0 no existe un usuario con ese alias, si devuelve mas de 1
			// algo raro ha pasado con el query
			if (rs.last()) {
				if (rs.getRow() == 1) {
					UsuarioVO user = extractUsuarioFromResultSet(rs);
					if (checkLogin(user, passwordHash)) {
						stmt.close();
						connection.close();
						return user;
					}
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
	 * Inserta un usuario en la base de datos.
	 * 
	 * @param usuario
	 * @return El ID del usuario insertado o 0 si la inserción ha sido incorrecta
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException
	 */
	public Long insertUsuario(UsuarioVO usuario) throws UsuarioYaExistenteException, ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			// Comprobar que no exista alguien con ese alias o email ya
			if (checkAliasYEmailExistente(usuario.getAlias(), usuario.getEmail())) {

				PreparedStatement stmt = connection.prepareStatement(
						"INSERT INTO Usuario VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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

				stmt.close();
			}

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return 0L;
	}

	/**
	 * Actualiza los datos de un usuario (asumiendo que ya existe un usuario con ese
	 * ID).
	 * 
	 * @param usuario
	 * @return true si la actualización ha sido correcta, false en caso contrario
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException
	 */
	public boolean updateUsuario(UsuarioVO usuario, boolean cambiaAlias, boolean cambiaEmail)
			throws UsuarioYaExistenteException, ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			// Comprobar que no exista alguien con ese alias o email ya
			if (checkAliasYEmailExistente(cambiaAlias ? usuario.getAlias() : "$",
					cambiaEmail ? usuario.getEmail() : "$")) {

				PreparedStatement stmt = connection.prepareStatement(
						"UPDATE Usuario SET alias=?, nombre=?, apellidos=?, fechaNacimiento=?, email=?, passwordHash=?, tipoUsuario=?, puntuacion=? WHERE idUsuario=?");
				stmt.setString(1, usuario.getAlias());
				stmt.setString(2, usuario.getNombre());
				stmt.setString(3, usuario.getApellidos());
				stmt.setDate(4, usuario.getFechaNacimiento());
				stmt.setString(5, usuario.getEmail());
				stmt.setString(6, usuario.getPasswordHash());
				stmt.setString(7, usuario.getTipoUsuario().toString());
				stmt.setDouble(8, usuario.getPuntuacion());
				stmt.setLong(9, usuario.getIdUsuario());

				int result = stmt.executeUpdate();
				stmt.close();
				connection.close();
				return result == 1;

			}

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return false;
	}

	/**
	 * Elimina a un usuario de la base de datos según su id.
	 * 
	 * @param id
	 * @return true si el borrado ha sido correcto, false en caso contrario
	 * @throws ErrorInternoException
	 */
	public boolean deleteUsuario(Long id) throws ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

			PreparedStatement stmt = connection.prepareStatement("DELETE FROM Usuario WHERE idUsuario=?");
			stmt.setLong(1, id);
			int result = stmt.executeUpdate();

			stmt.close();
			if (result == 1) {
				connection.close();
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
	 * Extrae los datos de un usuario dado un ResultSet.
	 * 
	 * @param rs
	 * @return Datos del usuario de la fila que apunta rs
	 * @throws SQLException
	 */
	private UsuarioVO extractUsuarioFromResultSet(ResultSet rs) throws SQLException {
		UsuarioVO user = new UsuarioVO(rs.getLong("idUsuario"), rs.getString("alias"), rs.getString("nombre"),
				rs.getString("apellidos"), rs.getDate("fechaNacimiento"), rs.getString("email"),
				rs.getString("passwordHash"), UsuarioVO.TipoUsuario.valueOf(rs.getString("tipoUsuario")),
				rs.getDouble("puntuacion"));
		return user;
	}

	/**
	 * Comprueba si los datos de login para el usuario son correctos.
	 * 
	 * @param usuario
	 * @param passwordHash
	 * @return true si las contraseúas coinciden, false en caso contrario
	 */
	private boolean checkLogin(UsuarioVO usuario, String passwordHash) {
		return usuario.getPasswordHash().equals(passwordHash);
	}

	/**
	 * Comprueba si ya existe un usuario en la base de datos con el alias o email
	 * pasados, y lanza un error en caso afirmativo.
	 * 
	 * @param alias
	 * @param email
	 * @return true si no existe ningún usuario con ese alias o email
	 * @throws UsuarioYaExistenteException
	 * @throws ErrorInternoException
	 */
	private boolean checkAliasYEmailExistente(String alias, String email)
			throws UsuarioYaExistenteException, ErrorInternoException {
		try {
			Connection connection = ConnectionFactory.getConnection();

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

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new ErrorInternoException();
		}
		return true;
	}

}
