<#include "nutz/copyright.ftl"/>
package ${basePackage}.${moduleName};

<#if (table.hasDateColumn)>
import java.util.Date;
</#if>
<#if (table.hasBigDecimalColumn)>
import java.math.BigDecimal;
</#if>

import org.nutz.dao.entity.annotation.*;

/**
 * <p>实体类- ${table.remarks}</p>
 * <p>Table: ${table.tableName} </p>
 *
 * @since ${.now}
 */
@Table("${table.className}")
public class ${table.className} {
<#list table.primaryKeys as key>

    /** ${key.columnName} - ${key.remarks} */
    <#if (key.isString())>
    @Name
    <#else>
    @Id
    </#if>
    @Column
    private ${key.javaType} ${key.javaProperty};
</#list>
<#list table.baseColumns as column>

    /** ${column.remarks} */
    @Column
    private ${column.javaType} ${column.javaProperty};
</#list>

<#list table.primaryKeys as key>
	/** Get ${key.remarks} */
    public ${key.javaType} ${key.getterMethodName}(){
        return this.${key.javaProperty};
    }
    
    /** Set ${key.remarks} */
    public void ${key.setterMethodName}(${key.javaType} ${key.javaProperty}){
        this.${key.javaProperty} = ${key.javaProperty};
    }
</#list>
<#list table.baseColumns as column>
	/** Get ${column.remarks} */
    public ${column.javaType} ${column.getterMethodName}(){
        return this.${column.javaProperty};
    }
    
    /** Set ${column.remarks} */
    public void ${column.setterMethodName}(${column.javaType} ${column.javaProperty}){
        this.${column.javaProperty} = ${column.javaProperty};
    }
</#list>
}