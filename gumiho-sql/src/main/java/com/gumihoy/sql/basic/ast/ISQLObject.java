package com.gumihoy.sql.basic.ast;

import com.gumihoy.sql.basic.ast.expr.comment.ISQLComment;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.SQLObjectType;

import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public interface ISQLObject extends Cloneable {

    DBType getDbType();
    void setDbType(DBType dbType);

    DBType getTargetDBType();
    void setTargetDBType(DBType dbType);

    SQLObjectType getObjectType();

    void accept(ISQLASTVisitor visitor);

    ISQLObject clone();
    <T extends ISQLObject> void cloneTo(T x);

    ISQLObject getParent();
    void setParent(ISQLObject parent);


    boolean isAfterSemi();
    void setAfterSemi(boolean afterSemi);


    List<ISQLComment> getBeforeComments();
    void addBeforeComment(ISQLComment comment);
    void addBeforeComment(List<ISQLComment> comments);

    List<ISQLComment> getAfterComments();
    void addAfterComment(ISQLComment comment);
    void addAfterComment(List<ISQLComment> comments);


    boolean hasBeforeComment();
    boolean hasAfterComment();

}
