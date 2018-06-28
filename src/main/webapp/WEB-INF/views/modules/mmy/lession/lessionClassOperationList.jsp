<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>课文批改</title>
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
		<li class="active"><a href="${ctx}/operator/lession/lessionList">课文列表</a></li>
		<li><a href="${ctx}/operator/lession/lessionForm">课文添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="lessionInfo"
		action="${ctx}/operator/lession/lessionList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>课文名称：</label> <form:input path='name'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li><label>教材名称：</label> <form:select path='textId'
					htmlEscape="false" maxlength="50" class="input-medium">
					<c:forEach items="${textList}" var='text'>
						<form:option value="${text.id}">${text.name}</form:option>
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
				<th>课文名称</th>
				<th>班级</th>
				<th>下发状态</th>
				<th>完成人数/总人数</th>
				<th>待批改人数</th>
				<th>分数分布</th>
				<th>批改</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="lession" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${lession.name}</td>
					<td><img src="${lession.image}"
						style="width: 70px;height: 45px"></td>
					<td>${lession.textId}</td>
					<td>第${lession.unit}单元</td>
					<td><audio controls="controls" src="${lession.exampleUrl}"></audio></td>
					<td><a class="btn"
						href="${ctx}/operator/lession/detailsForm?id=${lession.id}">查看</a>
						<a class="btn"
						href="${ctx}/operator/lession/lessionForm?id=${lession.id}">编辑</a>
						<input class="btn" type="button" value="删除"
						onclick="delById(this,'${lession.id}')">&nbsp;<input
						class="btn" type="button" value="下发课文"
						onclick="alertWindow('${lession.id}','${lession.name}')">&nbsp;<a
						href="${ctx}/operator/lession/getClassInfoListByLession?id=${lession.id}">查看已下发班级</a>&nbsp;<a
						href="${ctx}/operator/studio/studioList?lessionId=${lession.id}">批改录音</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>
	<div id="alertWindow" style="display:none;"
		class="form-horizontal windowsClass">
		<input id="lessionId" type="hidden">
		<div class="control-group">
			<label class="control-label">下发课程:</label>
			<div class="controls" id="lessionname"></div>
		</div>
		<div class="control-group">
			<label class="control-label">下发班级:</label>
			<div class="controls">
				<select id="issueClassList">
					<option value="">请选择</option>
				</select><br>
			</div>
			<div class="form-actions">
				<input onclick="issueLession()" type="button"
					class="btn btn-primary" value="确认下发">&nbsp; <input
					onclick="closeWindow()" type="button" class="btn" value="关闭">
			</div>
		</div>
	</div>
</body>
</html>