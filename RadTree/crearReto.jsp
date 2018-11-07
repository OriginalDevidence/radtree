<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO.TipoUsuario" %>
<%--
	Comprueba los errores que se han podido obtener al crear noticia y les añade formato
	SuppressWarnings para evitar el warning de type cast de "errores" (aunque esta bien hecho)
--%>
<%! @SuppressWarnings("unchecked") %>
<%
	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
	if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
		// El usuario no es un creador de contenido o admin, no deberia estar en esta página
		response.sendRedirect("errorInterno.html");
	} else if (request.getAttribute("errores") instanceof HashMap) {
		// Mostrar errores
		Map<String, String> errores = (HashMap<String, String>)request.getAttribute("errores");
		String estiloCabecera = "<i class=\"ml-10 ion-close color-red\"></i><span class=\"pl-5 font-10 color-red\">";
		String estiloFinal = "</span>";
		// Añadir formato
		for (String k : errores.keySet()) {
			errores.replace(k, estiloCabecera + errores.get(k) + estiloFinal);
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Crear reto - RadTree</title>
	<meta name="description" content="Página de perfil de usuario">
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
			<a class="mt-10" href="gestionarContenido.jsp">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Crear reto</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30">
						<b>Crear reto</b>
					</h3>
					<form name="crearReto" action="CrearReto.do" method="post">

						<div
							class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-8">
								<label for="titulo">Título del reto</label>
								<c:out value="${requestScope.errores.get('titulo')}"
									escapeXml="false" />
								<input type="text" name="titulo" placeholder="Título"
									value="<c:out value="${param.titulo}"/>" />
							</div>

							<div class="col-sm-12">
								<label for="cuerpo">Planteamiento del reto</label>
								<c:out value="${requestScope.errores.get('cuerpo')}"
									escapeXml="false" />
								<textarea class="p-10" name="cuerpo" rows=4><c:out value="${param.cuerpo}"/></textarea>
							</div>

						</div>

						<div class="row">
							<div class="col-sm-12 mt-30">
								<p class="mb-20"><i>
									Recuerda que todos los contenidos deben ser aprobados previamente por un administrador antes de ser visibles para el público.
									Este proceso puede tomar un tiempo, así que ten paciencia.
								</i></p>
								<button class="w-100 btn-fill-primary" type="submit">
									<b>Enviar reto</b>
								</button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>
		<!-- container -->
	</section>

	<%@ include file="WEB-INF/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>
