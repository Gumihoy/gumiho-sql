package com.gumihoy.sql.dialect.oracle.visitor;

import com.gumihoy.sql.basic.ast.expr.common.SQLParameterDeclaration;
import com.gumihoy.sql.basic.ast.expr.common.SQLWithClause;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;

/**
 * @author kent on 2019-06-14.
 */
public class OracleASTOutputVisitor extends SQLASTOutputVisitor implements IOracleASTVisitor {

    public OracleASTOutputVisitor(StringBuilder appender) {
        super(appender);
    }

    public OracleASTOutputVisitor(StringBuilder appender, SQLOutputConfig config) {
        super(appender, config);
    }


    // ---------------------------- Common Start ------------------------------------


    @Override
    public boolean visit(SQLParameterDeclaration x) {
        printAccept(x.getName());

        printSpaceAfterValue(x.getParameterModel());

        if (x.isNoCopy()) {
            printSpaceAfterValue(SQLKeyWord.NOCOPY);
        }

        printSpaceAfterAccept(x.getDataType());
        printSpaceAfterAccept(x.getDefaultClause());

        return false;
    }


    // ---------------------------- Common Start ------------------------------------


    // ---------------------------- Table expr Start ------------------------------------


    // ---------------------------- Table expr Start ------------------------------------


    // ---------------------------- Select expr Start ------------------------------------

    @Override
    public boolean visit(OracleSelectQuery x) {
        SQLWithClause withClause = x.getWithClause();
        if (withClause != null) {
            withClause.accept(this);
            println();
        }

        print(SQLKeyWord.SELECT);

        printSpaceAfterValue(x.getSetQuantifier());

        printSpace();
        printSelectItems(x.getSelectItems());

        if (x.isBulkCollect()) {
            printSpaceAfterValue(isLowerCase() ? "bulk collect" : "BULK COLLECT");
        }
        printIndentLnSelectTargetItems(x.getSelectTargetItems());

        printlnAndAccept(x.getFromClause());

        printlnAndAccept(x.getWhereClause());

        printlnAndAccept(x.getHierarchicalQueryClause());

        printlnAndAccept(x.getGroupByClause());

//        printlnAndAccept(x.getModelClause());

        printlnAndAccept(x.getOrderByClause());

        printlnAndAccept(x.getLimitClause());

        printlnAndAccept(x.getLockClause());

        return false;
    }


    // ---------------------------- Select expr End ------------------------------------


}
