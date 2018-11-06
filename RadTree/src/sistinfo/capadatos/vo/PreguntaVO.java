package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class PreguntaVO extends ContenidoVO {
	
	private String enunciado;
	
	public PreguntaVO(Long idAutor, Date fechaRealizacion, String enunciado) {
		super(idAutor, 0L, fechaRealizacion, Estado.PENDIENTE);

		this.enunciado = enunciado;
	}
	
	public PreguntaVO(Long idContenido, Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado,
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
