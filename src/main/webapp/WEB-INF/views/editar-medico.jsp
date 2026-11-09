<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar médico</title>
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
  <h1>Editar médico</h1>

  <spring:hasBindErrors name="medico">
    <ul class="error">
      <c:forEach var="e" items="${errors.allErrors}"><li><spring:message message="${e}"/></li></c:forEach>
    </ul>
  </spring:hasBindErrors>

  <form action="${pageContext.request.contextPath}/medicos/actualizar/${medicoId}" method="post">
    <label>Nombre (máximo 40 caracteres)</label>
    <input type="text" name="nombre" maxlength="40" value="${fn:escapeXml(medico.nombre)}" required />

    <label>Especialidad (máximo 45 caracteres)</label>
    <input type="text" name="especialidad" maxlength="45" value="${fn:escapeXml(medico.especialidad)}" required />

    <br/><br/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="submit" value="Actualizar" style="width:auto" />
  </form>
  <p><a href="${pageContext.request.contextPath}/medicos">Volver a la lista</a></p>
</body>
</html>
