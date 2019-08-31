package com.gumihoy.sql.basic.ast.expr.datatype.string;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.AbstractSQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;

/**
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#predefined%20type
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/string-type-overview.html
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483
 *
 * @author kent on 2018/4/19.
 */
public abstract class AbstractSQLStringDataType extends AbstractSQLDataType implements ISQLStringDataType {


    public AbstractSQLStringDataType() {
    }

    public AbstractSQLStringDataType(String name) {
        super(name);
    }

    public AbstractSQLStringDataType(ISQLExpr... arguments) {
        super(arguments);
    }

    public AbstractSQLStringDataType(String name, ISQLExpr... arguments) {
        super(name, arguments);
    }

    public AbstractSQLStringDataType(ISQLName name) {
        super(name);
    }

    @Override
    public String getCharacterSetName() {
        return null;//characterSetExpr.getValue().toString();
    }

    @Override
    public String getCollationName() {
        return null;//collateClause.getValue().toString();
    }

//    public SQLCharacterSetOptionExpr getCharacterSetExpr() {
//        return characterSetExpr;
//    }
//
//    public void setCharacterSetExpr(SQLCharacterSetOptionExpr characterSetExpr) {
//        setChildParent(characterSetExpr);
//        this.characterSetExpr = characterSetExpr;
//    }
//
//    public SQLCollateOptionExpr getCollateClause() {
//        return collateClause;
//    }
//
//    public void setCollateClause(SQLCollateOptionExpr collateClause) {
//        setChildParent(collateClause);
//        this.collateClause = collateClause;
//    }




}
