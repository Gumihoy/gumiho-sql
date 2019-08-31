package com.gumihoy.sql.basic.ast.expr.table.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ALLOW ANYSCHEMA
 * | ALLOW NONSCHEMA
 * | DISALLOW NONSCHEMA
 * <p>
 * alter_XMLSchema_clause
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/7/18.
 */
public interface SQLAlterXmlSchemaClause extends ISQLExpr {

    @Override
    SQLAlterXmlSchemaClause clone();

    /**
     * ALLOW ANYSCHEMA
     */
    class SQLAlterXmlSchemaAllowAnySchemaClause extends AbstractSQLExpr implements SQLAlterXmlSchemaClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLAlterXmlSchemaAllowAnySchemaClause clone() {
            return new SQLAlterXmlSchemaAllowAnySchemaClause();
        }
    }

    /**
     * ALLOW NONSCHEMA
     */
    class SQLAlterXmlSchemaAllowNonSchemaClause extends AbstractSQLExpr implements SQLAlterXmlSchemaClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLAlterXmlSchemaAllowNonSchemaClause clone() {
            return new SQLAlterXmlSchemaAllowNonSchemaClause();
        }
    }

    /**
     * DISALLOW NONSCHEMA
     */
    class SQLAlterXmlSchemaDisallowNonSchemaClause extends AbstractSQLExpr implements SQLAlterXmlSchemaClause {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLAlterXmlSchemaDisallowNonSchemaClause clone() {
            return new SQLAlterXmlSchemaDisallowNonSchemaClause();
        }
    }
}
