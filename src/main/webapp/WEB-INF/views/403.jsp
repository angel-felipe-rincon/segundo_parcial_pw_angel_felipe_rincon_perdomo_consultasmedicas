<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acceso denegado</title>
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
  <div style="text-align:center;padding-top:60px">
  <h1 style="font-size:80px;color:#b71c1c;margin:0">403</h1>
  <h2>Acceso denegado</h2>
  <p>Tu rol no tiene permiso para realizar esta acción.</p>
  <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
  </div>
</body>
</html>
