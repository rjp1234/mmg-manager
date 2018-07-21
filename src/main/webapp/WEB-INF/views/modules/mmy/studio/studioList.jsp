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
	function delStudio(id) {
		var flag = confirm("确认删除该录音文件？");
		if (!flag) {
			return;
		}
		$.ajax({
			url : '${ctx}/operator/studio/delStudio',
			type : 'post',
			data : "studioId=" + id,
			dataType : 'json',
			success : function(data) {
				if (data.flag) {
					$("#del_" + id).html("已删除");
				}
				alertx(data.message);
			}
		});


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
		<li><a href="${ctx}/operator/lessionOperation/lessionList">课文列表</a></li>
		<li class="active"><a
			href="${ctx}/operator/studio/studioList?lessionId=${studioInfo.lessionId}&classId=${studioInfo.classId}">录音列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="studioInfo"
		action="${ctx}/operator/studio/studioList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<form:hidden path="lessionId" />
		<ul class="ul-form">
			<li><label>学生名称：</label> <form:input path='userId'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li><label>状态：</label> <form:select path='isPointed'
					htmlEscape="false" maxlength="50" class="input-medium">
					<form:option value="0">未评分</form:option>
					<form:option value="1">已评分</form:option>
				</form:select></li>
			<li><label>班级：</label> <form:select path='classId'
					htmlEscape="false" maxlength="50" class="input-medium">
					<form:option value="">请选择</form:option>
					<c:forEach items="${classList}" var="cl">
						<form:option value="${cl.id}">${cl.name}</form:option>
					</c:forEach>
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
				<th>学生姓名</th>
				<th>班级</th>
				<th>组别</th>
				<th>上传时间</th>
				<c:if test="${searchPoint}">
					<th>评分</th>
					<th>评语</th>
				</c:if>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="studio" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${studio.userId}</td>
					<td>${studio.classId}</td>
					<td>${gradeInfo.name}</td>
					<td>${studio.createTime}</td>
					<c:if test="${searchPoint}">
						<td>${studio.point}</td>
						<td>${studio.comment }</td>
					</c:if>
					<td id="del_${studio.id}"><c:if test="${!searchPoint}">
							<a
								href="${ctx}/operator/studio/studioPointForm?id=${studio.id}&classIdSearch=${studioInfo.classId}">批改</a>
						</c:if> <c:if test="${searchPoint}">
							<a>修改</a>
						</c:if><a onclick="delStudio('${studio.id}')">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>