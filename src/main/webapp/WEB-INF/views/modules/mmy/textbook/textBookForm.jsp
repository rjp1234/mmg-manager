<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>教材信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	function submitCheck() {
		var len=$("#name").val().length;
		if(len==0){
			alertx("教材名称不得为空");
			return false;
		}
		len=$("#gradeId").val().length
		if(len==0){
			alertx("请选择组别");
			return false;
		}
		len=$("#unitNum").val();
		if(len<1){
			alertx("单元数必须大于等于1");
			return false;
		}
		len=$("#imageUrl").val().length;
		if(len==0){
			alertx("请选择封面");
			return false;
		}
		return true;
	
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operator/textbook/textBookList">教材列表</a></li>
		<li class="active"><a href="${ctx}/operator/textbook/textBookForm">教材添加</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="textBookInfo"
		action="${ctx}/operator/textbook/insertTextBook" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">教材名称:</label>
			<div class="controls">
				<form:input id="name"  path="name" htmlEscape="false"
					maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组别:</label>
			<div class="controls">
				<form:select path="gradeId" width="20%"  id="gradeId">
					<form:option value="">请选择</form:option>
					<c:forEach items="${gradeList}" var='grade'>
						<form:option value="${grade.id}">${grade.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教材单元数:</label>
			<div class="controls">
				<form:input id="unitNum"  path="unitNum" htmlEscape="false"
					maxlength="50" />
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">教材封面:</label>
			<div class="controls">
				<form:input type="hidden" id="imageUrl" path="image"  />
				<sys:ckfinder input="imageUrl" type="images" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				onclick="return submitCheck();" value="保 存" />
		</div>
	</form:form>
</body>
</html>