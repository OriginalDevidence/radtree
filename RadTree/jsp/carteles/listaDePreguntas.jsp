<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Preguntas - RadTree</title>
	<meta name="description" content="Listado de todas las preguntas">
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
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Preguntas</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
                <!-- Barra de búsqueda -->
				<div class="col-12 col-md-8 offset-0 offset-md-2 mb-50">
                	<form name="busqueda" action="${pageContext.request.contextPath}/preguntas" method="post">
						<div class="row form-block form-plr-15 form-h-45 form-brdr-lite-white">
							
							<c:if test="${empty param.busqueda}">
								<div class="col-9 col-md-10">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."/>
								</div>
							</c:if>
							<c:if test="${not empty param.busqueda}">
								<div class="col-2">
									<button class="w-100 btn-fill-primary" name="busqueda" value="" type="submit">
										<i class="ion-close"></i>
									</button>
								</div>
								<div class="col-7 col-md-8">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."
										value="<c:out value="${param.busqueda}"/>">
								</div>
							</c:if>
							
							<div class="col-3 col-md-2">
								<button class="w-100 btn-fill-primary" type="submit">
									<i class="ion-search"></i>
								</button>
							</div>
						</div>
						<c:if test="${not empty sessionScope.usuario}">
							<div class="mt-20 row">
								<div class="col-12 col-lg-8 offset-0 offset-lg-2 float-right">
		                			<c:if test="${param.noContestadas=='on'}">
		                				<input type="checkbox" class="mt-5 mr-10" name="noContestadas" checked/>
		                			</c:if>
		                			<c:if test="${param.noContestadas!='on'}">
		                				<input type="checkbox" class="mt-5 mr-10" name="noContestadas"/>
		                			</c:if>
		                			<label for="noContestadas"><i>Mostrar solo preguntas no contestadas</i></label>
	                			</div>
	                		</div>
                		</c:if>
					</form>
                </div>
				<div class="col-12">
					<c:if test="${not empty requestScope.preguntas}">
                    	<ul class="lista-titulos">
							<c:forEach items="${requestScope.preguntas}" var="pregunta">
		                        <li><form name="reto" action="${pageContext.request.contextPath}/preguntas/ver" method="post">
		                        	<input type="hidden" name="id" value="<c:out value="${pregunta.idContenido}"/>"/>
			                        <button type="submit">
			                            <span><c:out value="${pregunta.enunciado}"/></span><br>
			                            <span class="titulo-desc">
			                                <i class="ml-10 mr-5 color-primary ion-ios-bolt"></i>
			                                <c:out value="${pregunta.numVisitas}"/>
			                                <c:if test="${pregunta.numVisitas == 1}">
			                                	 visita
			                               	</c:if>
			                               	<c:if test="${pregunta.numVisitas != 1}">
			                                	 visitas
			                               	</c:if>
			                                <i class="ml-10 mr-5 color-primary ion-ios-lightbulb"></i>
			                                <c:out value="${pregunta.vecesContestada}"/>
			                                <c:if test="${pregunta.vecesContestada == 1}">
			                                	 vez respondida
			                               	</c:if>
			                               	<c:if test="${pregunta.vecesContestada != 1}">
			                                	 veces respondida
			                               	</c:if>
			                                <i class="ml-10 mr-5 color-primary ion-chatbubbles"></i>
			                                <c:out value="${pregunta.numComentarios}"/>
			                                <c:if test="${pregunta.numComentarios == 1}">
			                                	 comentario
			                               	</c:if>
			                               	<c:if test="${pregunta.numComentarios != 1}">
			                                	 comentarios
			                               	</c:if>
			                            </span>
			                        </button>
		                        </form></li>
							</c:forEach>
	                    </ul>
                   	</c:if>
					<c:if test="${empty requestScope.preguntas}">
						<p><i>Parece que no hay nada por aquí...</i></p>
					</c:if>
                </div>
            </div>

		</div><!-- container -->

		<%-- Paginacion --%>
		<div class="text-center">
			<form name="paginacion"
				action="${pageContext.request.contextPath}/preguntas" method="post">
				<%@ include file="/jsp/include/paginacion.jsp"%>
			</form>
		</div>

	</section>

	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
