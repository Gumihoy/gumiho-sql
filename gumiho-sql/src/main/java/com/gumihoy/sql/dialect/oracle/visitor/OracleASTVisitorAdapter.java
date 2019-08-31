package com.gumihoy.sql.dialect.oracle.visitor;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.visitor.SQLASTVisitorAdapter;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;

/**
 * @author kent on 2019-06-14.
 */
public class OracleASTVisitorAdapter extends SQLASTVisitorAdapter implements IOracleASTVisitor {


    @Override
    public void preVisit(ISQLObject x) {

    }

    @Override
    public void postVisit(ISQLObject x) {

    }



    // ---------------------------- Common Start ------------------------------------



    // ---------------------------- Common Start ------------------------------------



    // ---------------------------- Table expr Start ------------------------------------


    // ---------------------------- Table expr Start ------------------------------------



    // ---------------------------- Select expr Start ------------------------------------

    @Override
    public boolean visit(OracleSelectQuery x) {
        return true;
    }


    // ---------------------------- Select expr End ------------------------------------
}
