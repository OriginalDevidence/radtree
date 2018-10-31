package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class PreguntaVO extends ContenidoVO {
	
	private String enunciado;
	
	public PreguntaVO(long idAutor, long numVisitas, Date fechaRealizacion, Estado estado,
			String enunciado) {
		super(idAutor, numVisitas, fechaRealizacion, estado);

		this.enunciado = enunciado;
	}
	
	public PreguntaVO(long idContenido, long idAutor, long numVisitas, Date fechaRealizacion, Estado estado,
			String enunciado) {
		super(idContenido, idAutor, numVisitas, fechaRealizacion, estado);

		this.enunciado = enunciado;
	}
	
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
}
