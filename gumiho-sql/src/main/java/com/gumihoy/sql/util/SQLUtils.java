package com.gumihoy.sql.util;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.ISQLReplaceable;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLDoubleQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLPropertyExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLReverseQuotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLUnquotedIdentifier;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLRowNumExpr;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectItem;
import com.gumihoy.sql.basic.ast.expr.select.SQLSelectQuery;
import com.gumihoy.sql.basic.ast.expr.select.SQLSubQueryTableReference;
import com.gumihoy.sql.basic.parser.SQLParserException;
import com.gumihoy.sql.basic.visitor.SQLASTOutputVisitor;
import com.gumihoy.sql.config.SQLOutputConfig;
import com.gumihoy.sql.dialect.edb.visitor.EDBASTOutputVisitor;
import com.gumihoy.sql.dialect.mysql.visitor.MySQLASTOutputVisitor;
import com.gumihoy.sql.dialect.oracle.visitor.OracleASTOutputVisitor;
import com.gumihoy.sql.dialect.postgresql.visitor.PostgreSQLASTOutputVisitor;
import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.translate.visitor.oracle2.edb.Oracle2EDBASTOutputVisitor;
import com.gumihoy.sql.translate.visitor.oracle2.mysql.Oracle2MySQLASTOutputVisitor;
import com.gumihoy.sql.translate.visitor.oracle2.postgresql.Oracle2PostgreSQLASTOutputVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author kent on 2019-06-14.
 */
public final class SQLUtils {

    protected static final Logger log = LoggerFactory.getLogger(SQLUtils.class);

    public static String format(String sql, DBType type) {
        return format(sql, type, null);
    }

    public static String format(String sql, DBType type, SQLOutputConfig config) {
        try {
            List<ISQLObject> statements = SQLParserUtils.parseStatements(sql, type, config);
            return toSQLString(statements, type, config);
        } catch (SQLParserException e) {
            log.warn("format exception. sql: {}", sql, e);
            throw e;
        }
    }

    public static String toSQLString(ISQLObject sqlObject, DBType type) {
        return toSQLString(sqlObject, type, null);
    }

    public static String toSQLString(ISQLObject sqlObject, DBType type, SQLOutputConfig config) {
        if (sqlObject == null) {
            throw new IllegalArgumentException("sqlObject is null.");
        }

        StringBuilder out = new StringBuilder();
        SQLASTOutputVisitor visitor = createASTOutputVisitor(out, type);
        sqlObject.accept(visitor);

        return out.toString();
    }

    public static String toSQLString(List<ISQLObject> sqlObjects, DBType type) {
        return toSQLString(sqlObjects, type, null);
    }

    public static String toSQLString(List<ISQLObject> sqlObjects, DBType type, SQLOutputConfig config) {
        StringBuilder out = new StringBuilder();

        SQLASTOutputVisitor visitor = createASTOutputVisitor(out, type, config);
        outputVisitor(sqlObjects, visitor);

        return out.toString();
    }

    public static void outputVisitor(ISQLObject sqlObject, SQLASTOutputVisitor visitor) {
        sqlObject.accept(visitor);
    }

    public static void outputVisitor(List<ISQLObject> sqlObjects, SQLASTOutputVisitor visitor) {
        for (int i = 0; i < sqlObjects.size(); i++) {
            ISQLObject stmt = sqlObjects.get(i);
            if (i > 0) {
                ISQLObject preStmt = sqlObjects.get(i - 1);
                if (!preStmt.isAfterSemi()) {
                    visitor.print(";");
                }
                visitor.println();
            }
            stmt.accept(visitor);
        }
    }


    public static SQLASTOutputVisitor createASTOutputVisitor(StringBuilder out, DBType type) {
        if (type == null) {
            type = DBType.SQL;
        }
        switch (type) {
            case Oracle:
                return new OracleASTOutputVisitor(out);
//            case EDB:
//                return new PPASSQLASTOutputVisitor(out);
//            case PostgreSQL:
//                return new PostgreSQLSQLASTOutputVisitor(out);
            case MySQL:
                return new MySQLASTOutputVisitor(out);
            default:
                return new SQLASTOutputVisitor(out);
        }
    }

    public static SQLASTOutputVisitor createASTOutputVisitor(StringBuilder out, DBType type, SQLOutputConfig config) {
        if (type == null) {
            type = DBType.SQL;
        }
        switch (type) {
            case Oracle:
                return new OracleASTOutputVisitor(out, config);
//            case EDB:
//                return new PPASSQLASTOutputVisitor(out);
//            case PostgreSQL:
//                return new PostgreSQLSQLASTOutputVisitor(out);
            case MySQL:
                return new MySQLASTOutputVisitor(out, config);
            default:
                return new SQLASTOutputVisitor(out, config);
        }
    }

    public static SQLASTOutputVisitor createASTOutputVisitor(StringBuilder out, DBType source, DBType target) {
        return createASTOutputVisitor(out, new SQLOutputConfig(), source, target);
    }

    public static SQLASTOutputVisitor createASTOutputVisitor(StringBuilder out, SQLOutputConfig config, DBType source, DBType target) {
        if (source == null) {
            source = DBType.SQL;
        }
        if (target == null) {
            target = source;
        }
        switch (source) {
            case Oracle:
                switch (target) {
                    case MySQL:
                        return new Oracle2MySQLASTOutputVisitor(out, config);
//                    case MariaDB:
//                        return new Oracle2MariaDBASTOutputVisitor(out, config);
                    case EDB:
                        return new Oracle2EDBASTOutputVisitor(out, config);
                    case PostgreSQL:
                        return new Oracle2PostgreSQLASTOutputVisitor(out, config);
//                    case SQLServer:
//                        return new Oracle2SQLServerASTOutputVisitor(out, config);
                    default:
                        return new OracleASTOutputVisitor(out);
                }
            case MySQL:
                switch (target) {
//                    case Oracle:
//                        return new MySQL2OracleASTOutputVisitor(out, config);
                    default:
                        return new MySQLASTOutputVisitor(out);
                }
            case MariaDB:
                switch (target) {
                    default:
//                        return new MariaDBSQLASTOutputVisitor(out);
                }
            case EDB:
                switch (target) {
                    default:
                        return new EDBASTOutputVisitor(out);
                }
            case PostgreSQL:
                switch (target) {
                    default:
                        return new PostgreSQLASTOutputVisitor(out);
                }
            default:
                return new SQLASTOutputVisitor(out);
        }
    }

    /**
     * 判断是否是数字开头
     *
     * @return true: 数字开头, false: 不是数字开头
     */
    public static boolean isStartWithNumber(String text) {
        if (text == null
                || text.length() == 0) {
            return false;
        }
        char c = text.charAt(0);
        if (c >= '0'
                && c <= '9') {
            return true;
        }
        return false;
    }

    private static final String[] SPECIAL_CHARACTER = new String[]{"<", ">", "（", "）", "(", ")", "{", "}", "[", "]", "!", "@", "%", "^", "&", "*", "/", "\\", "+", "-", "|", ":", "'", "=", " ", ";", ".", "\"", "?", "~"};

    /**
     * 判断是否是包含特殊字符
     *
     * @return true: 数字开头, false: 不是数字开头
     */
    public static boolean containsSpecialCharacter(String text) {
        if (text == null
                || text.length() == 0) {
            return false;
        }
        for (String src : SPECIAL_CHARACTER) {
            boolean contains = text.contains(src);
            if (contains) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有小写字母
     */
    public static boolean hasLowerLetter(String text) {
        if (text == null
                || text.length() == 0) {
            return false;
        }
        for (char c : text.toCharArray()) {
            if (c >= 'a'
                    && c <= 'z') {
                return true;
            }
        }
        return false;
    }


    public static boolean hasDoubleQuote(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return c1 == '"' && c2 == '"';
    }

    public static boolean hasReverseQuote(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return c1 == '`' && c2 == '`';
    }

    public static boolean hasSingeQuote(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return c1 == '\'' && c2 == '\'';
    }

    /**
     * remove double quote
     *
     * @param text "xxx"
     * @return xxx
     */
    public static String removeDoubleQuote(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return (c1 == '"' && c2 == '"') ? text.substring(1, text.length() - 1) : text;
    }

    /**
     * remove Single Quote
     *
     * @param text 'xx'
     * @return xx
     */
    public static String removeSingleQuote(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return (c1 == '\'' && c2 == '\'') ? text.substring(1, text.length() - 1) : text;
    }

    /**
     * remove Reverse Quote
     *
     * @param text `xx`
     * @return xx
     */
    public static String removeReverseQuote(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }

        char c1 = text.charAt(0);
        char c2 = text.charAt(text.length() - 1);

        return (c1 == '`' && c2 == '`') ? text.substring(1, text.length() - 1) : text;
    }


    public static String removeQuote(String text) {
        if (text == null
                || text.length() < 2) {
            return text;
        }

        text = removeDoubleQuote(text);
        text = removeReverseQuote(text);
        text = removeSingleQuote(text);
        return text;
    }

    public static boolean nameEquals(ISQLName a, ISQLName b, boolean full) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (full) {
            return a.fullNameHash() == b.fullNameHash();
        } else {
            return a.hash() == b.hash();
        }
    }


    public static boolean nameEqualsIgnoreCase(ISQLName a, ISQLName b) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (a instanceof SQLPropertyExpr
                && b instanceof SQLPropertyExpr) {
            return a.fullNameLowerHash() == b.fullNameLowerHash();
        } else {
            return a.lowerHash() == b.lowerHash();
        }
    }

    public static boolean nameEqualsIgnoreCase(ISQLName a, ISQLName b, boolean full) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (full) {
            return a.fullNameLowerHash() == b.fullNameLowerHash();
        } else {
            return a.lowerHash() == b.lowerHash();
        }
    }


    public static boolean equals(ISQLExpr a, ISQLExpr b, boolean full) {
        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (a instanceof ISQLName
                && b instanceof ISQLName) {
            if (full) {
                return ((ISQLName) a).fullNameHash() == ((ISQLName) b).fullNameHash();
            } else {
                return ((ISQLName) a).hash() == ((ISQLName) b).hash();
            }
        }

        return a.equals(b);
    }

    public static boolean equalsIgnoreCase(ISQLExpr a, ISQLExpr b, boolean full) {

        if (a == b) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }


        if (a instanceof ISQLName
                && b instanceof ISQLName) {
            if (full) {
                return ((ISQLName) a).fullNameLowerHash() == ((ISQLName) b).fullNameLowerHash();
            } else {
                return ((ISQLName) a).lowerHash() == ((ISQLName) b).lowerHash();
            }
        }

        return a.equals(b);
    }


    public static ISQLIdentifier ofName(String name) {

        boolean hasDoubleQuote = SQLUtils.hasDoubleQuote(name);
        if (hasDoubleQuote) {
            return new SQLDoubleQuotedIdentifier(name);
        }

        boolean hasReverseQuote = SQLUtils.hasReverseQuote(name);
        if (hasReverseQuote) {
            return new SQLReverseQuotedIdentifier(name);
        }

        return new SQLUnquotedIdentifier(name);
    }


    /**
     * target replace source
     *
     * @return true replace succ , false not replace
     */
    public static boolean replaceInParent(ISQLExpr source, ISQLExpr target) {
        if (source == null) {
            return false;
        }

        ISQLObject parent = source.getParent();
        if (parent instanceof ISQLReplaceable) {
            return ((ISQLReplaceable) parent).replace(source, target);
        }
        return false;
    }


    public static boolean isRowNum(ISQLExpr x, SQLSelectQuery query) {
        if (x instanceof SQLRowNumExpr) {
            return true;
        }

        if (x instanceof ISQLName) {
            long nameLowerHash = ((ISQLName) x).lowerHash();
            return isRowNum(nameLowerHash, query);
        }

        return false;
    }

    private static boolean isRowNum(long nameHash, SQLSelectQuery query) {
        if (query == null) {
            return false;
        }

        SQLSelectItem item = query.findSelectItem(nameHash);
        if (query.getFromClause() != null
                && query.getFromClause().getTableReference() instanceof SQLSubQueryTableReference
                && ((SQLSubQueryTableReference) query.getFromClause().getTableReference()).getSubQuery() instanceof SQLSelectQuery) {
            query = (SQLSelectQuery) ((SQLSubQueryTableReference) query.getFromClause().getTableReference()).getSubQuery();
        } else {
            query = null;
        }

        if (item == null
                && isRowNum(nameHash, query)) {
            return true;
        }
        if (item != null
                && isRowNum(item.getExpr(), query)) {
            return true;
        }

        return false;
    }
}
