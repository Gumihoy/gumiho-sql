package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * [ { PCTFREE integer
 * | PCTUSED integer
 * | INITRANS integer
 * | storage_clause
 * }...
 * ]
 * <p>
 * physical_attributes_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/img_text/physical_attributes_clause.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2019-07-16.
 */
public interface ISQLPhysicalAttribute extends ISQLSegmentAttribute {
    @Override
    ISQLPhysicalAttribute clone();


    class SQLPctfree extends AbstractSQLExpr implements ISQLPhysicalAttribute {

        public ISQLExpr value;

        public SQLPctfree(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLPctfree clone() {
            ISQLExpr valueClone = value.clone();
            return new SQLPctfree(valueClone);
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    class SQLPctused extends AbstractSQLExpr implements ISQLPhysicalAttribute {

        public ISQLExpr value;

        public SQLPctused(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLPctused clone() {
            ISQLExpr valueClone = value.clone();
            return new SQLPctused(valueClone);
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    class SQLInitrans extends AbstractSQLExpr implements ISQLPhysicalAttribute {

        public ISQLExpr value;

        public SQLInitrans(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        @Override
        public SQLInitrans clone() {
            ISQLExpr valueClone = value.clone();
            return new SQLInitrans(valueClone);
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
