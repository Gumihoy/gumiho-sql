package com.gumihoy.sql.basic.ast.statement.ddl.packagebody;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLExceptionHandler;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ OR REPLACE ] [ EDITIONABLE | NONEDITIONABLE ] PACKAGE BODY  [ schema. ] package
 * { IS | AS }
 * declare_section
 * [ initialize_section ]
 * END [ package_name ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-PACKAGE-statement.html#GUID-03A70A54-90FF-4293-B6B8-F0B35E184AC5
 *
 * @author kent on 2018/5/31.
 */
public class SQLCreatePackageBodyStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLEditionAbleType editionAbleType;

    protected ISQLName name;

    protected SQLASType as = SQLASType.AS;

    protected final List<ISQLExpr> declareSections = new ArrayList<>();

    protected final List<SQLLabelStatement> statements = new ArrayList<>();

    protected final List<SQLExceptionHandler> exceptionHandlers = new ArrayList<>();

    protected ISQLName endName;

    public SQLCreatePackageBodyStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, statements);
            this.acceptChild(visitor, exceptionHandlers);
            this.acceptChild(visitor, endName);
        }
    }

    @Override
    public SQLCreatePackageBodyStatement clone() {
        SQLCreatePackageBodyStatement x = new SQLCreatePackageBodyStatement(this.dbType);
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLCreatePackageBodyStatement x) {
        super.cloneTo(x);
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name) {
            this.setName((ISQLName) target);
            return true;
        }

        if (source == endName) {
            this.setEndName((ISQLName) target);
            return true;
        }
        return false;
    }

    @Override
    public SQLObjectType getObjectType() {
        return SQLObjectType.PACKAGE_BODY_CREATE;
    }



    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public SQLEditionAbleType getEditionAbleType() {
        return editionAbleType;
    }

    public void setEditionAbleType(SQLEditionAbleType editionAbleType) {
        this.editionAbleType = editionAbleType;
    }

    public ISQLName getName() {
        return name;
    }

    public String getPackageName() {
        return name.getSimpleName();
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
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

    public List<SQLExceptionHandler> getExceptionHandlers() {
        return exceptionHandlers;
    }

    public void addExceptionHandler(SQLExceptionHandler exceptionHandler) {
        if (exceptionHandler == null) {
            return;
        }
        setChildParent(exceptionHandler);
        this.exceptionHandlers.add(exceptionHandler);
    }

    public ISQLName getEndName() {
        return endName;
    }

    public void setEndName(ISQLName endName) {
        this.endName = endName;
    }
}
