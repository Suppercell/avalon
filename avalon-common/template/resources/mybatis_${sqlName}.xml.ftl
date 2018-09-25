<#include "/macro.include">
<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 <mapper namespace="${basePackage}.dao.<#if module??>${module}<#else>${table.sqlName?split("_")[1]}</#if>.${className}Dao"> 

    <resultMap id="BaseResultMap" type="${classNameLower}">
    <#list table.columns as column>
        <result column="${column.sqlName}" property="${column.columnNameLower}" />
    </#list>
    </resultMap>

    <sql id="Base_Column_List" >
        <![CDATA[
        <#list table.columns as column>
              ${column.sqlName} <#if column_has_next>,</#if>
        </#list>
        ]]>
    </sql>
    
    <!-- ======================================================================== 
      	自动生成sql
    ========================================================================= -->
    <!--根据ID查询记录-->
    <select id="getById" resultMap="BaseResultMap" parameterType="java.lang.Long" >
      select <include refid="Base_Column_List" /> from ${table.sqlName} where id = ${r"#{id}"} and is_delete=0
    </select>

	<!--通过IDS查询多条记录-->
    <select id="getByIds" resultMap="BaseResultMap">
	   select <include refid="Base_Column_List"/> from ${table.sqlName} where id in  
        <foreach item="item" index="index" collection="array" 
                 open="(" separator="," close=")"> 
                ${r"#{item}"}
        </foreach> 
	   and is_delete = 0  
	</select>

    <!--save保存-->
    <insert id="save" parameterType="${classNameLower}" useGeneratedKeys="true" keyProperty="id" >
      	insert into ${table.sqlName} 
      	(
      	<trim suffix="" suffixOverrides=",">       	
      	<#list table.columns as column>
      		<#if !column.pk>
		  	  <if test="${column.columnNameLower} != null" >
		        ${column.sqlName},
		      </if> 
        	</#if>
        </#list>
        </trim>
      	) values(
      	<trim suffix="" suffixOverrides=","> 
      	<#list table.columns as column>
      		<#if !column.pk>
		  	  <if test="${column.columnNameLower} != null" >
		        ${r"#{"}${column.columnNameLower}},
		      </if> 
        	</#if>
	    </#list>
	    </trim>
      	)
    </insert>
    
    <!--saveBatch批量保存-->
    <insert id="saveBatch" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id" >
      	insert into ${table.sqlName} 
      	(
      	<#list table.columns as column>
      	<#if !column.pk>
              ${column.sqlName} <#if column_has_next>,</#if>
        </#if>
        </#list>
      	) values
      	<foreach collection="list" item="item" index="index" separator="," >  
        (
        <#list table.columns as column>
      	<#if !column.pk>
        	  ${r"#{item."}${column.columnNameLower}}<#if column_has_next>,</#if>
        </#if>
	    </#list>
        ) 
    	</foreach>
    </insert>
    
    <!--update更新-->
    <update id="update" parameterType="${classNameLower}">
      update ${table.sqlName}
      	<set>
      	<trim suffix="" suffixOverrides=",">   
      	<#list table.columns as column>
	      <if test="${column.columnNameLower} != null" >
	        ${column.sqlName} = ${r"#{"}${column.columnNameLower}},
	      </if> 
	    </#list> 
	    </trim>  
    	</set>
      where id = ${r"#{id}"} and is_delete=0
    </update>
     
    <!--updateBatch批量更新-->
    <update id="updateBatch" parameterType="java.util.List">
    	update ${table.sqlName}
        <trim prefix="set" suffixOverrides=",">
    		<#list table.columns as column>
	      		<trim prefix="${column.sqlName}=case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                     <if test="item.${column.columnNameLower} !=null">
                         when id=${r"#{item.id"}} then ${r"#{item."}${column.columnNameLower}}
                     </if>
                 </foreach>
            	</trim>
	    	</#list>     		
    	</trim>
    	where id in
        <foreach collection="list" index="index" item="item" separator="," open="(" close=")">
            ${r"#{item.id"}}
        </foreach>
    </update>
    
    <sql id="query_base_sql">
        <#list table.columns as column>
	      <if test="${column.columnNameLower} != null" >
	       and ${column.sqlName} = ${r"#{"}${column.columnNameLower}}
	      </if> 
	    </#list>
    </sql>
    
    <!--query基础查询-->
    <select id="query" resultMap="BaseResultMap" parameterType="${classNameLower}">
	   select <include refid="Base_Column_List"/> from ${table.sqlName} where is_delete = 0  
       <include refid="query_base_sql"></include>
	</select>
    
     
    <!-- ========================================================================
     								自定义sql
    ========================================================================= -->
	
	<!--query扩展查询-->
    <select id="queryVo" resultMap="BaseResultMap" parameterType="${classNameLower}Vo">
	   select <include refid="Base_Column_List"/> from ${table.sqlName} where is_delete = 0  
       <include refid="query_base_sql"></include>
	</select>

</mapper>