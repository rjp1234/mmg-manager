 <%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<title>用户修改</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-imgbox/jquery.min.js"></script>
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
</script>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operate/clientUser/">班级列表</a></li>
		<li class="active"><a
			href="${ctx}/operator/class/classForm?id=${classInfo.id}">班级${not empty classInfo.id?'编辑':'添加'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="classInfo"
		action="${ctx}/operator/class/createClass" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">班级名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-xlarge "  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组别：</label>
			<div class="controls">
					<form:select path="gradeId">
					<c:forEach items="${gradeList}" var="grade">
						<form:option value="${grade.id}">${grade.name}</form:option>
					</c:forEach>
					</form:select>
			</div>
		</div>
		<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>