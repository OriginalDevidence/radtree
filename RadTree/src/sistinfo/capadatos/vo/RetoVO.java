package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class RetoVO extends ContenidoVO  {
	
	private String titulo;
	private String cuerpo;

	public RetoVO(long idAutor, long numVisitas, Date fechaRealizacion, Estado estado,
			String titulo, String cuerpo) {
		super(idAutor, numVisitas, fechaRealizacion, estado);
		this.titulo = titulo;
		this.cuerpo = cuerpo;
	}
	
	public RetoVO(long idContenido, long idAutor, long numVisitas, Date fechaRealizacion, Estado estado,
			String titulo, String cuerpo) {
		super(idContenido, idAutor, numVisitas, fechaRealizacion, estado);
		this.titulo = titulo;
		this.cuerpo = cuerpo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

}
