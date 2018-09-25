<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basePackage}.service.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>;

import ${basePackage}.service.BaseService;
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.bo.${className};
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.vo.${className}Vo;
import java.util.List;
import com.github.pagehelper.PageInfo;

public interface ${className}Service extends BaseService<${className},${table.columns[0].javaType}>{
	
	List<${className}Vo> queryVo(${className}Vo vo);
	
	PageInfo<${className}Vo> queryPage(${className}Vo vo);
	
}