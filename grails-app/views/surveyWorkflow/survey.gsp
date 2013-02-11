<%--
  Created by IntelliJ IDEA.
  User: god08d
  Date: 31/08/12
  Time: 5:29 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
    <script src="${resource(dir: 'js/jquery', file: 'jquery-1.7.1.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js/jquery', file: 'jquery-ui-1.8.23.custom.min.js')}" type="text/javascript"></script>
  <style>
  table
  {
      border: 1px solid black;
      width: 100%;
  }

  .description {
      width:40%;
      line-height: 1em;
  }
  .dragHandle {
      width:5%;
  }
      .name {
          width:15%;
          line-height: 1em;
      }
      .help {
          width: 40%;
          line-height: 1em;
      }

  </style>
    <script type="text/javascript">
        $(function(){
            setupDragDrop($('#t1 tr'), $('#t2 tr'));
            setupDragDrop($('#t2 tr'), $('#t1 tr'));
        });

        function setupDragDrop(source, target) {
            source.draggable({
                helper: "clone",
                appendTo: 'body'
            });

            target.droppable({
                drop: function(event, ui) {
                    alert('row dropped ' + $(this).text());
                    ui.draggable.insertBefore($(this));
                }
                //accept: source.selector
            });
        }
        function setupDragDrop2(source, target) {
            source.draggable({
                helper: function(event) {
                    return $('<div class="drag-row"><table></table></div>')
                            .find('table').append($(event.target).closest('tr').clone()).end();
                },
                appendTo: 'body'
            });

            target.droppable({
                drop: function(event, ui) {
                    alert('row dropped ' + $(this).text());
                },
                accept: source.selector
            });
        }
    </script>
</head>
<body>
${survey.name}
<form id="surveyForm">
<table id="t1">
    <thead>
        <td class="dragHandle"></td><td class="name">Name</td><td class="description">Description</td><td class="help">Help Text</td>
    </thead>
    <tbody>
    <g:each in="${survey.orderedAttributes}" var="attr">
        <tr>
            <td><input type="hidden" name="id" value="${attr.id}"/></td>
            <td class="name">${attr.name}</td>
            <td class="description">${attr.description}</td>
            <td class="help"></td>

        </tr>
    </g:each>
    </tbody>
    <tbody>
    <tr></tr>
    </tbody>
</table>


<table id="t2">
    <thead>
    <td class="name">Name</td><td class="description">Description</td><td class="help">Help text</td>
    </thead>
    <tbody><tr><td>hi</td><td></td><td>s</td></tr></tbody>
</table>
</form>

</body>
</html>