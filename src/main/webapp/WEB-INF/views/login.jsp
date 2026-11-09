<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar sesión</title>
<style>
  body { font-family: Arial, sans-serif; background: #f2f2f2; display: flex; justify-content: center; padding-top: 80px; }
  .caja { background: white; padding: 30px; border-radius: 8px; width: 300px; box-shadow: 0 2px 8px rgba(0,0,0,.15); }
  input { width: 100%; padding: 8px; margin: 6px 0 14px; box-sizing: border-box; }
  button { width: 100%; padding: 10px; background: #1565c0; color: white; border: none; border-radius: 4px; cursor: pointer; }
  .error { color: #b71c1c; margin-bottom: 10px; }
  .ok { color: #2e7d32; margin-bottom: 10px; }
</style>
<script>
window.addEventListener('pageshow', function (e) {
  var nav = performance.getEntriesByType('navigation')[0];
  if (e.persisted || (nav && nav.type === 'back_forward')) { window.location.reload(); }
});
</script>
</head>
<body>
  <div class="caja">
    <h2>Consultas médicas</h2>
    <h3>Iniciar sesión</h3>

    <c:if test="${param.error != null}">
      <div class="error">Usuario o clave incorrectos.</div>
    </c:if>
    <c:if test="${param.csrf != null}">
      <div class="error">La página había expirado. Intenta iniciar sesión de nuevo.</div>
    </c:if>
    <c:if test="${param.logout != null}">
      <div class="ok">Cerraste sesión correctamente.</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
      <label>Usuario</label>
      <input type="text" name="username" required />
      <label>Clave</label>
      <input type="password" name="password" required />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <button type="submit">Entrar</button>
    </form>
  </div>
</body>
</html>
