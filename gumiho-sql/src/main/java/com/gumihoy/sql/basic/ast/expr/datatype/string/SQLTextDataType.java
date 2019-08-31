package com.gumihoy.sql.basic.ast.expr.datatype.string;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * TEXT[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]
 * <p>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#predefined%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/string-type-overview.html
 *
 * @author kent on 2018/4/19.
 */
public class SQLTextDataType extends AbstractSQLStringDataType {


//    public SQLTextDataType() {
//        super(SQLReserved.TEXT.ofExpr());
//    }

    public static SQLTextDataType of() {
        return new SQLTextDataType();
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, characterSetExpr);
//            this.acceptChild(visitor, collateClause);
        }
    }


}
