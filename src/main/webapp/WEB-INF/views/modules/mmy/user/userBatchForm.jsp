<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>学生管理</title>
<meta name="decorator" content="default" />

<script type="text/javascript"
	src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table-zh-CN.js"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	function gradeChange(e) {
		var id = e.value;
		$.ajax({
			url : '${ctx}/operator/class/getByGradeId',
			type : 'post',
			data : "gradeId=" + id,
			dataType : 'json',
			success : function(res) {
				var flag = res.flag;
				if (!flag) {
					alertx("获取班级数据失败，请刷新重试");
					return;
				}
				var data = res.data;
				var element = $("#classId");
				var str = "<option value=''>请选择</option>";
				for (var i = 0; i < data.length; i++) {
					str = str + "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
				}
				element.html(str);

			}
		});



	}
	function checkForm() {
		if ($("#classId").val().length <= 0) {
			alertx("请选择学生班级")
			return false;
		}
		if($("#excel").val().length<=0){
			alertx("请选择要上传的excel");
			return false;
		}
		return true;

	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operator/user/userList">学生列表</a></li>
		<li class="active"><a href="${ctx}/operator/user/userBatchForm">学生账户批量上传</a></li>
	</ul>

	<form:form id="inputForm" modelAttribute="userInfo"
		action="${ctx}/operator/user/userBatchCreate" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<form:hidden path="id" />
		<br>
		<br>
		<div class="control-group">
			<label class="control-label">组别:</label>
			<div class="controls">
				<select class="input-medium" id="grade" onchange="gradeChange(this)">
					<option value="">请选择</option>
					<c:forEach items="${gradeList}" var="grade">
						<option value="${grade.id}">${grade.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级:</label>
			<div class="controls">
				<form:select class="input-medium" id="classId" path="classId">
					<form:option value="">请选择</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文档上传:</label>
			<div class="controls">
				<input type="hidden" id="excel" name="excelUrl" />
				<sys:ckfinder input="excel" type="files" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="上传" onclick="return checkForm()" /> <input id="btnCancel"
				class="btn" type="button" value="取消" onclick="history.go(-1)" />
		</div>
		<img hidden="hidden" id="imgJump" alt="" src="">
	</form:form>
</body>