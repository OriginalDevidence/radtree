package sistinfo.capamodelo.resultados;

@SuppressWarnings("serial")
public class ResultadoRegistro implements java.io.Serializable {
	
	private String errorAlias;
	private String errorNacimiento;
	private String errorNombre;
	private String errorApellidos;
	private String errorEmail;
	private String errorClave;
	private String errorReclave;
	
	public ResultadoRegistro() {
		this.errorAlias = "";
		this.errorNacimiento = "";
		this.errorNombre = "";
		this.errorApellidos = "";
		this.errorEmail = "";
		this.errorClave = "";
		this.errorReclave = "";
	}
	
	public ResultadoRegistro(String errorAlias, String errorNacimiento, String errorNombre, String errorApellidos,
			String errorEmail, String errorClave, String errorReclave) {
		super();
		this.errorAlias = errorAlias;
		this.errorNacimiento = errorNacimiento;
		this.errorNombre = errorNombre;
		this.errorApellidos = errorApellidos;
		this.errorEmail = errorEmail;
		this.errorClave = errorClave;
		this.errorReclave = errorReclave;
	}

	public String getErrorAlias() {
		return errorAlias;
	}
	public void setErrorAlias(String errorAlias) {
		this.errorAlias = errorAlias;
	}
	public String getErrorNacimiento() {
		return errorNacimiento;
	}
	public void setErrorNacimiento(String errorNacimiento) {
		this.errorNacimiento = errorNacimiento;
	}
	public String getErrorNombre() {
		return errorNombre;
	}
	public void setErrorNombre(String errorNombre) {
		this.errorNombre = errorNombre;
	}
	public String getErrorApellidos() {
		return errorApellidos;
	}
	public void setErrorApellidos(String errorApellidos) {
		this.errorApellidos = errorApellidos;
	}
	public String getErrorEmail() {
		return errorEmail;
	}
	public void setErrorEmail(String errorEmail) {
		this.errorEmail = errorEmail;
	}
	public String getErrorClave() {
		return errorClave;
	}
	public void setErrorClave(String errorClave) {
		this.errorClave = errorClave;
	}
	public String getErrorReclave() {
		return errorReclave;
	}
	public void setErrorReclave(String errorReclave) {
		this.errorReclave = errorReclave;
	}
	
}
