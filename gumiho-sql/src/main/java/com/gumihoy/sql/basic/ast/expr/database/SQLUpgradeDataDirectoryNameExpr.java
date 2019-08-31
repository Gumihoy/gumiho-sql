package com.gumihoy.sql.basic.ast.expr.database;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * UPGRADE DATA DIRECTORY NAME
 * <p>
 * https://dev.mysql.com/doc/refman/5.6/en/alter-database.html
 *
 * @author kent on 2019-07-11.
 */
public class SQLUpgradeDataDirectoryNameExpr extends AbstractSQLExpr {

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
