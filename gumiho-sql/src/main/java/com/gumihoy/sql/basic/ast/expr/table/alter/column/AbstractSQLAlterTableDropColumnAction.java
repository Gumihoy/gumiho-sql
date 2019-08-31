package com.gumihoy.sql.basic.ast.expr.table.alter.column;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;

import java.util.ArrayList;
import java.util.List;

/**
 * DROP [ COLUMN ] <column name> <drop behavior>
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#drop%20column%20definition
 * <p>
 * DROP [COLUMN] col_name
 * https://dev.mysql.com/doc/refman/8.0/en/alter-table.html
 * <p>
 * DROP [ COLUMN ] [ IF EXISTS ] column_name [ RESTRICT | CASCADE ]
 * https://www.postgresql.org/docs/devel/static/sql-altertable.html
 * <p>
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/ALTER-TABLE.html#GUID-552E7373-BF93-477D-9DA3-B2C9386F2877
 *
 * @author kent on 2018/6/4.
 */
public abstract class AbstractSQLAlterTableDropColumnAction extends AbstractSQLExpr implements ISQLAlterTableDropColumnAction {

    protected final List<SQLOption> options = new ArrayList<>();
    protected boolean online;
    protected ISQLExpr checkPoint;

    @Override
    public AbstractSQLAlterTableDropColumnAction clone() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void cloneTo(AbstractSQLAlterTableDropColumnAction x) {
        super.cloneTo(x);

        for (SQLOption option : this.options) {
            x.addOption(option);
        }

        x.online = this.online;

        if (checkPoint != null) {
            ISQLExpr checkPointClone = this.checkPoint.clone();
            x.setCheckPoint(checkPointClone);
        }
    }


    public List<SQLOption> getOptions() {
        return options;
    }

    public void addOption(SQLOption option) {
        options.add(option);
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public ISQLExpr getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(ISQLExpr checkPoint) {
        this.checkPoint = checkPoint;
    }

    public enum SQLOption implements ISQLASTEnum {

        CASCADE_CONSTRAINTS("cascade constraints", "CASCADE CONSTRAINTS"),
        INVALIDATE("invalidate", "INVALIDATE");

        public final String lower;
        public final String upper;


        SQLOption(String lower, String upper) {
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
