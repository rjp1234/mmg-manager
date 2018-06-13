
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html><head>
<title>用户修改</title>
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
var globalFlag = false;
function checkName() {
		globalFlag = false;
		var name = $("#className").val();
		if (name.length < 3) {
			$("#classMsg").html("<span style='color:red'>名称太短</span>");
			return;
		}
		if (name.length > 8) {
			$("#classMsg").html("<span style='color:red'>名称太长</span>");
			return;
		}
		

		$.ajax({
			url : '${ctx}/operator/class/checkClassName',
			type : 'post',
			data : "className=" + name,
			dataType : 'json',
			success : function(data) {
				$("#classMsg").html(data.msg);
				globalFlag = data.flag;
			}
		});

	}
	
function submitCheck(){
		if(!globalFlag){
			alertx('班级名称设置不合法');
			return false;
		}
		if(!$("#gradeId").val().length>0){
			alertx('请选定组别');
			return false;
		}
		
		return true;
		
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
	
		<li class="active"><a
			href="${ctx}/operator/class/classForm?id=${classInfo.id}">班级${not empty classInfo.id?'编辑':'添加'}</a></li>
			<li><a href="${ctx}/operator/class/classList">班级列表</a></li>
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
				<form:input path="name" id="className" 
					maxlength="50" class="input-xlarge " oninput="checkName();" />
				<span id="classMsg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组别：</label>
			<div class="controls">
				<form:select id="gradeId" path="gradeId" style="width:20%">
					<c:forEach items="${gradeList}" var="grade">
						<form:option value="${grade.id}">${grade.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="保 存" onclick="return submitCheck();" />&nbsp; <input
				id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>