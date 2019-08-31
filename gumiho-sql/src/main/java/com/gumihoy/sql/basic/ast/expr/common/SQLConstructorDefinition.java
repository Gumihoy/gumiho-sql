package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [ FINAL ] [ INSTANTIABLE ] CONSTRUCTOR FUNCTION datatype
 * [ [ SELF IN OUT datatype, ]
 * parameter datatype [, parameter datatype ]...
 * ]
 * RETURN SELF AS RESULT
 * { IS | AS } { [ declare_section ] body | call_spec }
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/element-specification.html#GUID-20D95D8A-5C17-4C89-9AAB-1852CDB57CE2
 *
 * @author kent on 2018/6/17.
 */
public class SQLConstructorDefinition extends SQLConstructorHeading {

    protected SQLASType as = SQLASType.AS;

    protected final List<ISQLExpr> declareSections = new ArrayList<>();

    protected ISQLExpr asExpr;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, dataType);
//            this.acceptChild(visitor, selfInOutDataType);
//            this.acceptChild(visitor, parameters);
//            this.acceptChild(visitor, declareSections);
//            this.acceptChild(visitor, asExpr);
//        }
    }


    public SQLASType getAs() {
        return as;
    }

    public void setAs(SQLASType as) {
        this.as = as;
    }

    public List<ISQLExpr> getDeclareSections() {
        return declareSections;
    }

    public void addDeclareSection(ISQLExpr declareSection) {
        if (declareSection == null) {
            return;
        }
        setChildParent(declareSection);
        this.declareSections.add(declareSection);
    }


    public ISQLExpr getAsExpr() {
        return asExpr;
    }

    public void setAsExpr(ISQLExpr asExpr) {
        setChildParent(asExpr);
        this.asExpr = asExpr;
    }
}
