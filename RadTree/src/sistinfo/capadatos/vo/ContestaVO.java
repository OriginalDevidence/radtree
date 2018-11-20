package sistinfo.capadatos.vo;

@SuppressWarnings("serial")
public class ContestaVO implements java.io.Serializable{
	
	private Long idUsuario;	
	private Long idRespuesta;
	private Boolean respuesta;
	
	public ContestaVO(Long idUsuario, Long idRespuesta, Boolean respuesta) {
		this.idUsuario = idUsuario;
		this.idRespuesta = idRespuesta;
		this.respuesta = respuesta;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdRespuesta() {
		return idRespuesta;
	}

	public void setIdRespuesta(Long idRespuesta) {
		this.idRespuesta = idRespuesta;
	}

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}

}
