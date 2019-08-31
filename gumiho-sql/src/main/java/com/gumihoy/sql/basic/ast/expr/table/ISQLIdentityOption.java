package com.gumihoy.sql.basic.ast.expr.table;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * identity_options
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/22.
 */
public interface ISQLIdentityOption extends ISQLExpr {

    @Override
    ISQLIdentityOption clone();


    /**
     * Start With (integer | LIMIT VALUE)
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent on 2018/6/22.
     */
    class SQLIdentityStartWithOption extends AbstractSQLExpr implements ISQLIdentityOption {

        protected ISQLExpr value;

        public SQLIdentityStartWithOption(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLIdentityStartWithOption clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLIdentityStartWithOption x = new SQLIdentityStartWithOption(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * Start With (integer | LIMIT VALUE)
     */
    class SQLLimitValueExpr extends AbstractSQLExpr {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

    }


    /**
     * Increment By value
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    class SQLIdentityIncrementByOption extends AbstractSQLExpr implements ISQLIdentityOption {

        protected boolean by = true;
        protected ISQLExpr value;


        public SQLIdentityIncrementByOption(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLIdentityIncrementByOption clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLIdentityIncrementByOption x = new SQLIdentityIncrementByOption(valueClone);
            return x;
        }


        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * MAXVALUE value
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityMaxValueOption extends AbstractSQLExpr implements ISQLIdentityOption {

        protected ISQLExpr value;

        public SQLIdentityMaxValueOption(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLIdentityMaxValueOption clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLIdentityMaxValueOption x = new SQLIdentityMaxValueOption(valueClone);
            return x;
        }


        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * NOMAXVALUE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityNoMaxValueOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
        }

        @Override
        public SQLIdentityNoMaxValueOption clone() {
            return new SQLIdentityNoMaxValueOption();
        }
    }


    /**
     * MINVALUE value
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityMinValueOption extends AbstractSQLExpr implements ISQLIdentityOption {

        protected ISQLExpr value;

        public SQLIdentityMinValueOption(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLIdentityMinValueOption clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLIdentityMinValueOption x = new SQLIdentityMinValueOption(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }


    /**
     * NOMINVALUE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityNoMinValueOption extends AbstractSQLExpr implements ISQLIdentityOption {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
//        visitor.visit(this);
        }

        @Override
        public SQLIdentityNoMinValueOption clone() {
            return new SQLIdentityNoMinValueOption();
        }
    }


    /**
     * CYCLE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityCycleOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLIdentityCycleOption clone() {
            return new SQLIdentityCycleOption();
        }
    }


    /**
     * NOCYCLE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityNoCycleOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLIdentityNoCycleOption clone() {
            return new SQLIdentityNoCycleOption();
        }
    }


    /**
     * CACHE integer
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityCacheOption extends AbstractSQLExpr implements ISQLIdentityOption {

        protected ISQLExpr value;

        public SQLIdentityCacheOption(ISQLExpr value) {
            setValue(value);
        }

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, value);
            }
        }

        @Override
        public SQLIdentityCacheOption clone() {
            ISQLExpr valueClone = this.value.clone();
            SQLIdentityCacheOption x = new SQLIdentityCacheOption(valueClone);
            return x;
        }

        public ISQLExpr getValue() {
            return value;
        }

        public void setValue(ISQLExpr value) {
            setChildParent(value);
            this.value = value;
        }
    }

    /**
     * NOCACHE
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityNoCacheOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
        visitor.visit(this);
        }

        @Override
        public SQLIdentityNoCacheOption clone() {
            return new SQLIdentityNoCacheOption();
        }
    }


    /**
     * ORDER
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityOrderOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLIdentityOrderOption clone() {
            return new SQLIdentityOrderOption();
        }
    }


    /**
     * NOORDER
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
     *
     * @author kent onCondition 2018/4/2.
     */
    public class SQLIdentityNoOrderOption extends AbstractSQLExpr implements ISQLIdentityOption {


        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SQLIdentityNoOrderOption clone() {
            return new SQLIdentityNoOrderOption();
        }
    }

}
