package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class UsuarioVO implements java.io.Serializable {
	
	public enum TipoUsuario { ADMINISTRADOR, CREADOR, PARTICIPANTE };

	public static final int ALIAS_MIN = 3;
	public static final int ALIAS_MAX = 20;
	public static final int NOMBRE_MAX = 50;
	public static final int APELLIDOS_MAX = 100;
	public static final int EMAIL_MAX = 254;
	
	private Long idUsuario;
	private String alias;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String email;
	private String passwordHash;
	private TipoUsuario tipoUsuario;
	private Double puntuacion;
	
	public UsuarioVO(String alias, String nombre, String apellidos, Date fechaNacimiento, String email,
			String passwordHash, TipoUsuario tipoUsuario, Double puntuacion) {
		this.alias = alias;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.passwordHash = passwordHash;
		this.tipoUsuario = tipoUsuario;
		this.puntuacion = puntuacion;
	}
	
	public UsuarioVO(Long idUsuario, String alias, String nombre, String apellidos, Date fechaNacimiento, String email,
			String passwordHash, TipoUsuario tipoUsuario, Double puntuacion) {
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
	
	/* Constructor especial para editar usuario */
	public UsuarioVO(UsuarioVO original, String alias, String nombre, String apellidos, Date fechaNacimiento, String email) {
		this.idUsuario = original.getIdUsuario();
		this.alias = alias;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.passwordHash = original.getPasswordHash();
		this.tipoUsuario = original.getTipoUsuario();
		this.puntuacion = original.getPuntuacion();
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
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
	public Double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(Double puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
