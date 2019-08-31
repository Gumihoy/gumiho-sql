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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * STORE AS OBJECT RELATIONAL
 * | STORE AS basicFileType? CLOB segName=expr? (LEFT_PAREN lobParameter* RIGHT_PAREN)?
 * | STORE AS basicFileType? BINARY XML segName=expr? (LEFT_PAREN lobParameter* RIGHT_PAREN)?
 * | STORE AS ALL VARRAYS AS (LOBS | TABLES)
 *
 * XMLType_storage
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/25.
 */
public interface ISQLXmlTypeStorage extends ISQLExpr {


    /**
     * STORE AS OBJECT RELATIONAL
     */
    class SQLXmlTypeStorageAsObjectRelational extends AbstractSQLExpr implements ISQLXmlTypeStorage {
        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLXmlTypeStorageAsObjectRelational clone() {
            return new SQLXmlTypeStorageAsObjectRelational();
        }
    }

    /**
     * STORE AS basicFileType? CLOB segName=expr? (LEFT_PAREN lobParameter* RIGHT_PAREN)?
     */
    class OracleSQLXmlTypeStorageAsClob extends AbstractSQLExpr implements ISQLXmlTypeStorage {

//        protected SQLBasicFileType fileType;
        protected ISQLExpr lobSegName;
        // LOB_parameters
        protected final List<ISQLLobParameter> parameters = new ArrayList<>();


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, lobSegName);
//                this.acceptChild(visitor, parameters);
//            }
        }

//        public SQLBasicFileType getFileType() {
//            return fileType;
//        }
//
//        public void setFileType(SQLBasicFileType fileType) {
//            this.fileType = fileType;
//        }

        public ISQLExpr getLobSegName() {
            return lobSegName;
        }

        public void setLobSegName(ISQLExpr lobSegName) {
            setChildParent(lobSegName);
            this.lobSegName = lobSegName;
        }

        public List<ISQLLobParameter> getParameters() {
            return parameters;
        }
        public void addParameter(ISQLLobParameter parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }
    }


    /**
     * STORE AS basicFileType? BINARY XMl segName=expr? (LEFT_PAREN lobParameter* RIGHT_PAREN)?
     */
    class OracleSQLXmlTypeStorageAsBinaryXml extends AbstractSQLExpr implements ISQLXmlTypeStorage {

//        protected SQLBasicFileType fileType;
        protected ISQLExpr lobSegName;
        // LOB_parameters
        protected final List<ISQLLobParameter> parameters = new ArrayList<>();


        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, lobSegName);
//                this.acceptChild(visitor, parameters);
//            }
        }
//        public SQLBasicFileType getFileType() {
//            return fileType;
//        }
//
//        public void setFileType(SQLBasicFileType fileType) {
//            this.fileType = fileType;
//        }

        public ISQLExpr getLobSegName() {
            return lobSegName;
        }

        public void setLobSegName(ISQLExpr lobSegName) {
            setChildParent(lobSegName);
            this.lobSegName = lobSegName;
        }

        public List<ISQLLobParameter> getParameters() {
            return parameters;
        }
        public void addParameter(ISQLLobParameter parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }
    }


    /**
     * STORE AS ALL VARRAYS AS (LOBS | TABLES)
     */
    class OracleSQLXmlTypeStorageAsAllVarrays extends AbstractSQLExpr implements ISQLXmlTypeStorage {

        private SQLStoreAllVarraysAsType type;

        public OracleSQLXmlTypeStorageAsAllVarrays(SQLStoreAllVarraysAsType type) {
            this.type = type;
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        public SQLStoreAllVarraysAsType getType() {
            return type;
        }

        public void setType(SQLStoreAllVarraysAsType type) {
            this.type = type;
        }
    }


    public enum SQLStoreAllVarraysAsType implements ISQLASTEnum {
        LOBS("lobs", "LOBS"),
        TABLES("tables","TABLES"),;

        public final String lower;
        public final String upper;


        SQLStoreAllVarraysAsType(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }

        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }

    enum LobCacheType {

//        CACHE(SQLReserved.CACHE),
//        NOCACHE(SQLReserved.NOCACHE),
//        CACHE_READS(SQLReserved.CACHE_READS);
//
//        public final SQLReserved name;
//
//        LobCacheType(SQLReserved name) {
//            this.name = name;
//        }
//
//        @Override
//        public SQLReserved getName() {
//            return name;
//        }
//
//        @Override
//        public String toString() {
//            return name.upper;
//        }
    }

}
