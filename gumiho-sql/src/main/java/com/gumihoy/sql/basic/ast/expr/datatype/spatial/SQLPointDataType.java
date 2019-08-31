package com.gumihoy.sql.basic.ast.expr.datatype.spatial;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * POINT
 * https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html
 *
 * @author kent on 2018/7/23.
 */
public class SQLPointDataType extends AbstractSQLDataType implements ISQLSpatialDataType {

    public SQLPointDataType() {
        super("POINT");
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLPointDataType clone() {
        return new SQLPointDataType();
    }
}
