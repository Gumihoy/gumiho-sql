package com.gumihoy.sql.basic.ast.expr.sequence;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * https://www.postgresql.org/docs/current/static/sql-createsequence.html
 *
 * @author kent onCondition 2018/4/2.
 */
public class SQLSetSchemaOption extends AbstractSQLExpr {

    protected ISQLExpr setSchema;

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, setSchema);
//        }
    }

    @Override
    public SQLSetSchemaOption clone() {
        SQLSetSchemaOption x = new SQLSetSchemaOption();
        ISQLExpr setSchemaClone = this.setSchema.clone();
        x.setSetSchema(setSchemaClone);
        return x;
    }


    public ISQLExpr getSetSchema() {
        return setSchema;
    }

    public void setSetSchema(ISQLExpr setSchema) {
        this.setSchema = setSchema;
    }
}
