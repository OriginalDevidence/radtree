<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.pregunta.enunciado}"/> - RadTree</title>
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

	<%@ include file="WEB-INF/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="${pageContext.request.contextPath}/preguntas">Preguntas<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href=""><c:out value="${requestScope.pregunta.enunciado}"/></a>
		</div><!-- container -->
	</section>


	<section>
		<div class="container">
			
			<c:if test="${requestScope.pregunta == null or requestScope.pregunta.estado != 'VALIDADO'}">
				<c:redirect url="/preguntas"/>
			</c:if>
			
			<div class="row">
				<div class="col-md-12">
					<h3 class="mb-10"><b><c:out value="${requestScope.pregunta.enunciado}"/></b></h3>
					<p class="mb-30"><i><c:out value="${requestScope.pregunta.enunciado}"/></i></p>

					<form class="pregunta" name="enviarPregunta" action="TODO.do" method="post">
					
						<c:forEach items="${requestScope.respuestas}" var="respuesta">
							<div class="row mt-10">
								<div class="col-2 col-sm-1">
									<input type="radio" name="correcta"/>
								</div>
	
								<div class="col-10 col-sm-11">
									<p><c:out value="${respuesta.enunciado}"/></p>
								</div>
							</div>
						</c:forEach>
						
					</form>
				</div>
            </div>
            
			<div class="row mt-20 mt-sm-40">
				<div class="col-12 col-md-4">
					<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-primary" type="submit"><b>Validar pregunta</b></button>
				</div>
				<div class="col-12 col-md-4">
					<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-grey" type="submit"><b>No responder y pasar</b></button>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
				</div>
			</div>

		</div><!-- container -->


		<!-- COMENTARIOS -->
		<%@ include file="WEB-INF/comentarios.jsp" %>

	</section>


	<%@ include file="WEB-INF/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>
