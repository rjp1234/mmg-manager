<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>教材信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	function submitCheck() {
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
			<li ><a href="${ctx}/operator/studio/studioList?lessionId=${studioInfo.lessionId}">录音列表</a></li>
		<li class="active"><a
			href="${ctx}/operator/studio/studioPointForm?id= ${studioInfo.id}">录音批改</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="lessionInfo"
		method="post" class="form-horizontal">
		<sys:message content="${message}" />
		<form:hidden path="id" />
		<div class="control-group">
			<label class="control-label">学生名:</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" maxlength="50"
					oninput="checkLessionName(this)" />
				&nbsp;&nbsp;<span id="msg"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">教材:</label>
			<div class="controls">
				<form:select style="width: 20%" onchange="textChange(this)"
					path="textId" id="textId">
					<option value="">请选择</option>
					<c:forEach items="${textList}" var='text'>
						<form:option value="${text.id}">${text.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单元:</label>
			<div class="controls">
				<form:select path="unit" style="width:20%" id="unit">
					<form:option value='0'>请选择</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课文封面:</label>
			<div class="controls">
				<form:input type="hidden" id="imageUrl" path="image" />
				<sys:ckfinder input="imageUrl" type="images" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">示范录音:</label>
			<div class="controls">
				<form:input type="hidden" path="exampleUrl" id="exampleUrl" />
				<sys:ckfinder input="exampleUrl" type="studio" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">教师的话（录音）:</label>
			<div class="controls">
				<form:input type="hidden" path="tStudioUrl" id="tStudioUrl" />
				<sys:ckfinder input="tStudioUrl" type="studio" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">教师的话（文本）:</label>
			<div class="controls">
				<form:textarea style="width:600px" path="tContent"
					htmlEscape="false" rows="3" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课文内容:</label>
			<div class="controls">
				<form:textarea style="width:600px;" path="content" id="content"
					htmlEscape="false" rows="3" class="input-xlarge" />
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				onclick="return submitCheck();" value="保 存" />
		</div>
	</form:form>
</body>
</html>