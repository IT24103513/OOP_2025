<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html><html><head>
<title>Logs</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head><body class="p-4">

<h3>System Logs</h3>
<pre class="bg-dark text-white p-3" style="max-height:300px;overflow:auto;">
<c:forEach items="${systemLogs}" var="line">${line}
</c:forEach>
</pre>

<h3 class="mt-4">Admin Activity Logs</h3>
<pre class="bg-dark text-white p-3" style="max-height:300px;overflow:auto;">
<c:forEach items="${adminLogs}" var="line">${line}
</c:forEach>
</pre>

</body></html>
