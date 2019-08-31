package com.gumihoy.sql.basic.ast.expr.method.xml;

import com.gumihoy.sql.basic.ast.expr.method.AbstractSQLFunction;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * XMLEXISTS ( XQuery_string [ XML_passing_clause ] )
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/XMLCAST.html#GUID-06563B93-1247-4F0C-B6BE-42DB3B1DB069
 *
 * @author kent on 2018/5/24.
 */
public class SQLXmlExistsFunction extends AbstractSQLFunction {

    protected SQLXmlPassingClause passingClause;


    public SQLXmlExistsFunction() {
//        super(SQLReserved.XMLEXISTS.ofExpr());
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, arguments);
//        }
    }

    public SQLXmlPassingClause getPassingClause() {
        return passingClause;
    }

    public void setPassingClause(SQLXmlPassingClause passingClause) {
        setChildParent(passingClause);
        this.passingClause = passingClause;
    }
}
