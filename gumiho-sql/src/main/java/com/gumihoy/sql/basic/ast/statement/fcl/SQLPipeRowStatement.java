package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * PIPE ROW ( row ) ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/PIPE-ROW-statement.html#GUID-AD2713A9-062A-42DD-B49E-804C6120378B
 *
 * @author kent on 2018/6/14.
 */
public class SQLPipeRowStatement extends AbstractSQLStatement {

    protected ISQLExpr row;

    public SQLPipeRowStatement(DBType dbType) {
        super(dbType);
    }

    public SQLPipeRowStatement(DBType dbType, ISQLExpr row) {
        super(dbType);
        setRow(row);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, row);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if(source == row) {
            setRow(target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.PIPE_ROW;
    }


    public ISQLExpr getRow() {
        return row;
    }

    public void setRow(ISQLExpr row) {
        setChildParent(row);
        this.row = row;
    }
}
