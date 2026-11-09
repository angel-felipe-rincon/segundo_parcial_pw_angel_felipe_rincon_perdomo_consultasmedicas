<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Médicos</title>
<style>
body { font-family: Arial, sans-serif; margin: 40px; }
table { border-collapse: collapse; }
th, td { border: 1px solid #999; padding: 6px 12px; }
th { background: #eee; }
label { display: block; margin-top: 10px; }
input, select, textarea { padding: 6px; width: 260px; }
.error { color: #b71c1c; }
.nota { color: #555; font-size: 13px; }
a { margin-right: 12px; }
</style>
<script>
window.addEventListener('pageshow', function (e) {
  var nav = performance.getEntriesByType('navigation')[0];
  if (e.persisted || (nav && nav.type === 'back_forward')) { window.location.reload(); }
});
</script>
</head>
<body>
  <h1>Médicos</h1>

  <p>
    <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
    <a href="${pageContext.request.contextPath}/medicos/nuevo">Registrar médico</a>
  </p>

  <c:if test="${not empty mensajeError}">
    <p class="error"><c:out value="${mensajeError}"/></p>
  </c:if>

  <table>
    <tr>
      <th>ID</th>
      <th>Nombre</th>
      <th>Especialidad</th>
      <th>Acciones</th>
    </tr>
    <c:forEach var="m" items="${medicos}">
      <tr>
        <td>${m.id}</td>
        <td><c:out value="${m.nombre}"/></td>
        <td><c:out value="${m.especialidad}"/></td>
        <td>
          <a href="${pageContext.request.contextPath}/medicos/editar/${m.id}">Editar</a>
          <form action="${pageContext.request.contextPath}/medicos/eliminar/${m.id}" method="post" style="display:inline"
                onsubmit="return confirm('¿Eliminar este médico?');">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit">Eliminar</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

  <c:if test="${empty medicos}">
    <p>Todavía no hay médicos registrados.</p>
  </c:if>
</body>
</html>
