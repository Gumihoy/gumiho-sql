package com.gumihoy.sql.dialect.mysql.ast.statement.dml;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.dml.SQLInsertStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.IMySQLASTVisitor;
import com.gumihoy.sql.enums.DBType;

import java.util.List;

/**
 * @author kent on 2019-07-03.
 */
public class MySQLInsertStatement extends SQLInsertStatement implements IMySQLDMLStatement {

    public MySQLInsertStatement() {
        super(DBType.MySQL);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor instanceof IMySQLASTVisitor) {
            accept0((IMySQLASTVisitor) visitor);
        } else {
            throw new IllegalArgumentException("not support visitor type : " + visitor.getClass().getName());
        }
    }

    @Override
    public void accept0(IMySQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, withClause);
            this.acceptChild(visitor, tableReference);
            this.acceptChild(visitor, columns);
            this.acceptChild(visitor, valuesClause);
//            this.acceptChild(visitor, returningClause);
//            this.acceptChild(visitor, errorLoggingClause);
        }
    }


    @Override
    public <T extends ISQLExpr> boolean replaceInList(List<T> exprList, ISQLExpr source, T target, ISQLObject parent) {
        return false;
    }
}
