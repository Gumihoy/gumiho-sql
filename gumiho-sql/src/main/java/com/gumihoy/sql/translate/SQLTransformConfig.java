/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.translate;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.enums.DoubleQuoteActionType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kent onCondition 2018/1/23.
 */
public class SQLTransformConfig {

    public DBType source;
    public DBType target;
    public DBVersion targetDBVersion;

    /**
     * 表名、字段名移除引号
     */
    public DoubleQuoteActionType doubleQuoteAction = DoubleQuoteActionType.NONE;

    /**
     * 对象名(表、视图、PL/SQL等)、字段名移除schema
     */
    public boolean removeSchema = false;


    private Map<String, TableMapping> tableMappings = new LinkedHashMap<>();

    public TableMapping findTableMapping(ISQLName tableName) {

        String lowerFullName = tableName.getFullName().toLowerCase();
        String lowerName = tableName.getSimpleName().toLowerCase();

        TableMapping mapping = tableMappings.get(lowerFullName);
        if (mapping != null) {
            return mapping;
        }

        return tableMappings.get(lowerName);
    }

    public void addTableMapping(TableMapping tableMapping) {
        if (tableMapping == null
                || tableMapping.name == null
                || tableMapping.name.length() == 0) {
            return;
        }
        this.tableMappings.put(tableMapping.name.toLowerCase(), tableMapping);

        if (tableMapping.targetName == null) {
            tableMapping.targetName = tableMapping.name;
        }
    }

    public static class TableMapping {
        public String owner;
        public String name;
        public String targetOwner;
        public String targetName;
        /**
         * 修改字段
         */
        public final Set<ColumnMapping> columnMappings = new LinkedHashSet<>();
        /**
         * 移除字段
         */
        public final Set<String> removeColumns = new LinkedHashSet<>();
        /**
         * 添加字段
         */
        public final Set<ColumnMapping> addColumnMappings = new LinkedHashSet<>();


        public TableMapping(String name, String targetName, ColumnMapping... columnMappings) {
            this.name = name;
            this.targetName = targetName;

            for (ColumnMapping columnMapping : columnMappings) {
                addColumnMapping(columnMapping);
            }
        }


        public ColumnMapping findColumnMapping(String columnName) {
            for (ColumnMapping columnMapping : columnMappings) {
                if (columnMapping.name.equals(columnName)) {
                    return columnMapping;
                }
            }

            for (ColumnMapping columnMapping : columnMappings) {
                if (columnMapping.name.equalsIgnoreCase(columnName)) {
                    return columnMapping;
                }
            }
            return null;
        }

        public void addColumnMapping(ColumnMapping columnMapping) {
            if (columnMapping == null) {
                return;
            }
            this.columnMappings.add(columnMapping);
        }

        public boolean isRemoveColumn(String columnName) {
            return removeColumns.contains(columnName);
        }

        public void addRemoveColumn(String columnName) {
            removeColumns.add(columnName);
        }

        public void addAddColumnMapping(ColumnMapping columnMapping) {
            if (columnMapping == null) {
                return;
            }
            this.addColumnMappings.add(columnMapping);
        }
    }


    // 当前遍历下标
    public int index = 0;
    // 当前 sql 的 stmt LIST
    public List<ISQLObject> stmtList = new ArrayList<>();


    /**
     * CREATE index ON table 映射关系
     */
    private final ConcurrentHashMap<String, String> INDEX_TABLE_MAP = new ConcurrentHashMap<>();

    public String getIndexTable(String index) {
        return INDEX_TABLE_MAP.get(index);
    }

    public void setIndexTable(String index, String table) {
        INDEX_TABLE_MAP.put(index, table);
    }


    public static class ColumnMapping {
        public String name;
        public String targetName;
        public ISQLDataType dataType;
        public ISQLExpr defaultValue;

        public ColumnMapping() {

        }

        public ColumnMapping(String name, String targetName) {
            this.name = name;
            this.targetName = targetName;
        }

        public ColumnMapping(String name, String targetName, ISQLDataType dataType) {
            this.name = name;
            this.targetName = targetName;
            this.dataType = dataType;
        }

        public ColumnMapping(String name, String targetName, ISQLDataType dataType, ISQLExpr defaultValue) {
            this.name = name;
            this.targetName = targetName;
            this.dataType = dataType;
            this.defaultValue = defaultValue;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ColumnMapping that = (ColumnMapping) o;

            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }


    @Override
    public String toString() {
        return "SQLTransformConfig{" +
                "source=" + source +
                ", target=" + target +
                ", doubleQuoteAction=" + doubleQuoteAction +
                ", removeSchema=" + removeSchema +
                ", tableMappings=" + tableMappings +
                ", index=" + index +
                ", stmtList=" + stmtList +
                ", INDEX_TABLE_MAP=" + INDEX_TABLE_MAP +
                '}';
    }
}
