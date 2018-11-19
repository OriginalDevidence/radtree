package sistinfo.capadatos.vo;

@SuppressWarnings("serial")
public class RespuestaVO implements java.io.Serializable {
	
	private Long idRespuesta;	
	private Long idPregunta;
	private String enunciado;
	private Boolean correcta;
	
	public RespuestaVO(Long idPregunta, String enunciado, Boolean correcta) {
		this.idPregunta = idPregunta;
		this.enunciado = enunciado;
		this.correcta = correcta;
	}
	
	public RespuestaVO(Long idRespuesta, Long idPregunta, String enunciado, Boolean correcta) {
		this.idRespuesta = idRespuesta;
		this.idPregunta = idPregunta;
		this.enunciado = enunciado;
		this.correcta = correcta;
	}
	
	public Long getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(Long idRespuesta) {
		this.idRespuesta = idRespuesta;
	}
	public Long getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(Long idPregunta) {
		this.idPregunta = idPregunta;
	}
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public Boolean getCorrecta() {
		return correcta;
	}
	public void setCorrecta(Boolean correcta) {
		this.correcta = correcta;
	}
	
}
