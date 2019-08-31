package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.ast.expr.IOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * { [ ELEMENT ] IS OF [ TYPE ] ( ONLY type ) | [ NOT ] SUBSTITUTABLE AT ALL LEVELS }
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/22.
 */
public interface IOracleSubstitutableColumnClause extends IOracleExpr {
    @Override
    IOracleSubstitutableColumnClause clone();

    class OracleSQLSubstitutableColumnIsOFClause extends AbstractOracleExpr implements IOracleSubstitutableColumnClause {
        protected boolean element;
        protected boolean type;
        protected ISQLName onlyType;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, onlyType);
//            }

        }

        @Override
        public OracleSQLSubstitutableColumnIsOFClause clone() {
            OracleSQLSubstitutableColumnIsOFClause x = new OracleSQLSubstitutableColumnIsOFClause();
            x.element = this.element;
            x.type = this.type;
            ISQLName onlyTypeClone = this.onlyType.clone();
            x.setOnlyType(onlyTypeClone);
            return x;
        }

        public boolean isElement() {
            return element;
        }

        public void setElement(boolean element) {
            this.element = element;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public ISQLName getOnlyType() {
            return onlyType;
        }

        public void setOnlyType(ISQLName onlyType) {
            setChildParent(onlyType);
            this.onlyType = onlyType;
        }
    }

    /**
     * [ NOT ] SUBSTITUTABLE AT ALL LEVELS
     *
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
     */
    class OracleSQLSubstitutableColumnAtAllLevelsClause extends AbstractOracleExpr implements IOracleSubstitutableColumnClause {

        protected boolean not;

        @Override
        public void accept0(IOracleASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public OracleSQLSubstitutableColumnAtAllLevelsClause clone() {
            OracleSQLSubstitutableColumnAtAllLevelsClause x = new OracleSQLSubstitutableColumnAtAllLevelsClause();
            x.not = this.not;
            return x;
        }

        public boolean isNot() {
            return not;
        }

        public void setNot(boolean not) {
            this.not = not;
        }
    }
}
