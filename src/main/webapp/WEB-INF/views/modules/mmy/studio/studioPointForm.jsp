<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>录音信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	function submitCheck() {
		return false;
	}
	function checkPoint(e) {
		var value = e.value;
		console.log(value);
		if (value > 10) {
			e.value = 10;
		}
	}
	function selectComment() {
		var commentSelect = $("#commentSelect").val();
		$("#comment").html(commentSelect);
	}
	function pointSubmit() {
		$.ajax({
			url : '${ctx}/operator/studio/studioInfoPoint',
			type : 'post',
			data : "id=" + $("#id").val() + "&point=" + $("#point").val() + "&comment=" + $("#comment").val() + "&classId=${classIdSearch}",
			dataType : 'json',
			success : function(data) {
				console.log(data);
				if (data.flag) {
					var studio = data.nextStudio;
					//成功
					if (studio) {
						$("#id").val(studio.id);
						$("#userName").html(studio.userId);
						$("#className").html(studio.classId);
						$("#createTime").html(studio.createTime);
						$("#studioUrl").html(studio.url);
						$("#point").val('');
					} else {
						$("#userName").html("");
						$("#className").html("");
						$("#createTime").html("");
						$("#studioUrl").html("");
						$("#point").val('');
					}
				}
				alertx(data.msg);

			}
		});

	}
</script>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="${ctx}/operator/studio/studioList?lessionId=${studioInfo.lessionId}">录音列表</a></li>
		<li class="active"><a
			href="${ctx}/operator/studio/studioPointForm?id= ${studioInfo.id}&classIdSearch=${classIdSearch}">录音批改</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="studioInfo" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<input type="hidden" id="id" value="${studioInfo.id}" />
		<div class="control-group">
			<label class="control-label">学生名:</label>
			<div id="userName" class="controls">${userInfo.realname}</div>
		</div>

		<div class="control-group">
			<label class="control-label">班级:</label>
			<div id="className" class="controls">${classInfo.name}</div>
		</div>
		<div class="control-group">
			<label class="control-label">组别:</label>
			<div id="gradeName" class="controls">${gradeInfo.name}</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间:</label>
			<div id="createTime" class="controls">${studioInfo.createTime}</div>
		</div>
		<div class="control-group">
			<label class="control-label">录音:</label>
			<div class="controls">
				<audio id="studioUrl" controls="controls" src="${studioInfo.url}"></audio>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分:</label>
			<div class="controls">
				<input class="input-medium" id="point"
					onKeyUp="value=value.replace(/[^\d]/g,'')"
					oninput="checkPoint(this);">

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评语模版</label>
			<div class="controls">
				<select id="commentSelect" style="width:40%;"
					onchange="selectComment();">
					<option value="">自定义</option>
					<c:forEach items="${commonComment}" var="comment"
						varStatus="status">
						<option value="${comment}">${status.index+1}、${comment}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评语:</label>
			<div class="controls">
				<textarea class="input-medium" style="width:200px; height: 100px"
					maxlength="140" id="comment"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button"
				onclick="pointSubmit();" value="保 存" />
		</div>
	</form:form>
</body>
</html>