package com.gumihoy.sql.basic.ast.expr.table.partition.sub;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.ISQLPartitionValues;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * SUBPARTITION logical_name [[STORAGE] ENGINE [=] engine_name] [COMMENT [=] 'string' ] [DATA DIRECTORY [=] 'data_dir'] [INDEX DIRECTORY [=] 'index_dir'] [MAX_ROWS [=] max_number_of_rows] [MIN_ROWS [=] min_number_of_rows] [TABLESPACE [=] tablespace_name] [NODEGROUP [=] node_group_id]
 * subpartition_definition: https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * SUBPARTITION [subpartition] values_clause [partitioning_storage_clause]
 * subpartition_by: https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 *
 * @author kent onCondition 2018/4/9.
 */
public class SQLSubPartitionDefinition extends AbstractSQLExpr {

    protected ISQLName name;

    protected ISQLPartitionValues values;

    protected final List<ISQLExpr> options = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, values);
            this.acceptChild(visitor, options);
        }
    }

    @Override
    public SQLSubPartitionDefinition clone() {
        SQLSubPartitionDefinition x = new SQLSubPartitionDefinition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLSubPartitionDefinition x) {
        super.cloneTo(x);
        if (this.name != null) {
            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);
        }

        if (this.values != null) {
            ISQLPartitionValues valuesClone = this.values.clone();
            x.setValues(valuesClone);
        }

        for (ISQLExpr option : options) {
            ISQLExpr optionClone = option.clone();
            x.addOption(optionClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {
        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (source == values
                && target instanceof ISQLPartitionValues) {
            setValues((ISQLPartitionValues) target);
            return true;
        }

        boolean replace = replaceInList(options, source, target, this);
        if (replace) {
            return true;
        }

        return false;
    }

    public ISQLName getName() {
        return name;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public ISQLPartitionValues getValues() {
        return values;
    }

    public void setValues(ISQLPartitionValues values) {
        setChildParent(values);
        this.values = values;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }
}
