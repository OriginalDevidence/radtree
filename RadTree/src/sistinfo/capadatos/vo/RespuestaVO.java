package sistinfo.capadatos.vo;

@SuppressWarnings("serial")
public class RespuestaVO implements java.io.Serializable {
	
	private long idRespuesta;	
	private long idPregunta;
	private String enunciado;
	private boolean correcta;
	
	public RespuestaVO(long idPregunta, String enunciado, boolean correcta) {
		this.idPregunta = idPregunta;
		this.enunciado = enunciado;
		this.correcta = correcta;
	}
	
	public RespuestaVO(long idRespuesta, long idPregunta, String enunciado, boolean correcta) {
		this.idRespuesta = idRespuesta;
		this.idPregunta = idPregunta;
		this.enunciado = enunciado;
		this.correcta = correcta;
	}
	
	public long getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(long idRespuesta) {
		this.idRespuesta = idRespuesta;
	}
	public long getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(long idPregunta) {
		this.idPregunta = idPregunta;
	}
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public boolean isCorrecta() {
		return correcta;
	}
	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
	}
	
}
