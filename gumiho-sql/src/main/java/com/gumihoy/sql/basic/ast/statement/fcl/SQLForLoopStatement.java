package com.gumihoy.sql.basic.ast.statement.fcl;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * [ <<label>> ]
 * FOR target IN query LOOP
 * statements
 * END LOOP [ label ];
 * https://www.postgresql.org/docs/current/static/plpgsql-control-structures.html
 * <p>
 * FOR index IN [ REVERSE ] [inExpr] LOOP
 * statement... END LOOP [ label ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/FOR-LOOP-statement.html#GUID-D00F8F0B-ECFC-48B6-B399-D8B5114E7E21
 *
 * @author kent onCondition 2018/4/4.
 */
public class SQLForLoopStatement extends AbstractSQLStatement {

    protected ISQLName name;

    protected boolean reverse;

    protected ISQLExpr lower;
    protected ISQLExpr upper;

    protected ISQLExpr by;

    protected final List<SQLLabelStatement> statements = new ArrayList<>();

    protected ISQLName endLabel;

    public SQLForLoopStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, lower);
            this.acceptChild(visitor, upper);
            this.acceptChild(visitor, by);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, endLabel);
        }
    }

    @Override
    public SQLForLoopStatement clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (source == endLabel
                && target instanceof ISQLName) {
            setEndLabel((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return null;//SQLObjectType.FOR_LOOP;
    }


    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public ISQLExpr getLower() {
        return lower;
    }

    public void setLower(ISQLExpr lower) {
        setChildParent(lower);
        this.lower = lower;
    }

    public ISQLExpr getUpper() {
        return upper;
    }

    public void setUpper(ISQLExpr upper) {
        setChildParent(upper);
        this.upper = upper;
    }

    public ISQLExpr getBy() {
        return by;
    }

    public void setBy(ISQLExpr by) {
        setChildParent(by);
        this.by = by;
    }

    public List<SQLLabelStatement> getStatements() {
        return statements;
    }

    public void addStatement(SQLLabelStatement statement) {
        if (statement == null) {
            return;
        }
        setChildParent(statement);
        this.statements.add(statement);
    }

    public ISQLName getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(ISQLName endLabel) {
        setChildParent(endLabel);
        this.endLabel = endLabel;
    }
}
