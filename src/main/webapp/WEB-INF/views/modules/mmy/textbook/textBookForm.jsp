<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	var loginnameFlag = false;
	function submit() {
	
	}
	function checkLoginName(e) {
		var loginname = e.value;
		console.log(loginname.length);
		if (loginname.length < 6) {
			$("#msg").html("<span style='color:red'>登录名不得少于6个字符</span>");
			return;
		}
		$.ajax({
			url : '${ctx}/operator/user/checkLoginName',
			type : 'post',
			data : "loginname=" + loginname,
			dataType : 'json',
			success : function(data) {
				$("#msg").html(data.msg);
				globalFlag = data.flag;
			}
		});
	}
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
				<form:input  path="name" htmlEscape="false"
					maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组别:</label>
			<div class="controls">
				<form:select path="gradeId" width="20%" onchange="gradeChange(this)">
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
				<form:input  path="unitNum" htmlEscape="false"
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
				onclick="return submit();" value="保 存" />
		</div>
	</form:form>
</body>
</html>