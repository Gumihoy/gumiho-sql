package com.gumihoy.sql.basic.ast.expr.datatype.string;

import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * ENUM('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]
 * <p>
 * https://dev.mysql.com/doc/refman/8.0/en/string-type-overview.html
 *
 * @author kent on 2018/6/17.
 */
public class SQLEnumDataType extends AbstractSQLStringDataType {

//    public SQLEnumDataType() {
//        super(SQLReserved.ENUM.ofExpr());
//    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, arguments);
//            this.acceptChild(visitor, characterSetExpr);
//            this.acceptChild(visitor, collateClause);
        }
    }

    @Override
    public SQLEnumDataType clone() {
        SQLEnumDataType x = new SQLEnumDataType();
        this.cloneTo(x);
        return x;
    }
}
