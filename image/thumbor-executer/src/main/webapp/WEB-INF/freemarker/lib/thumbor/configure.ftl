<#-- 保存表格开始 -->
<#macro saveTable charset="UTF-8"  >
    <table width="100%" border="0">
        <@com.tags.spform.form servletRelativeAction="/thumbor/configure/save" method="POST" acceptCharset="${charset}" modelAttribute="bean" >
            <tr>
                <td colspan="2">
                    <table width="100%" bgcolor="0">
                        <caption>服务器</caption>
                        <tr>
                            <td>地址</td>
                            <td>
                                <@com.tags.spform.input path="server.url" />
                                <@com.tags.spform.errors path="server" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>去除空白</td>
                <td>
                    <@com.tags.spform.checkbox path="trim" />
                    <@com.tags.spform.errors path="trim" />
                </td>
            </tr>
            <tr>
                <td>智能</td>
                <td>
                    <@com.tags.spform.checkbox path="smart" />
                    <@com.tags.spform.errors path="smart" />
                </td>
            </tr>
            <tr>
                <td>图片信息</td>
                <td>
                    <@com.tags.spform.checkbox path="meta" />
                    <@com.tags.spform.errors path="meta" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="提交"/>
                </td>
            </tr>
        </@com.tags.spform.form>
    </table>
</#macro>
<#-- 保存表格结束 -->