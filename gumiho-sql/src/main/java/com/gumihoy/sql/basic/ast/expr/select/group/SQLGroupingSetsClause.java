package com.gumihoy.sql.basic.ast.expr.select.group;


import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * grouping_sets_clause : GROUPING SETS <left paren> <grouping set list> <right paren>
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#grouping%20sets%20specification
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/SELECT.html#GUID-CFA006CA-6FF1-4972-821E-6996142A51C6
 *
 * @author kent on 2018/5/2.
 */
public class SQLGroupingSetsClause extends AbstractSQLExpr {

    protected final List<ISQLExpr> elements = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {

    }

    public List<ISQLExpr> getElements() {
        return elements;
    }

    public void addElement(ISQLExpr element) {
        if (element == null) {
            return;
        }
        setChildParent(element);
        this.elements.add(element);
    }


}
