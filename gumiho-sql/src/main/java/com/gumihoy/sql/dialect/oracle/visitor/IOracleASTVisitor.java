package com.gumihoy.sql.dialect.oracle.visitor;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;

/**
 * @author kent on 2019-06-14.
 */
public interface IOracleASTVisitor extends ISQLASTVisitor {




    // ---------------------------- Common Start ------------------------------------

    // ---------------------------- Common Start ------------------------------------





    // ---------------------------- Table expr Start ------------------------------------

    // ---------------------------- Table expr Start ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------

    boolean visit(OracleSelectQuery x);

    // ---------------------------- Select expr End ------------------------------------

}
