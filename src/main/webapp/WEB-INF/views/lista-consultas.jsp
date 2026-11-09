<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consultas médicas</title>
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
  <h1>Consultas médicas</h1>

  <p>
    <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
    <a href="${pageContext.request.contextPath}/consultas/nueva">Registrar consulta</a>
  </p>

  <table>
    <tr>
      <th>ID</th>
      <th>Paciente</th>
      <th>Motivo</th>
      <th>Consultorio</th>
      <th>Horario</th>
      <th>Médico</th>
      <th>Acciones</th>
    </tr>
    <c:forEach var="x" items="${consultas}">
      <tr>
        <td>${x.id}</td>
        <td><c:out value="${x.nombrePaciente}"/></td>
        <td><c:out value="${x.motivo}"/></td>
        <td>${x.numeroConsultorio}</td>
        <td>${x.horaInicio} - ${x.horaFin}</td>
        <td><c:out value="${x.medico.nombre}"/> (<c:out value="${x.medico.especialidad}"/>)</td>
        <td>
          <a href="${pageContext.request.contextPath}/consultas/editar/${x.id}">Editar</a>
          <a href="${pageContext.request.contextPath}/consultas/horario/${x.id}">Cambiar horario</a>
          <form action="${pageContext.request.contextPath}/consultas/eliminar/${x.id}" method="post" style="display:inline"
                onsubmit="return confirm('¿Eliminar esta consulta?');">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit">Eliminar</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

  <c:if test="${empty consultas}">
    <p>No hay consultas para mostrar.</p>
  </c:if>
</body>
</html>
