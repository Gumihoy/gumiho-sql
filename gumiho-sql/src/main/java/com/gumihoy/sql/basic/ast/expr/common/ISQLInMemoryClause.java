package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * { INMEMORY [ inmemory_attributes ] } | { NO INMEMORY }
 * <p>
 * inmemory_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public interface ISQLInMemoryClause extends ISQLExpr{

    @Override
    ISQLInMemoryClause clone();


    /**
     * { INMEMORY [ inmemory_attributes ] } | { NO INMEMORY }
     * <p>
     * inmemory_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLInMemoryClause extends AbstractSQLExpr implements ISQLInMemoryClause {

        protected final List<ISQLInMemoryAttribute> attributes = new ArrayList<>();

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, inMemoryAttributes);
//            }
        }

        @Override
        public SQLInMemoryClause clone() {
            SQLInMemoryClause x = new SQLInMemoryClause();

//            if (this.inMemoryAttributes != null) {
//                OracleSQLInMemoryAttributes inMemoryAttributesClone = this.inMemoryAttributes.clone();
//                x.setInMemoryAttributes(inMemoryAttributesClone);
//            }
            return x;
        }

//        public OracleSQLInMemoryAttributes getInMemoryAttributes() {
//            return inMemoryAttributes;
//        }
//
//        public void setInMemoryAttributes(OracleSQLInMemoryAttributes inMemoryAttributes) {
//            setChildParent(inMemoryAttributes);
//            this.inMemoryAttributes = inMemoryAttributes;
//        }
    }


    /**
     * { INMEMORY [ inmemory_attributes ] } | { NO INMEMORY }
     * <p>
     * inmemory_clause
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/25.
     */
    public class SQLNoInMemoryClause extends AbstractSQLExpr implements ISQLInMemoryClause {

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            visitor.visit(this);
        }

        @Override
        public SQLNoInMemoryClause clone() {
            SQLNoInMemoryClause x = new SQLNoInMemoryClause();
            return x;
        }

    }
}
