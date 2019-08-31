package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

/**
 * OPEN expr FOR expr using_clause?
 *
 * @author kent on 2018/6/9.
 */
public class SQLOpenForStatement extends AbstractSQLStatement {

    protected ISQLExpr open;
    protected ISQLExpr for_;
//    protected SQLUsingClause usingClause;


    public SQLOpenForStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, open);
            this.acceptChild(visitor, for_);
//            this.acceptChild(visitor, usingClause);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == open) {
            setOpen(target);
            return true;
        }
        if (source == for_) {
            setFor_(target);
            return true;
        }

        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.OPEN_FOR;
    }



    public ISQLExpr getOpen() {
        return open;
    }

    public void setOpen(ISQLExpr open) {
        setChildParent(open);
        this.open = open;
    }

    public ISQLExpr getFor_() {
        return for_;
    }

    public void setFor_(ISQLExpr for_) {
        setChildParent(for_);
        this.for_ = for_;
    }

//    public SQLUsingClause getUsingClause() {
//        return usingClause;
//    }
//
//    public void setUsingClause(SQLUsingClause usingClause) {
//        setChildParent(usingClause);
//        this.usingClause = usingClause;
//    }



}
