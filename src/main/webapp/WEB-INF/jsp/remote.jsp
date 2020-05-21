<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <div class="row">
        <form:form method="post" modelAttribute="filterDto" action="/remote">

            <form:select path="namePath">

                <form:option value="NONE"> --SELECT--</form:option>
                <form:options items="${stationInfoDto.stations}"></form:options>
            </form:select>

            <form:select path="payloadType">
                <form:option value="NONE"> --SELECT--</form:option>
                <form:options items="${stationInfoDto.payloadTypes}"></form:options>
            </form:select>

            <fieldset class="form-group row">
                <form:label path="targetDate">Target Date</form:label>
                <form:input path="targetDate" type="text" class="form-control"
                            required="required"/>
                <form:errors path="targetDate" cssClass="text-warning"/>
            </fieldset>

            <button type="submit" class="btn btn-success">Add</button>
        </form:form>
    </div>
</div>

<%@ include file="common/footer.jspf" %>