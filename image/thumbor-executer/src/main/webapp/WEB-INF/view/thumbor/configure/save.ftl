<#import "/lib/common.ftl" as com />
<#import "/lib/thumbor/configure.ftl" as lib />
<#escape x as (x)!>
    <#compress>
        <@com.indexHeader title="图片格式配置" keywords="图片格式配置" description="图片格式配置" />
        <@lib.saveTable />
        <@com.indexFooter />
    </#compress>
</#escape>