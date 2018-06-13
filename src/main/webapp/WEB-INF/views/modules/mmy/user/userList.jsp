<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>班级管理</title>
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
	window.onload=function(){
	if(${not  empty  className}){
		$("#gradeLi").attr("hidden","hidden");
		$("#classLi").attr("hidden","hidden");
	}}
	

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
				alertx("请选择班级");

			}
		});
	}
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	function delById(e,userId){
		var con=confirm("请确认是否删除该学生信息");
		if(!con){
			return;
		}
		$.ajax({
			url : '${ctx}/operator/user/delById',
			type : 'post',
			data : "id=" + userId,
			dataType : 'json',
			success : function(res) {
				var flag = res.flag;
				if (!flag) {
					alertx("删除失败，请联系管理员");
					return;
				}else{
					$(e).parent().html("<span>删除成功</span>");
				}

			}});
	}
	
</script>
<style type="text/css">
.windowsClass {
	top: 15%;
	position: absolute;
	left: 20%;
	background-color: #fff;
	border: 1px solid #000;
	width: 500px;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operator/user/userList">学生列表</a></li>
		<li><a href="${ctx}/operator/user/userBatchForm">学生账户批量上传</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userInfo"
		action="${ctx}/operator/user/userList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<!-- 检索条件：组名 -->
			<c:if test="${empty  className}">
				<input type="hidden" id="classIdSearch" name="classIdSearch">
				<li id="gradeLi"><label>组名：</label> <select name="gradeId"
					width="20%" onchange="gradeChange(this)">
						<option value="">不限</option>
						<c:forEach items="${gradeList}" var="grade">
							<option value="${grade.id}">${grade.name}</option>
						</c:forEach>
				</select></li>

				<li id="classLi"><label>班级名：</label> <form:select id="classId"
						path="classId" width="20%">
						<form:option value="">不限</form:option>
						<c:forEach items="${classList}" var="classInfo">
							<option value="${classInfo.id}">${classInfo.name}</option>
						</c:forEach>
					</form:select></li>
			</c:if>
			<c:if test="${ not empty  className}">
				<form:input type="hidden" id="classId" path="classId" />
				<li><span style="color:green;">现在正在查询
						${className}的学生，刷新清除查询条件</span></li>
			</c:if>
			<li><label>学生姓名：</label> <form:input path='realname'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li><label>学生登录名：</label> <form:input path='loginname'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>

			<li class="btns"><a id="btnSubmit" class="btn btn-primary"
				href='${ctx}/operator/user/userForm'>新增单个学生</a></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th style="width:10px">序号</th>
				<th>学生名称</th>
				<th>登录名</th>
				<th>昵称</th>
				<th>所在组</th>
				<th>所在班级</th>
				<th>累计完成</th>
				<th>创建人</th>
				<th>创建日期</th>
				<th>删除</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="user" varStatus="status">
				<tr id="tr${status.index}">
					<td>${status.index+1 }</td>
					<td>${user.realname}</td>
					<td>${user.loginname}</td>
					<td>${user.nickname}</td>
					<td>${user.gradeId}</td>
					<td>${user.classId}</td>
					<td>0/0</td>
					<td>${user.creater }</td>
					<td>${user.createTime }</td>
					<td><input class="btn" type="button" value="删除"
						onclick="delById(this,'${user.id}')"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>

</body>
</html>