<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
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
  <h1>Bienvenido, <c:out value="${username}"/></h1>
  <p>Tus roles:
    <c:forEach var="r" items="${roles}"><b><c:out value="${r}"/></b> </c:forEach>
  </p>

  <p>
    <a href="${pageContext.request.contextPath}/consultas">Ver consultas médicas</a>
    <a href="${pageContext.request.contextPath}/consultas/nueva">Registrar consulta</a>
    <a href="${pageContext.request.contextPath}/medicos">Gestionar médicos</a>
    <a href="${pageContext.request.contextPath}/swagger-ui.html" target="_blank">Documentación Swagger</a>
  </p>

  <form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <button type="submit">Cerrar sesión</button>
  </form>
</body>
</html>
