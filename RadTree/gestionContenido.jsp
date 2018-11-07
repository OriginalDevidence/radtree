<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sistinfo.capadatos.dao.ContenidoDAO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO.TipoUsuario" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%--
	Comprobar que hay un usuario logueado y que es creador o administrador
--%>
<%
	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
	if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
		response.sendRedirect("errorInterno.html");
	} else {
		ContenidoDAO contenidoDAO = new ContenidoDAO();
		try {
			int numValidacion = contenidoDAO.getNumContenidosInColaValidacion();
			request.setAttribute("numInValidacion", numValidacion);
		} catch (ErrorInternoException e) {
            response.sendRedirect("errorInterno.html");
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Gestionar contenido - RadTree</title>
	<meta name="description" content="Menú para la gestión del contenido del sistema">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">
	
	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">
	
	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
</head>
<body>
	
	<%@ include file="WEB-INF/header.jsp" %>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.jsp"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Gestionar contenido</a>
		</div><!-- container -->
	</section>
	
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<!-- TODO funcionalidad, poner 3 botones (noticias/preguntas/reto) que generen listas -->
					<h3 class="p-title mb-30"><b>Mis carteles</b></h3>
					<ul>
						<li class="w-100 mb-30">
							<ul>
								<li><h4 class="mr-15"><b>Noticia: *titulo*</b></h4></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="51_noticia.html"><i class="mr-5 ion-eye"></i>Ver</a></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="43_editarNoticia.html"><i class="mr-5 ion-edit"></i>Editar</a></li>
							</ul>
							<p class="mt-10">*descripcion de la noticia*</p>
						</li>
						<li class="w-100 mb-30">
							<ul>
								<li><h4 class="mr-15"><b>Pregunta: *titulo*</b></h4></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="53_pregunta.html"><i class="mr-5 ion-eye"></i>Ver</a></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="44_editarPregunta.html"><i class="mr-5 ion-edit"></i>Editar</a></li>
							</ul>
							<p class="mt-10">*respuesta de la pregunta*</p>
						</li>
						<li class="w-100 mb-30">
							<ul>
								<li><h4 class="mr-15"><b>Reto: *titulo*</b></h4></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="55_reto.html"><i class="mr-5 ion-eye"></i>Ver</a></li>
								<li><a class="plr-10 btn-b-sm btn-fill-primary" href="45_editarReto.html"><i class="mr-5 ion-edit"></i>Editar</a></li>
							</ul>
							<p class="mt-10">*planteamiento del reto*</p>
						</li>
					</ul>
					
				</div>

				<div class="col-md-6 col-lg-4">
					<c:if test="${sessionScope.usuario.tipoUsuario == 'ADMINISTRADOR'}">
						<h3 class="p-title mb-30"><b>Validar contenido</b></h3>
						<p class="mb-25">Actualmente hay <b><c:out value="${requestScope.numInValidacion}"/></b> elementos en la cola de validación.</p>
						<a class="w-100 mb-40 btn-fill-primary" href="31_colaValidacion.html"><b>Ver cola de validación</b></a>
					</c:if>
					<h3 class="p-title mb-30"><b>Gestionar contenido</b></h3>
					<p class="mb-25">Todos los contenidos subidos han de ser aprobados previamente por un administrador para poder ser mostrados en la web.</p>
					<a class="w-100 mb-15 btn-fill-primary" href="crearNoticia.jsp"><b>Crear noticia</b></a>
					<a class="w-100 mb-15 btn-fill-primary" href="crearPregunta.jsp"><b>Crear pregunta</b></a>
					<a class="w-100 mb-15 btn-fill-primary" href="crearReto.jsp"><b>Crear reto</b></a>
				</div>
			
			</div>

		</div><!-- container -->
	</section>
	
	<%@ include file="WEB-INF/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>