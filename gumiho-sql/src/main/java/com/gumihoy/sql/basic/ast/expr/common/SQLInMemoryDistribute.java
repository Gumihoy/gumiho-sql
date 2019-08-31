package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

/**
 * DISTRIBUTE [ AUTO | BY { ROWID RANGE | PARTITION | SUBPARTITION } ]
 [ FOR SERVICE { DEFAULT | ALL | service_name | NONE } ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent on 2018/6/25.
 */
public class SQLInMemoryDistribute extends AbstractSQLExpr implements ISQLInMemoryAttribute{

    protected ISQLExpr distribute;

    protected ISQLExpr forServiceOption;

    @Override
    public void accept0(ISQLASTVisitor visitor) {
//        if(visitor.visit(this)) {
//            this.acceptChild(visitor, distribute);
//            this.acceptChild(visitor, forServiceOption);
//        }
    }

    @Override
    public SQLInMemoryDistribute clone() {
        SQLInMemoryDistribute x = new SQLInMemoryDistribute();

        if (this.distribute!=null) {
            ISQLExpr distributeClone = this.distribute.clone();
            x.setDistribute(distributeClone);
        }
        if (this.forServiceOption!=null) {
            ISQLExpr forServiceOptionClone = this.forServiceOption.clone();
            x.setForServiceOption(forServiceOptionClone);
        }
        return x;
    }


    public ISQLExpr getDistribute() {
        return distribute;
    }

    public void setDistribute(ISQLExpr distribute) {
        setChildParent(distribute);
        this.distribute = distribute;
    }

    public ISQLExpr getForServiceOption() {
        return forServiceOption;
    }

    public void setForServiceOption(ISQLExpr forServiceOption) {
        setChildParent(forServiceOption);
        this.forServiceOption = forServiceOption;
    }
}
