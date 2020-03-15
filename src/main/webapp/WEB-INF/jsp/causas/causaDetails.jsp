<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causas">

    <h2>Causa Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Fecha Inicio</th>
            <td><b><c:out value="${causa.fechaInicio}"/></b></td>
        </tr>
        <tr>
            <th>Fecha Fin</th>
            <td><c:out value="${causa.fechaFin}"/></td>
        </tr>
        <tr>
            <th>ONG</th>
            <td><c:out value="${causa.ong}"/></td>
        </tr>
        <tr>
            <th>Objetivo</th>
            <td><c:out value="${causa.objetivo}"/></td>
        </tr>
        <tr>
            <th>Dinero Recaudado</th>
            <td><c:out value="${causa.dineroRecaudado}"/></td>
        </tr>

    </table>
    
        <spring:url value="/donacion/{causaId}/new" var="causaUrl">
        	<spring:param name="causaId" value="${causa.id}"/>
   		</spring:url>
   		<a href="${fn:escapeXml(causaUrl)}" class="btn btn-default">Hacer Donacion</a>
    	
</petclinic:layout>