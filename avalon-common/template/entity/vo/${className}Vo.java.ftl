<#include "/macro.include"/>
<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.vo;

import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.bo.${className};

/**
 * 
 * @ClassName: ${className}Vo
 * @Description:
 * @author: carme-generator
 *
 */
public class ${className}Vo extends ${className} {
	private Integer pageSize;

    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
