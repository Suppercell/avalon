<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basePackage}.dao.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>;

import ${basePackage}.dao.BaseDao;
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.bo.${className};
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.vo.${className}Vo;
import java.util.List;

public interface ${className}Dao extends BaseDao<${className},${table.columns[0].javaType}>{

	List<${className}Vo> queryVo(${className}Vo vo);

}