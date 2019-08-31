package com.gumihoy.sql.basic.ast.expr.table.partition;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLSegmentAttribute;
import com.gumihoy.sql.basic.ast.expr.common.SQLStoreInClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.partition.sub.SQLSubPartitionDefinition;
import com.gumihoy.sql.basic.ast.expr.table.partition.values.ISQLPartitionValues;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * partition_definition: https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * <p>
 * <p>
 * <p>
 * <p>
 * PARTITION [partition]
 * range_values_clause
 * table_partition_description
 * [ ( { range_subpartition_desc [, range_subpartition_desc] ...
 * | list_subpartition_desc [, list_subpartition_desc] ...
 * | individual_hash_subparts [, individual_hash_subparts] ...
 * }partitionByConsistentHash
 * ) | hash_subparts_by_quantity ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/9.
 */
public class SQLPartitionDefinition extends AbstractSQLExpr {

    protected ISQLName name;

    protected ISQLPartitionValues values;

    protected final List<ISQLExpr> options = new ArrayList<>();

    protected ISQLExpr subpartitionsNum;

    protected SQLStoreInClause storeInClause;

    protected final List<SQLSubPartitionDefinition> subPartitions = new ArrayList<>();

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, values);
            this.acceptChild(visitor, options);

            this.acceptChild(visitor, subpartitionsNum);
//            this.acceptChild(visitor, storeInClause);

            this.acceptChild(visitor, subPartitions);
        }
    }

    @Override
    public SQLPartitionDefinition clone() {
        SQLPartitionDefinition x = new SQLPartitionDefinition();
        this.cloneTo(x);
        return x;
    }

    public void cloneTo(SQLPartitionDefinition x) {
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

        if (source == subpartitionsNum) {
            setSubpartitionsNum(target);
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

    public ISQLExpr getSubpartitionsNum() {
        return subpartitionsNum;
    }

    public void setSubpartitionsNum(ISQLExpr subpartitionsNum) {
        setChildParent(subpartitionsNum);
        this.subpartitionsNum = subpartitionsNum;
    }

    public SQLStoreInClause getStoreInClause() {
        return storeInClause;
    }

    public void setStoreInClause(SQLStoreInClause storeInClause) {
        setChildParent(storeInClause);
        this.storeInClause = storeInClause;
    }

    public List<SQLSubPartitionDefinition> getSubPartitions() {
        return subPartitions;
    }

    public void addSubPartition(SQLSubPartitionDefinition subPartition) {
        if (subPartition == null) {
            return;
        }
        setChildParent(subPartition);
        this.subPartitions.add(subPartition);
    }


    /**
     * Overflow [segmentAttribute...]
     */
    public static class SQLOverflowClause extends AbstractSQLExpr {
        protected final List<ISQLSegmentAttribute> segmentAttributes = new ArrayList<>();

        @Override
        protected void accept0(ISQLASTVisitor visitor) {

        }

        public List<ISQLSegmentAttribute> getSegmentAttributes() {
            return segmentAttributes;
        }
        public void addSegmentAttribute(ISQLSegmentAttribute segmentAttribute) {
            if (segmentAttribute == null) {
                return;
            }
            setChildParent(segmentAttribute);
            segmentAttributes.add(segmentAttribute);
        }
    }

}
