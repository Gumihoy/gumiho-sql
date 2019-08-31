package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * subtype_definition
 * <p>
 * SUBTYPE subtype IS base_type [ constraint | CHARACTER SET character_set ] [ NOT NULL ]
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D__CHDCIGAD
 *
 * @author kent on 2018/4/25.
 */
public class SQLSubtypeDefinition extends AbstractSQLExpr implements ISQLTypeDefinition {

    protected ISQLName name;

    protected ISQLDataType dataType;

    protected ISQLExpr option;

    protected boolean notNull;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, dataType);
            this.acceptChild(visitor, option);

        }
    }

    @Override
    public SQLSubtypeDefinition clone() {
        return null;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        this.name = name;
    }

    public ISQLDataType getDataType() {
        return dataType;
    }

    public void setDataType(ISQLDataType dataType) {
        this.dataType = dataType;
    }

    public ISQLExpr getOption() {
        return option;
    }

    public void setOption(ISQLExpr option) {
        setChildParent(option);
        this.option = option;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }


    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
     */
    public interface ISQLSubtypeConstraint extends ISQLExpr {

    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
     */
    public class SQLSubtypeConstraint extends AbstractSQLExpr implements ISQLSubtypeConstraint {

        protected SQLIntegerLiteral precision;
        protected SQLIntegerLiteral scale;

        @Override
        public void accept0(ISQLASTVisitor visitor) {

        }

        public SQLIntegerLiteral getPrecision() {
            return precision;
        }

        public void setPrecision(SQLIntegerLiteral precision) {
            this.precision = precision;
        }

        public SQLIntegerLiteral getScale() {
            return scale;
        }

        public void setScale(SQLIntegerLiteral scale) {
            this.scale = scale;
        }
    }

    /**
     * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D
     */
    public static class SQLSubtypeRangeConstraint extends AbstractSQLExpr implements ISQLSubtypeConstraint {

        protected ISQLExpr lowValue;
        protected ISQLExpr highValue;


        public SQLSubtypeRangeConstraint(ISQLExpr lowValue, ISQLExpr highValue) {
            setLowValue(lowValue);
            setHighValue(highValue);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {

        }

        public ISQLExpr getLowValue() {
            return lowValue;
        }

        public void setLowValue(ISQLExpr lowValue) {
            this.lowValue = lowValue;
        }

        public ISQLExpr getHighValue() {
            return highValue;
        }

        public void setHighValue(ISQLExpr highValue) {
            this.highValue = highValue;
        }
    }
}
