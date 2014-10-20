package org.covito.coder.db.model;

import java.util.ArrayList;
import java.util.List;

import org.covito.coder.util.StringUtil;

public class Table implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID  = -7246043091254837124L;
    private String            tableName;
    private String            tableType;
    private String            tableAlias;
    private String            remarks;
    private String            className;
    private String            javaProperty;
    private String            catalog           = null;
    private String            schema            = null;
    private List<Column>      baseColumns       = new ArrayList<Column>();
    private List<Column>      primaryKeyColumns = new ArrayList<Column>();

    public Table(){
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.tableAlias = tableName;
        this.className = StringUtil.getCamelCaseString(tableAlias, true);
        this.javaProperty = StringUtil.getCamelCaseString(tableAlias, false);
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks;
    }

    public boolean isHasRemarks() {
        return StringUtil.isNotEmpty(remarks);
    }

    public String getRemarksUnicode() {
        return StringUtil.toUnicodeString(getRemarks());
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Column getColumn(String columnName) {
        for (Column column : primaryKeyColumns) {
            if (column.getColumnName().equals(columnName)) {
                return column;
            }
        }
        for (Column column : baseColumns) {
            if (column.getColumnName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    public List<Column> getColumns() {
        List<Column> allColumns = new ArrayList<Column>();
        allColumns.addAll(primaryKeyColumns);
        allColumns.addAll(baseColumns);
        return allColumns;
    }

    public List<Column> getBaseColumns() {
        return baseColumns;
    }

    public void addBaseColumn(Column column) {
        this.baseColumns.add(column);
    }

    public List<Column> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public void addPrimaryKeyColumn(Column primaryKeyColumn) {
        this.primaryKeyColumns.add(primaryKeyColumn);
    }

    public String getJavaProperty() {
        return javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        this.className = StringUtil.getCamelCaseString(tableAlias, true);
        this.javaProperty = StringUtil.getCamelCaseString(tableAlias, false);
    }

    public boolean isHasDateColumn() {
        for (Column column : getColumns()) {
            if (column.isDate()) {
                return true;
            }
        }
        return false;
    }

    public boolean isHasBigDecimalColumn() {
        for (Column column : getColumns()) {
            if (column.isBigDecimal()) {
                return true;
            }
        }
        return false;
    }
}
