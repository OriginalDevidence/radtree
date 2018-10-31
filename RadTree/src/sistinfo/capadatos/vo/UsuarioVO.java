package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class UsuarioVO implements java.io.Serializable {
	
	/* TODO: restringir los posibles caracteres de alias y asegurar que el email tiene un @ al menos */
	
	public enum TipoUsuario { ADMINISTRADOR, CREADOR, PARTICIPANTE };
	
	private long idUsuario;
	private String alias;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String email;
	private String passwordHash;
	private TipoUsuario tipoUsuario;
	private double puntuacion;
	
	public UsuarioVO(String alias, String nombre, String apellidos, Date fechaNacimiento, String email,
			String passwordHash, TipoUsuario tipoUsuario) {
		this.alias = alias;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.passwordHash = passwordHash;
		this.tipoUsuario = tipoUsuario;
	}
	
	public UsuarioVO(String alias, String nombre, String apellidos, Date fechaNacimiento, String email,
			String passwordHash, TipoUsuario tipoUsuario, double puntuacion) {
		this.alias = alias;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.passwordHash = passwordHash;
		this.tipoUsuario = tipoUsuario;
		this.puntuacion = puntuacion;
	}
	
	public UsuarioVO(long idUsuario, String alias, String nombre, String apellidos, Date fechaNacimiento, String email,
			String passwordHash, TipoUsuario tipoUsuario, double puntuacion) {
		this.idUsuario = idUsuario;
		this.alias = alias;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.passwordHash = passwordHash;
		this.tipoUsuario = tipoUsuario;
		this.puntuacion = puntuacion;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
