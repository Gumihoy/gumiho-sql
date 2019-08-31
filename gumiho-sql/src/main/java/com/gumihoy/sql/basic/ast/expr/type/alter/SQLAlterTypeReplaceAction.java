package com.gumihoy.sql.basic.ast.expr.type.alter;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * REPLACE
 * [ invoker_rights_clause [ accessible_by_clause ] |
 *   accessible_by_clause | invoker_rights_clause ] ]
 * AS OBJECT  ( attribute datatype [, attribute datatype ]...
 * [, element_spec [, element_spec ]... ] )
 * <p>
 *
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/img_text/type_replace_clause.html
 *
 * @author kent on 2018/9/11.
 */
public class SQLAlterTypeReplaceAction extends AbstractSQLExpr implements ISQLAlterTypeAction {

    protected final List<ISQLExpr> properties = new ArrayList<>();
    protected final List<ISQLExpr> parameters = new ArrayList<>();


    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, properties);
            this.acceptChild(visitor, parameters);
        }
    }

    @Override
    public SQLAlterTypeReplaceAction clone() {
        SQLAlterTypeReplaceAction x = new SQLAlterTypeReplaceAction();
        return x;
    }

    public List<ISQLExpr> getProperties() {
        return properties;
    }

    public void addProperty(ISQLExpr property) {
        if (property == null) {
            return;
        }
        setChildParent(property);
        this.properties.add(property);
    }

    public List<ISQLExpr> getParameters() {
        return parameters;
    }

    public void addParameter(ISQLExpr parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        this.parameters.add(parameter);
    }



}
