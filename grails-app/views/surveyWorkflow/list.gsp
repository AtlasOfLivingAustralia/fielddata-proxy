<%--
  Created by IntelliJ IDEA.
  User: god08d
  Date: 31/08/12
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>
<body>

<ul>

    <g:each in="${surveys}" var="survey">
        <li>${survey.id}
            <g:link controller="SurveyWorkflow" action="survey" id="${survey.id}"> ${survey.name}</g:link>
        </li>
    </g:each>

</ul>
</body>
</html>