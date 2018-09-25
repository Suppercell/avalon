<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basePackage}.service.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${basePackage}.dao.BaseDao;
import ${basePackage}.dao.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.${className}Dao;
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.bo.${className};
import ${basePackage}.entity.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.vo.${className}Vo;
import ${basePackage}.service.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.${className}Service;
import ${basePackage}.service.BaseServiceImpl;
import java.util.List;
import com.github.pagehelper.PageInfo;


@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className},${table.columns[0].javaType}> implements ${className}Service {

	@Autowired
    private ${className}Dao ${classNameLower}Dao;
	    
    @Override
    protected BaseDao<${className},${table.columns[0].javaType}> getDao() {        
        return ${classNameLower}Dao;
    }
    
    @Override
    public List<${className}Vo> queryVo(${className}Vo vo) {        
        return ${classNameLower}Dao.queryVo(vo);
    }
    
    @Override
    public PageInfo<${className}Vo> queryPage(${className}Vo vo) {
        preparePage(vo.getPageNo(), vo.getPageSize());
        List<${className}Vo> list = queryVo(vo);
        PageInfo<${className}Vo> page = new PageInfo<${className}Vo>(list);
        return page;
    }

}
