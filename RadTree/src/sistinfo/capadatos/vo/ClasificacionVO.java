package sistinfo.capadatos.vo;

@SuppressWarnings("serial")
public class ClasificacionVO implements java.io.Serializable{
	
	private String alias;	
	private Long preguntasContestadas;
	private Long puntuacion;

	public ClasificacionVO(String alias, Long preguntasContestadas, Long puntuacion) {
		this.alias = alias;
		this.preguntasContestadas = preguntasContestadas;
		this.puntuacion = puntuacion;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getPreguntasContestadas() {
		return preguntasContestadas;
	}

	public void setPreguntasContestadas(Long preguntasContestadas) {
		this.preguntasContestadas = preguntasContestadas;
	}

	public Long getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Long puntuacion) {
		this.puntuacion = puntuacion;
	}
	
}
