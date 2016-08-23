<#import "/plugin/tags.ftl" as tags />

<#-- 头部开始 -->
<#macro indexHeader projectName="" title="" charset="UTF-8" keywords="" description="" >
	<!DOCTYPE HTML>
	<html lang="${charset}">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=${charset}" />
			<meta name="viewport" content="width=device-width, initial-scale=1" />
			<title>${title}<#if projectName?? && projectName?trim != "" >&nbsp;-&nbsp;${projectName!}<#elseif PROJECT_NAME?? && PROJECT_NAME?trim != "" >&nbsp;-&nbsp;${PROJECT_NAME!}</#if><#if PROJECT_POWER?? && PROJECT_POWER?trim != "" >&nbsp;-&nbsp;${PROJECT_POWER!}</#if></title>
			<meta http-equiv="pragma" content="no-cache" />
			<meta http-equiv="cache-control" content="no-cache" />
			<meta http-equiv="expires" content="0" />
			<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
			<#if keywords?? && keywords!="">
				<meta http-equiv="keywords" content="${keywords}" />
			<#else>
				<meta http-equiv="keywords" content="${title}" />
			</#if>
			<#if description?? && description!="">
				<meta http-equiv="description" content="${description}" />
			<#else>
				<meta http-equiv="description" content="${title}" />
			</#if>
		</head>
		<body>
</#macro>
<#-- 头部结束 -->

<#-- 底部开始 -->
<#macro indexFooter>
		</body>
	</html>
</#macro>
<#-- 底部结束 -->

<#-- 分页开始 -->
<#macro pager>
	<div>
		<span>
			<@tags.spring.message code="display.message.page.word.total" />
			${page.totalRecord!0}
			<@tags.spring.message code="display.message.page.word.record" />
		</span>
		<span>
			${page.pageNO!1}/${page.totalPage!0}
		</span>
		<span>
			<#list page.pageNoDisp?split("|") as displayPageNO>
				<#if displayPageNO == "0" >
					<label style="font-size: 10px; width: 20px; text-align: center;">•••</label>
				<#elseif displayPageNO != "${page.pageNo}">
					<button class="btn btn-default btn-sm" onclick="pageClick(${displayPageNO})">${displayPageNO}</button>
				<#else>
					<button class="btn btn-primary btn-sm" style="font-weight:bold;">${displayPageNO}</button>
				</#if>
			</#list>
		</span>
		<span><@tags.spring.message code="display.message.page.word.eachPage" /><@pageSizeSelect name="page.pageSize" /><@tags.spring.message code="display.message.page.word.row" /></span>
	</div>
</#macro>
<#-- 分页结束 -->

<#-- 分页大小选择器开始 -->
<#macro pageSizeSelect id="" name="" >
	<#if !(id??) || id?trim == "">
		<#local id = name />
	</#if>
	<#if !(name??) || name?trim == "">
		<#local name = id />
	</#if>
	<select id="${id}" name="${name}">
		<#list PAGE_SIZE_LIST?split("|") as item>
			<option value="${item}" <#if page.pageSize?string == item?string>selected</#if> >${item}</option>
		</#list>
	</select>
</#macro>
<#-- 分页大小选择器结束 -->