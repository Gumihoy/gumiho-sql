package com.gumihoy.sql.basic.ast.expr.datatype.spatial;

import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * GEOMETRYCOLLECTION
 * https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html
 *
 * @author kent on 2018/7/23.
 */
public class SQLGeometryCollectionDataType extends AbstractSQLDataType implements ISQLSpatialDataType {

    public SQLGeometryCollectionDataType() {
        super("GEOMETRYCOLLECTION");
    }


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SQLGeometryCollectionDataType clone() {
        return new SQLGeometryCollectionDataType();
    }
}
