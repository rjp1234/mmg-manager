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
		var len = $("#realname").val().length;
		if (!len > 0) {
			alertx("请输入学生姓名");
			return false;
		}
		len = $("#loginname").val().length;
		if (!len > 0) {
			alertx("请输入登录名");
			return false;
		}
		if (loginnameFlag) {
			alertx("登录名不符合要求，请更正");
			return false;
		}

		len = $("#password").val().length;
		if (len < 6) {
			alertx("密码必须大于6位");
			return false;
		}
		return true;

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
		<li><a href="${ctx}/operator/user/userList">学生列表</a></li>
		<li class="active"><a href="${ctx}/operator/user/userForm">学生用户添加</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="userInfo"
		action="${ctx}/operator/user/createUser" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">学生名称:</label>
			<div class="controls">
				<form:input id="realname" path="realname" htmlEscape="false"
					maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<form:input id="loginname" path="loginname" htmlEscape="false"
					maxlength="50" oninput="checkLoginName(this);" />
				&nbsp;<span id="msg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<form:input id="password" path="password" htmlEscape="false"
					maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input id="phonenum" path="phonenum" htmlEscape="false"
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
			<label class="control-label">班级:</label>
			<div class="controls">
				<form:select path="classId" width="20%" id="classId">
					<form:option value="">请选择</form:option>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				onclick="return submit();" value="保 存" />
		</div>
	</form:form>
</body>
</html>