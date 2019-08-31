package com.gumihoy.sql.dialect.oracle.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLLobParameter;
import com.gumihoy.sql.basic.ast.expr.common.ISQLLobStorageParameter;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.dialect.oracle.ast.expr.AbstractOracleExpr;
import com.gumihoy.sql.dialect.oracle.visitor.IOracleASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * LOB LEFT_PAREN nameIdentifier (COMMA nameIdentifier)* RIGHT_PAREN STORE AS lobStorageClauseParameter+
 * lobStorageClauseParameter: SECUREFILE | BASICFILE | lob_segname | LEFT_PAREN lobStorageParameter+ RIGHT_PAREN
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public class OracleLobStorageClause extends AbstractOracleExpr {

    protected final List<ISQLExpr> items = new ArrayList<>();
    protected final List<ISQLExpr> parameters = new ArrayList<>();

    @Override
    public void accept0(IOracleASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, items);
//            this.acceptChild(visitor, parameters);
//        }
    }

    @Override
    public OracleLobStorageClause clone() {
        super.clone();
        return null;
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        boolean replace = replaceInList(items, source, target, this);
        if (replace) {
            return true;
        }

        replace = replaceInList(parameters, source, target, this);
        if (replace) {
            return true;
        }

        return false;
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        items.add(item);
    }

    public List<ISQLExpr> getParameters() {
        return parameters;
    }

    public void addParameter(ISQLExpr parameter) {
        if (parameter == null) {
            return;
        }
        setChildParent(parameter);
        parameters.add(parameter);
    }



    public static class SQLLobStorageParameters extends AbstractSQLExpr {
        protected final List<ISQLLobStorageParameter> parameters = new ArrayList<>();
        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }
        public List<ISQLLobStorageParameter> getParameters() {
            return parameters;
        }

        public void addParameter(ISQLLobStorageParameter parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            parameters.add(parameter);
        }
    }
}
