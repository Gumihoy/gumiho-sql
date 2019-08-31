package com.gumihoy.sql.basic.ast.expr.trigger;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLBody;
import com.gumihoy.sql.basic.ast.expr.common.SQLExceptionHandler;
import com.gumihoy.sql.basic.ast.expr.common.SQLLabelStatement;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * COMPOUND TRIGGER [ declare_section ] timing_point_section [ timing_point_section ]... END [ trigger ] ;
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html#GUID-AF9E33F1-64D1-4382-A6A4-EC33C36F237B
 *
 * @author kent on 2018/6/15.
 */
public class SQLTriggerCompoundTriggerBlock extends AbstractSQLExpr {
    protected final List<ISQLExpr> declareSections = new ArrayList<>();
    protected final List<SQLTimingPointSection> timingPointSections = new ArrayList<>();
    protected ISQLName endName;

    public SQLTriggerCompoundTriggerBlock() {
        setAfterSemi(true);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, declareSections);
            this.acceptChild(visitor, timingPointSections);
            this.acceptChild(visitor, endName);
        }
    }

    public List<ISQLExpr> getDeclareSections() {
        return declareSections;
    }

    public void addDeclareSection(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.declareSections.add(item);
    }

    public List<SQLTimingPointSection> getTimingPointSections() {
        return timingPointSections;
    }

    public void addTimingPointSection(SQLTimingPointSection timingPointSection) {
        if (timingPointSection == null) {
            return;
        }
        setChildParent(timingPointSection);
        this.timingPointSections.add(timingPointSection);
    }

    public ISQLName getEndName() {
        return endName;
    }

    public void setEndName(ISQLName endName) {
        setChildParent(endName);
        this.endName = endName;
    }

    /**
     * timing_point IS BEGIN tps_body END timing_point ;
     * <p>
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-TRIGGER-statement.html#GUID-AF9E33F1-64D1-4382-A6A4-EC33C36F237B
     */
    public static class SQLTimingPointSection extends AbstractSQLExpr {

        protected SQLTimingPoint timingPoint;
        protected final List<SQLLabelStatement> statements = new ArrayList<>();
        protected final List<SQLExceptionHandler> exceptionHandlers = new ArrayList<>();
        protected SQLTimingPoint endTimingPoint;

        public SQLTimingPointSection() {
            setAfterSemi(true);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, statements);
                this.acceptChild(visitor, exceptionHandlers);
            }
        }

        @Override
        public SQLTimingPointSection clone() {
            SQLTimingPointSection x = new SQLTimingPointSection();

            x.timingPoint = this.timingPoint;

//            SQLBody bodyClone = body.clone();
//            x.setBody(bodyClone);

            return x;
        }

        public SQLTimingPoint getTimingPoint() {
            return timingPoint;
        }

        public void setTimingPoint(SQLTimingPoint timingPoint) {
            this.timingPoint = timingPoint;
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

        public SQLTimingPoint getEndTimingPoint() {
            return endTimingPoint;
        }

        public void setEndTimingPoint(SQLTimingPoint endTimingPoint) {
            this.endTimingPoint = endTimingPoint;
        }



    }


    public enum SQLTimingPoint implements ISQLASTEnum {

        BEFORE_STATEMENT("BEFORE STATEMENT", "BEFORE STATEMENT"),
        BEFORE_EACH_ROW("BEFORE EACH ROW", "BEFORE EACH ROW"),
        AFTER_STATEMENT("AFTER STATEMENT", "AFTER STATEMENT"),
        AFTER_EACH_ROW("AFTER EACH ROW", "AFTER EACH ROW"),
        INSTEAD_OF_EACH_ROW("INSTEAD OF EACH ROW", "INSTEAD OF EACH ROW"),;

        public final String lower;
        public final String upper;

        SQLTimingPoint(String lower, String upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return upper;
        }


        @Override
        public String lower() {
            return lower;
        }

        @Override
        public String upper() {
            return upper;
        }
    }
}
