<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar consulta</title>
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
  <h1>Registrar consulta</h1>

  <spring:hasBindErrors name="consulta">
    <ul class="error">
      <c:forEach var="e" items="${errors.allErrors}"><li><spring:message message="${e}"/></li></c:forEach>
    </ul>
  </spring:hasBindErrors>

  <form action="${pageContext.request.contextPath}/consultas/guardar" method="post">
    <label>Nombre del paciente (máximo 40 caracteres)</label>
    <input type="text" name="nombrePaciente" maxlength="40" value="${fn:escapeXml(consulta.nombrePaciente)}" required />

    <label>Motivo de la consulta (máximo 100 caracteres)</label>
    <textarea name="motivo" maxlength="100" rows="3" required><c:out value="${consulta.motivo}"/></textarea>

    <label>Número de consultorio</label>
    <input type="number" name="numeroConsultorio" min="1" max="9999" value="${consulta.numeroConsultorio}" required />

    <label>Hora de inicio</label>
    <input type="time" name="horaInicio" value="${consulta.horaInicio}" required />

    <label>Hora de finalización</label>
    <input type="time" name="horaFin" value="${consulta.horaFin}" required />

    <label>Médico</label>
    <select name="medicoId" required>
      <option value="">-- Seleccione --</option>
      <c:forEach var="m" items="${medicos}">
        <option value="${m.id}" ${consulta.medicoId == m.id ? 'selected' : ''}><c:out value="${m.nombre}"/> - <c:out value="${m.especialidad}"/></option>
      </c:forEach>
    </select>

    <br/><br/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="submit" value="Guardar" style="width:auto" />
  </form>
  <p><a href="${pageContext.request.contextPath}/consultas">Volver a la lista</a></p>
</body>
</html>
