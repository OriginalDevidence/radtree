<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.reto.titulo}"/> - RadTree</title>
	<meta name="description" content="Uno de los retos planteados por los usuarios de RadTree">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
	<link rel="icon" type="image/jpg" href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
</head>
<body>

	<%@ include file="/jsp/include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}/"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="${pageContext.request.contextPath}/retos">Retos<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#"><c:out value="${requestScope.reto.titulo}"/></a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<c:if test="${requestScope.reto == null or requestScope.reto.estado != 'VALIDADO'}">
				<c:redirect url="${pageContext.request.contextPath}/retos"/>
			</c:if>

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b><c:out value="${requestScope.reto.titulo}"/></b></h3>

                    <p class="text-justify mb-20">
                        <c:out value="${requestScope.reto.cuerpo}"/>
                    </p>

                    <form name="perfilAutor" action="${pageContext.request.contextPath}/perfil" method="post">
                    	<input type="hidden" name="alias" value="<c:out value='${requestScope.autorAlias}'/>"/>
                    	<p><b>Autor: </b>
	                   		<button class="link-brdr-btm-primary color-primary" type="submit"><c:out value='${requestScope.autorCompleto}'/></button>
						</p>
                    </form>
                </div>
			
				<c:if test="${sessionScope.usuario.tipoUsuario == 'ADMINISTRADOR'}">
					<div class="col-md-12 col-lg-4">
						<form name="borrarContenido" action="${pageContext.request.contextPath}/retos/borrar" method="post">
							<input type="hidden" name="id" value="<c:out value="${requestScope.reto.idContenido}"/>"/>
							<input type="hidden" name="redirect" value="retos"/>
							<button class="mt-md-30 w-100 btn-brdr-red" type="submit"><i class="ion-trash-b mr-10"></i><b>Borrar contenido</b></button>
						</form>
					</div>
				</c:if>
			</div>

			<script src="${pageContext.request.contextPath}/common/copyToClipboard.js"></script>
			<div class="col-12">
				<input class="hidden-input" type="text"
					value="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}/retos/ver?id=<c:out value="${requestScope.reto.idContenido}"/>" id="copy">
				<p class="mb-10"><b>Link para compartir:</b></p>
				<a class="share-link" href="#" onclick="copyToClipboard('copy')">
					<i class="ion-ios-copy-outline"></i>
					<span class="link" id="copy">${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}/retos/ver?id=<c:out value="${requestScope.reto.idContenido}"/></span>
				</a>
			</div>
		
		</div><!-- container -->
		
		<%@ include file="/jsp/include/comentarios.jsp" %>
		
	</section>
	
	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
