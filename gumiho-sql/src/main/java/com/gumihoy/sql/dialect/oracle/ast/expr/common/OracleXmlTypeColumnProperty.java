package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLXmlTypeStorage;
import com.gumihoy.sql.basic.ast.expr.common.SQLXmlSchemaSpec;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

/**
 * XMLTYPE [ COLUMN ] column [ XMLType_storage ] [ XMLSchema_spec ]
 * <p>
 * <p>
 * XMLType_column_properties
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-MATERIALIZED-VIEW.html#GUID-EE262CA4-01E5-4618-B659-6165D993CA1B
 *
 * @author kent on 2018/6/22.
 */
public class OracleXmlTypeColumnProperty extends AbstractOracleExpr implements IOracleColumnProperty {

    protected boolean column;
    protected ISQLExpr name;
    protected ISQLXmlTypeStorage xmlTypeStorage;
    protected SQLXmlSchemaSpec xmlSchemaSpec;


    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, name);
//            this.acceptChild(visitor, substitutableColumnClause);
//        }
    }

    @Override
    public OracleXmlTypeColumnProperty clone() {
        OracleXmlTypeColumnProperty x = new OracleXmlTypeColumnProperty();

        return x;
    }


    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public ISQLExpr getName() {
        return name;
    }

    public void setName(ISQLExpr name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLXmlTypeStorage getXmlTypeStorage() {
        return xmlTypeStorage;
    }

    public void setXmlTypeStorage(ISQLXmlTypeStorage xmlTypeStorage) {
        setChildParent(xmlTypeStorage);
        this.xmlTypeStorage = xmlTypeStorage;
    }

    public SQLXmlSchemaSpec getXmlSchemaSpec() {
        return xmlSchemaSpec;
    }

    public void setXmlSchemaSpec(SQLXmlSchemaSpec xmlSchemaSpec) {
        setChildParent(xmlSchemaSpec);
        this.xmlSchemaSpec = xmlSchemaSpec;
    }
}
