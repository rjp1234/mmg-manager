
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
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
		if (name.length < 2) {
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
		var name=$("#className").val();
		if(name==${classInfo.name}){
			//名称未变更的情况下
		}else{
		if(!globalFlag){
			alertx('班级名称设置不合法');
			return false;
		}
		}
		if(!$("#gradeId").val().length>0){
			alertx('请选定组别');
			return false;
		}
		return true;
	}
	
	/**
		更改班级名	
	*/
function changeClassName(e){
	$("#className").removeAttr("readOnly");
	$(e).val("取消更改");
	$(e).attr("onclick","canelChange(this)");
}
/**
 * 取消名字更改
 */
function canelChange(e){
	$("#className").attr("readOnly","true");
	$("#className").val('${classInfo.name}');
	$(e).attr("onclick","changeClassName(this)");
	$(e).val("点击更改");
	$("#classMsg").html("");
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
		action="${ctx}/operator/class/modifyClass" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">班级名称：</label>
			<div class="controls">
				<form:input path="name" id="className" maxlength="50"
					class="input-xlarge " oninput="checkName();" readOnly="readonly" />
				<input type="button" class="btn" onclick="changeClassName(this);"
					value="点击更改"><span id="classMsg"></span>
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