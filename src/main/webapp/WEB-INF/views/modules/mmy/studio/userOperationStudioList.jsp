<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>录音评分管理</title>
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
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function issueLession() {
		var classId = $("#issueClassList").val();
		if (classId.length == 0) {
			alertx("请选择班级");
			return;
		}

		var lessionId = $("#lessionId").val();
		if (lessionId.length == 0) {
			alertx("请选择课文");
			return;
		}

		$.ajax({
			url : '${ctx}/operator/lession/lessionIssue',
			type : 'post',
			data : "lessionId=" + lessionId + "&classId=" + classId,
			dataType : 'json',
			success : function(data) {
				if (data.flag) {
					closeWindow();
					alertx(data.msg);
				} else {
					alertx(data.msg);
				}
			}
		});


	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operator/userOperation/userList?classId=${classId}&userName=${userName}">学生列表</a></li>
		<li class="active"><a href="${ctx}/operator/userOperation/userStudioList?userId=${userId}">学生录音列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="studioInfo"
		action="${ctx}/operator/userOperation/userStudioList?userId=${userId}" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
			<form:hidden path="lessionId"/>
		<ul class="ul-form" hidden="hidden">
			<li ><label>状态：</label> <form:select path='id'
					htmlEscape="false" maxlength="50" class="input-medium">
					<form:option value="1">已评分</form:option>
					<form:option value="0">未评分</form:option>
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th style="width:10px">序号</th>
				<th>课文名称</th>
				<th>录音</th>
				<th>上传时间</th>
				<th>评分</th>
				<th>评语</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="studio" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${studio.lessionName }</td>
					<td><audio src="${studio.url}" loop controls="controls"></audio></td>					
					<td>${studio.createTime}</td>
					<td>${studio.point}</td>
					<td>${studio.comment }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>