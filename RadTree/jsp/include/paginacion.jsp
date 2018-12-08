<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="text-center">

	<form name="paginacion"
		action="${pageContext.request.contextPath}/noticias" method="post">
		<input type="hidden" name="currentPage" value="${currentPage}" />
		<input type="hidden" name="noOfPages" value="${noOfPages}" />
		<input type="hidden" name="busqueda" value="${param.busqueda}" />
		<%-- Añadir todos los posibles tipos de búsqueda para seguir filtrando al buscar --%>
		<input type="hidden" name="filtroTitulo" value="<c:out value="${param.filtroTitulo}"/>"/>
		<input type="hidden" name="filtroCuerpo" value="<c:out value="${param.filtroCuerpo}"/>"/>
		<input type="hidden" name="filtroUrl" value="<c:out value="${param.filtroUrl}"/>"/>

		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${requestScope.currentPage > 1}">
			<button class="col-fit col-md-fit btn-fill-primary" type="submit"
				name="button" value="primeraPagina">
				<i class="ion-chevron-left"></i><i class="ion-chevron-left"></i>
			</button>
			<button class="col-fit col-md-fit btn-fill-primary" type="submit"
				name="button" value="anteriorPagina">
				<i class="ion-chevron-left"></i>
			</button>
		</c:if>

		<%--For displaying Page numbers. 
	    The when condition does not display a link for the current page--%>
		<c:set var="maxPagNum" value="${10}"></c:set>
		<c:set var="startPagNum" value="${1}"></c:set>

		<c:if test="${currentPage - 4 > 1}">
			<c:set var="startPagNum" value="${currentPage - 4}"></c:set>
			<c:set var="maxPagNum" value="${maxPagNum + currentPage - 4}"></c:set>
		</c:if>

		<c:if test="${maxPagNum > noOfPages}">
			<c:set var="maxPagNum" value="${noOfPages}"></c:set>
		</c:if>

		<%-- Lista de páginas 1..n con la actual seleccionada --%>
		<c:forEach begin="${startPagNum}" end="${maxPagNum}" var="i">
			<c:choose>
				<c:when test="${currentPage eq i}">
					<span class="text alert-wo-margin alert-success"><b>${i}</b></span>
				</c:when>
				<c:otherwise>
					<button class="col-fit col-md-fit btn-fill-primary" type="submit"
						name="irPagina" value="${i}">
						<b>${i}</b>
					</button>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<%-- For displaying Next link --%>
		<c:if test="${currentPage < noOfPages}">

			<button class="col-fit col-md-fit btn-fill-primary" type="submit"
				name="button" value="siguientePagina">
				<i class="ion-chevron-right"></i>
			</button>
			<button class="col-fit col-md-fit btn-fill-primary" type="submit"
				name="button" value="ultimaPagina">
				<i class="ion-chevron-right"></i> <i class="ion-chevron-right"></i>
			</button>

		</c:if>

	</form>
</div>