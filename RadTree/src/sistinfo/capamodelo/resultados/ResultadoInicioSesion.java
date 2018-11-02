package sistinfo.capamodelo.resultados;

public class ResultadoInicioSesion {

	public String error;
	
	public ResultadoInicioSesion() {
		this.error = "";
	}

	public ResultadoInicioSesion(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
