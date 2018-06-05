<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>贴图管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operate/tags/tagTypeForm">贴图系列管理</a></li>
		<li class="active"><a
			href="${ctx}/operate/tags/tagBatchUploadForm">学生账户批量上传</a></li>
	</ul>
	<sys:message content="${message}" />
	<form:form id="inputForm" modelAttribute="tagInfo"
		action="${ctx}/operate/tags/tagBatchUpload" method="post"
		class="form-horizontal">

		<form:hidden path="id" />
		<div id="tagType" class="control-group">
			<label class="control-label">贴图系列:</label>
			<div class="controls">
				<form:select class="input-medium" id="type" path="tagType">
					<c:forEach items="${typeList}" var="type">
						<form:option value="${type}"></form:option>
					</c:forEach>
				</form:select>
				&nbsp;<input id="newType" class="btn" type="button" value="新增贴纸系列"
					onclick="newTypeInc()" />
			</div>
		</div>
		<div id="tagTypeHidden" class="control-group" hidden="hidden">
			<label class="control-label">新增贴纸系列:</label>
			<div class="controls">
				<input id="newTagType" name="newTagType" maxlength="32"
					class="input-medium" />
			</div>
		</div>
		<div id="tagurlHidden" class="control-group" hidden="hidden">
			<label class="control-label">新增贴纸系列封面:</label>
			<div class="controls">
				<input id="nameImage" name="typeUrl" maxlength="32" type="hidden"
					class="input-medium" />
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
				&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: red;"
					class="input-xlarge">请不要上传包含中文名称的文件</span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">压缩包上传:</label>
			<div class="controls">
				<form:hidden id="zip" path="tagUrl" htmlEscape="false"
					maxlength="255" class="input-xlarge" />
				<sys:ckfinder input="zip" type="files" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
				&nbsp;&nbsp;贴纸建议大小140*140
			</div>
			<br> <br>
			<div class="control-group">
				<label class="control-label">压缩包上传规则：</label>
				<div class="controls">
					<div style="color: red;" class="input-xlarge" id="contentId"
						>
						<p>1、压缩包不得为中文命名，且压缩格式为zip。例如：1.zip</p>
						<p>2、压缩包内贴纸中文命名支持utf-8及gbk字符集，优先为utf-8。</p>
						<p>3、压缩包内贴纸命名为数字_贴纸名称.png。数字为排序参考值，越小贴纸越靠前，不一定要自然数顺序，可以重复。贴纸名称在4个字符以内。例如：2_坏笑.png。</p>
					</div>
				</div>
			</div>
			</div>
			<div class="form-actions">
				
					<input id="btnSubmit" class="btn btn-primary" type="submit"
						value="上传" onclick="return checkForm()" />
				<input id="btnCancel" class="btn" type="button" value="取消"
					onclick="history.go(-1)" />
			</div>
			<img hidden="hidden" id="imgJump" alt="" src="">
	</form:form>
</body>