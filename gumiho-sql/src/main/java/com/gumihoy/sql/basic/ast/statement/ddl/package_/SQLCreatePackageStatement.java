package com.gumihoy.sql.basic.ast.statement.ddl.package_;

import com.gumihoy.sql.basic.ast.enums.SQLASType;
import com.gumihoy.sql.basic.ast.enums.SQLEditionAbleType;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.SQLSharingClause;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.statement.AbstractSQLStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.ISQLCreateStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE [ OR REPLACE ] PACKAGE name
 * [ AUTHID { DEFINER | CURRENT_USER } ]
 * { IS | AS }
 * [ declaration; ] [, ...]
 * [ { PROCEDURE proc_name [ (argname [ IN | IN OUT | OUT ] argtype [ DEFAULT value ] [, ...]) ];
 * [ PRAGMA RESTRICT_REFERENCES(name, { RNDS | RNPS | TRUST | WNDS | WNPS } [, ... ] ); ]
 * | FUNCTION func_name [ (argname [ IN | IN OUT | OUT ] argtype [ DEFAULT value ] [, ...]) ] RETURN rettype [ DETERMINISTIC ];
 * [ PRAGMA RESTRICT_REFERENCES(name, { RNDS | RNPS | TRUST | WNDS | WNPS } [, ... ] ); ] } ] [, ...]
 * END [ name ]
 * <p>
 * <p>
 * <p>
 * CREATE [ OR REPLACE ] [ EDITIONABLE | NONEDITIONABLE ]
 * PACKAGE [ schema. ] package_name [ sharing_clause ]
 * [ { default_collation_clause | invoker_rights_clause | accessible_by_clause }... ]
 * { IS | AS }
 * package_item_list
 * END [ package_name ] ;
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/CREATE-PACKAGE-statement.html#GUID-03A70A54-90FF-4293-B6B8-F0B35E184AC5
 *
 * @author kent on 2018/5/31.
 */
public class SQLCreatePackageStatement extends AbstractSQLStatement implements ISQLCreateStatement {

    protected boolean orReplace;

    protected SQLEditionAbleType editionAbleType;

    protected ISQLName name;

    protected SQLSharingClause sharingClause;

    protected final List<ISQLExpr> options = new ArrayList<>();

    protected SQLASType as = SQLASType.AS;

    protected final List<ISQLExpr> items = new ArrayList<>();

    protected ISQLName endName;

    public SQLCreatePackageStatement(DBType dbType) {
        super(dbType);
    }

    @Override
    protected void accept0(ISQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, name);
            this.acceptChild(visitor, sharingClause);
            this.acceptChild(visitor, options);
            this.acceptChild(visitor, items);
            this.acceptChild(visitor, endName);
        }
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
        return SQLObjectType.PACKAGE_CREATE;
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

    public String getPackgeName() {
        return name.getSimpleName();
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
    }

    public SQLSharingClause getSharingClause() {
        return sharingClause;
    }

    public void setSharingClause(SQLSharingClause sharingClause) {
        setChildParent(sharingClause);
        this.sharingClause = sharingClause;
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

    public SQLASType getAs() {
        return as;
    }

    public void setAs(SQLASType as) {
        this.as = as;
    }

    public List<ISQLExpr> getItems() {
        return items;
    }

    public void addItem(ISQLExpr item) {
        if (item == null) {
            return;
        }
        setChildParent(item);
        this.items.add(item);
    }

    public ISQLName getEndName() {
        return endName;
    }

    public void setEndName(ISQLName endName) {
        this.endName = endName;
    }

}
