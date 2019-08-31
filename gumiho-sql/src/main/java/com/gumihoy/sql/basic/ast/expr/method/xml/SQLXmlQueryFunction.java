package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLQUERY( XQuery_string [ XML_passing_clause ] RETURNING CONTENT [NULL ON EMPTY])
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLQUERY.html#GUID-9E8D3220-2CF5-4C63-BDC2-0526D57B9CDB
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlQueryFunction extends AbstractSQLFunction {

    protected SQLXmlPassingClause passingClause;

    protected boolean nullOnEmpty;

    public SQLXmlQueryFunction() {
//        super(SQLReserved.XMLCAST.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, passingClause);
//        }
    }

    public SQLXmlPassingClause getPassingClause() {
        return passingClause;
    }

    public void setPassingClause(SQLXmlPassingClause passingClause) {
        setChildParent(passingClause);
        this.passingClause = passingClause;
    }

    public boolean isNullOnEmpty() {
        return nullOnEmpty;
    }

    public void setNullOnEmpty(boolean nullOnEmpty) {
        this.nullOnEmpty = nullOnEmpty;
    }
}
