<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
  
	<form:form method="post" modelAttribute="filterDto" action="/staticFind">

	<form:select  path="namePath">
    <form:option value="NONE"> --SELECT--</form:option>
    <form:options items="${pathList}"></form:options>
  </form:select>

		<fieldset class="form-group">
			<form:label path="targetDate">Target Date</form:label>
			<form:input path="targetDate" type="text" class="form-control"
				required="required" />
			<form:errors path="targetDate" cssClass="text-warning" />
		</fieldset>

		<button type="submit" class="btn btn-success">Add</button>
	</form:form>
</div>
<%@ include file="common/footer.jspf"%>