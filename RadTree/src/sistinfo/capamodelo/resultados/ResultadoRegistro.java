package sistinfo.capamodelo.resultados;

@SuppressWarnings("serial")
public class ResultadoRegistro implements java.io.Serializable {
	
	private String errorAlias;
	private String valorAlias;
	private String errorNacimiento;
	private String valorNacimiento;
	private String errorNombre;
	private String valorNombre;
	private String errorApellidos;
	private String valorApellidos;
	private String errorEmail;
	private String valorEmail;
	private String errorClave;
	private String valorClave;
	private String errorReclave;
	private String valorReclave;
	
	public ResultadoRegistro() {
		this.errorAlias = "";
		this.valorAlias = "";
		this.errorNacimiento = "";
		this.valorNacimiento = "";
		this.errorNombre = "";
		this.valorNombre = "";
		this.errorApellidos = "";
		this.valorApellidos = "";
		this.errorEmail = "";
		this.valorEmail = "";
		this.errorClave = "";
		this.valorClave = "";
		this.errorReclave = "";
		this.valorReclave = "";
	}

	public ResultadoRegistro(String errorAlias, String valorAlias, String errorNacimiento, String valorNacimiento,
			String errorNombre, String valorNombre, String errorApellidos, String valorApellidos, String errorEmail,
			String valorEmail, String errorClave, String valorClave, String errorReclave, String valorReclave) {
		this.errorAlias = errorAlias;
		this.valorAlias = valorAlias == null ? "" : valorAlias;
		this.errorNacimiento = errorNacimiento;
		this.valorNacimiento = valorNacimiento == null ? "" : valorNacimiento;
		this.errorNombre = errorNombre;
		this.valorNombre = valorNombre == null ? "" : valorNombre;
		this.errorApellidos = errorApellidos;
		this.valorApellidos = valorApellidos == null ? "" : valorApellidos;
		this.errorEmail = errorEmail;
		this.valorEmail = valorEmail == null ? "" : valorEmail;
		this.errorClave = errorClave;
		this.valorClave = valorClave == null ? "" : valorClave;
		this.errorReclave = errorReclave;
		this.valorReclave = valorReclave == null ? "" : valorReclave;
	}

	public String getErrorAlias() {
		return errorAlias;
	}

	public void setErrorAlias(String errorAlias) {
		this.errorAlias = errorAlias;
	}

	public String getValorAlias() {
		return valorAlias;
	}

	public void setValorAlias(String valorAlias) {
		this.valorAlias = valorAlias;
	}

	public String getErrorNacimiento() {
		return errorNacimiento;
	}

	public void setErrorNacimiento(String errorNacimiento) {
		this.errorNacimiento = errorNacimiento;
	}

	public String getValorNacimiento() {
		return valorNacimiento;
	}

	public void setValorNacimiento(String valorNacimiento) {
		this.valorNacimiento = valorNacimiento;
	}

	public String getErrorNombre() {
		return errorNombre;
	}

	public void setErrorNombre(String errorNombre) {
		this.errorNombre = errorNombre;
	}

	public String getValorNombre() {
		return valorNombre;
	}

	public void setValorNombre(String valorNombre) {
		this.valorNombre = valorNombre;
	}

	public String getErrorApellidos() {
		return errorApellidos;
	}

	public void setErrorApellidos(String errorApellidos) {
		this.errorApellidos = errorApellidos;
	}

	public String getValorApellidos() {
		return valorApellidos;
	}

	public void setValorApellidos(String valorApellidos) {
		this.valorApellidos = valorApellidos;
	}

	public String getErrorEmail() {
		return errorEmail;
	}

	public void setErrorEmail(String errorEmail) {
		this.errorEmail = errorEmail;
	}

	public String getValorEmail() {
		return valorEmail;
	}

	public void setValorEmail(String valorEmail) {
		this.valorEmail = valorEmail;
	}

	public String getErrorClave() {
		return errorClave;
	}

	public void setErrorClave(String errorClave) {
		this.errorClave = errorClave;
	}

	public String getValorClave() {
		return valorClave;
	}

	public void setValorClave(String valorClave) {
		this.valorClave = valorClave;
	}

	public String getErrorReclave() {
		return errorReclave;
	}

	public void setErrorReclave(String errorReclave) {
		this.errorReclave = errorReclave;
	}

	public String getValorReclave() {
		return valorReclave;
	}

	public void setValorReclave(String valorReclave) {
		this.valorReclave = valorReclave;
	}
	
}
