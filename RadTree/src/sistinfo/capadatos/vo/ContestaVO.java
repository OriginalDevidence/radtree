package sistinfo.capadatos.vo;

@SuppressWarnings("serial")
public class ContestaVO implements java.io.Serializable{
	
	private long idUsuario;	
	private long idRespuesta;
	private boolean respuesta;
	
	public ContestaVO(long idUsuario, long idRespuesta, boolean respuesta) {
		this.idUsuario = idUsuario;
		this.idRespuesta = idRespuesta;
		this.respuesta = respuesta;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdRespuesta() {
		return idRespuesta;
	}

	public void setIdRespuesta(long idRespuesta) {
		this.idRespuesta = idRespuesta;
	}

	public boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

}
