package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.enums.SQLCharSizeUnit;
import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * GENERATED { ALWAYS | BY DEFAULT } AS IDENTITY [ <left paren> <common sequence generator options> <right paren> ]
 * https://ronsavage.github.io/SQL/sql-2003-2.bnf.html#identity%20column%20specification
 * <p>
 * <p>
 * GENERATED [ ALWAYS | BY DEFAULT [ ON NULL ] ] AS IDENTITY [ ( identity_options ) ]
 * <p>
 * identity_clause
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/28.
 */
public class SQLIdentityClause extends AbstractSQLExpr {

//    protected IdentityAction action;

    protected final List<ISQLIdentityOption> options = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
//        if (visitor.visit(this)) {
//            this.acceptChild(visitor, options);
//        }
    }

    @Override
    public SQLIdentityClause clone() {
        SQLIdentityClause x = new SQLIdentityClause();
//        x.action = this.action;

        for (ISQLIdentityOption option : this.options) {
            ISQLIdentityOption optionClone = option.clone();
            x.addOption(optionClone);
        }
        return x;
    }


//    public IdentityAction getAction() {
//        return action;
//    }
//
//    public void setAction(IdentityAction action) {
//        this.action = action;
//    }

    public List<ISQLIdentityOption> getOptions() {
        return options;
    }

    public void addOption(ISQLIdentityOption option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }

    public enum IdentityAction implements ISQLASTEnum {
        ALWAYS("ALWAYS", "ALWAYS"),
        BY_DEFAULT("by_default", "BY_DEFAULT"),
        BY_DEFAULT_ON_NULL("by_default_on_null", "BY_DEFAULT_ON_NULL"),;

        public final String lower;
        public final String upper;

        IdentityAction(String lower, String upper) {
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
