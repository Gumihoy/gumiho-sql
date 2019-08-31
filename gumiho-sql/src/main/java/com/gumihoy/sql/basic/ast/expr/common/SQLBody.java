package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * [begin_label:] BEGIN
 * [statement_list]
 * END [end_label]
 * https://dev.mysql.com/doc/refman/5.7/en/begin-end.html
 * <p>
 * <p>
 * [ <<label>> ]
 * [ DECLARE declarations ]
 * BEGIN statements
 * END [ label ];
 * https://www.postgresql.org/docs/devel/static/plpgsql-structure.html
 * <p>
 * <p>
 * BEGIN statement ...
 * [ EXCEPTION exception_handler [ exception_handler ]... ]
 * END [ name ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/block.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
 *
 * @author kent onCondition 2018/4/3.
 */
public class SQLBody extends AbstractSQLExpr {

    protected ISQLName beginLabel;

    protected final List<SQLLabelStatement> statements = new ArrayList<>();

    protected final List<SQLExceptionHandler> exceptionHandlers = new ArrayList<>();

    protected ISQLName endName;

    public SQLBody() {
        setAfterSemi(true);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, beginLabel);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, exceptionHandlers);
            this.acceptChild(visitor, endName);
        }
    }

    @Override
    public SQLBody clone() {
        SQLBody x = new SQLBody();
        this.cloneTo(x);
        return x;
    }


    public void cloneTo(SQLBody x) {
        super.cloneTo(x);

        if (this.beginLabel != null) {
            ISQLName beginLabelClone = this.beginLabel.clone();
            x.setBeginLabel(beginLabelClone);
        }

        for (SQLLabelStatement statement : this.statements) {
            SQLLabelStatement statementClone = statement.clone();
            x.addStatement(statementClone);
        }

        for (SQLExceptionHandler exceptionHandler : exceptionHandlers) {
            SQLExceptionHandler exceptionHandlerClone = exceptionHandler.clone();
            x.addExceptionHandler(exceptionHandlerClone);
        }

        if (this.endName != null) {
            ISQLName endNameClone = this.endName.clone();
            x.setEndName(endNameClone);
        }
    }


    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (target == null) {

            if (source == beginLabel) {
                setBeginLabel(null);
                return true;
            }

            boolean replace = replaceInList(statements, source, null, this);
            if (replace) {
                return true;
            }

            if (source == endName) {
                setEndName(null);
                return true;
            }

            return false;

        }

        if (source == beginLabel
                && target instanceof ISQLName) {
            setBeginLabel((ISQLName) target);
            return true;
        }

        if (source == endName
                && target instanceof ISQLName) {
            setEndName((ISQLName) target);
            return true;
        }

        return false;
    }

    public ISQLName getBeginLabel() {
        return beginLabel;
    }

    public SQLBody setBeginLabel(ISQLName beginLabel) {
        setChildParent(beginLabel);
        this.beginLabel = beginLabel;
        return this;
    }

    public List<SQLLabelStatement> getStatements() {
        return statements;
    }

    public SQLBody addStatement(SQLLabelStatement statement) {
        if (statement == null) {
            return this;
        }
        setChildParent(statement);
        this.statements.add(statement);
        return this;
    }


    public List<SQLExceptionHandler> getExceptionHandlers() {
        return exceptionHandlers;
    }

    public SQLBody addExceptionHandler(SQLExceptionHandler exceptionHandler) {
        if (exceptionHandler == null) {
            return this;
        }
        setChildParent(exceptionHandler);
        this.exceptionHandlers.add(exceptionHandler);
        return this;
    }

    public ISQLName getEndName() {
        return endName;
    }

    public void setEndName(ISQLName endName) {
        setChildParent(endName);
        this.endName = endName;
    }


}
