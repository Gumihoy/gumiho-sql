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
package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLPhysicalAttribute;
import com.gumihoy.sql.basic.ast.expr.common.ISQLSegmentAttribute;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * physical_attributes_clause
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/3/16.
 */
public interface IOraclePhysicalAttribute extends IOracleExpr, ISQLPhysicalAttribute, ISQLSegmentAttribute {
    @Override
    IOraclePhysicalAttribute clone();

    /**
     * USAGE QUEUE
     */
    class OracleSQLUsageQueueClause extends AbstractOracleExpr implements IOraclePhysicalAttribute {
        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleSQLUsageQueueClause clone() {
            return new OracleSQLUsageQueueClause();
        }
    }

    /**
     * PCTFREE integer
     * <p>
     * physical_attributes_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/3/16.
     */
    class OraclePctfreeClause extends AbstractOracleExpr implements IOraclePhysicalAttribute {

        protected ISQLExpr value;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public OraclePctfreeClause clone() {
            OraclePctfreeClause x = new OraclePctfreeClause();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * PCTUSED integer
     * <p>
     * physical_attributes_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/3/16.
     */
    class OraclePctusedClause extends AbstractOracleExpr implements IOraclePhysicalAttribute {

        protected ISQLExpr value;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public OraclePctusedClause clone() {
            OraclePctusedClause x = new OraclePctusedClause();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * INITRANS integer
     * <p>
     * physical_attributes_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/3/16.
     */
    class OracleInitransClause extends AbstractOracleExpr implements IOraclePhysicalAttribute {

        protected ISQLExpr value;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public OracleInitransClause clone() {
            OracleInitransClause x = new OracleInitransClause();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * MAXTRANS integer
     * <p>
     * physical_attributes_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/3/16.
     */
    class OracleMaxTransClause extends AbstractOracleExpr implements IOraclePhysicalAttribute {

        protected ISQLExpr value;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, value);
//            }
        }

        @Override
        public OracleMaxTransClause clone() {
            OracleMaxTransClause x = new OracleMaxTransClause();
            ISQLExpr valueClone = this.value.clone();
            x.setValue(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }
}
