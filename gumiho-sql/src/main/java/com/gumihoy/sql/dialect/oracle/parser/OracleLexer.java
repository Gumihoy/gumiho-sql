package com.gumihoy.sql.dialect.oracle.parser;

import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.COMMA;
import static com.gumihoy.sql.basic.parser.SQLToken.TokenKind.LPAREN;
import static com.gumihoy.sql.util.SQLCharacterUtils.isSQLIdentifierStart;
import static com.gumihoy.sql.util.SQLCharacterUtils.isWhitespace;

import com.gumihoy.sql.basic.ast.enums.SQLCommentType;
import com.gumihoy.sql.basic.parser.SQLLexer;
import com.gumihoy.sql.basic.parser.SQLReservedWords;
import com.gumihoy.sql.basic.parser.SQLToken;
import com.gumihoy.sql.config.SQLLexerConfig;
import com.gumihoy.sql.enums.DBType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kent on 2019-06-13.
 */
public class OracleLexer extends SQLLexer {

    public final static SQLReservedWords DEFAULT_ORACLE_RESERVED_WORDS;

    /**
     * https://dev.mysql.com/doc/refman/5.7/en/keywords.html
     */
    static {
        Map<Long, SQLToken.TokenKind> defaultKeyWordsMap = new HashMap<Long, SQLToken.TokenKind>();

        // A
        put(defaultKeyWordsMap, SQLToken.TokenKind.ALTER);
        put(defaultKeyWordsMap, SQLToken.TokenKind.AND);
        put(defaultKeyWordsMap, SQLToken.TokenKind.AS);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.AT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.AUTHORIZATION);

        // B
        put(defaultKeyWordsMap, SQLToken.TokenKind.BEGIN);
        put(defaultKeyWordsMap, SQLToken.TokenKind.BETWEEN);
        put(defaultKeyWordsMap, SQLToken.TokenKind.BOOLEAN);
        put(defaultKeyWordsMap, SQLToken.TokenKind.BY);

        // C
        put(defaultKeyWordsMap, SQLToken.TokenKind.CALL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CALLED);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CASE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CAST);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CHAR);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CHARACTER);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CHECK);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CLOB);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CLOSE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.COLLATE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.COLUMN);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.COMMIT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CONNECT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CONNECT_BY_ROOT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CONSTRAINT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CONTINUE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CORRESPONDING);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CREATE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.COMMENT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CONTINUE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CROSS);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRENT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRENT_DATE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRENT_TIME);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRENT_TIMESTAMP);
//
//        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRENT_USER);
        put(defaultKeyWordsMap, SQLToken.TokenKind.CURRVAL);


        // D
        put(defaultKeyWordsMap, SQLToken.TokenKind.DATE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DAY);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DEALLOCATE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DEC);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DECIMAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DECLARE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DEFAULT);

//        put(defaultKeyWordsMap, SQLToken.TokenKind.DELETE); // 不能用
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DEREF);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DESCRIBE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DETERMINISTIC);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DISCONNECT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.DISTINCT);
//
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DOUBLE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.DROP);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.DYNAMIC);

        // E
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ELSE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.END);
        put(defaultKeyWordsMap, SQLToken.TokenKind.EXISTS);


        // F
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FALSE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.FETCH);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FILTER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FLOAT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FOR);
        put(defaultKeyWordsMap, SQLToken.TokenKind.FOREIGN);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FREE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.FROM);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FULL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.FUNCTION);

        // G
//        put(defaultKeyWordsMap, SQLToken.TokenKind.GET);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.GLOBAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.GRANT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.GROUP);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.GROUPING);
//
//        // H
        put(defaultKeyWordsMap, SQLToken.TokenKind.HAVING);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.HOLD);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.HOUR);

        // I
//        put(defaultKeyWordsMap, SQLToken.TokenKind.IDENTITY);
        put(defaultKeyWordsMap, SQLToken.TokenKind.IF);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.IMMEDIATE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.IN);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INDICATOR);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INNER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INOUT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INPUT);
//
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INSENSITIVE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.INSERT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INTEGER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.INTERSECT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.INTERVAL);
        put(defaultKeyWordsMap, SQLToken.TokenKind.INTO);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.IS);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ISOLATION);

        // J
        put(defaultKeyWordsMap, SQLToken.TokenKind.JOIN);

        // K

        // L
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LANGUAGE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LARGE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LATERAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LEADING);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LEFT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.LIKE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LOCAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LOCALTIME);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.LOCALTIMESTAMP);

        // M
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MATCH);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MEMBER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MERGE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.METHOD);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MINUTE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MODIFIES);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MODULE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MONTH);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.MULTISET);

        // N
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NATIONAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NATURAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NCHAR);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NCLOB);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NEW);
        put(defaultKeyWordsMap, SQLToken.TokenKind.NEXTVAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NO);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NONE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.NOT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.NULL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.NUMERIC);

        // O
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OF);
        put(defaultKeyWordsMap, SQLToken.TokenKind.OFFSET);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OLD);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ON);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ONLY);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OPEN);
        put(defaultKeyWordsMap, SQLToken.TokenKind.OR);
        put(defaultKeyWordsMap, SQLToken.TokenKind.ORDER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OUT);

//        put(defaultKeyWordsMap, SQLToken.TokenKind.OUTER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OUTPUT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OVER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.OVERLAPS);

        // P
//        put(defaultKeyWordsMap, SQLToken.TokenKind.PARAMETER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.PARENT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.PARTITION);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.PRECISION);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.PREPARE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.PRIMARY);
        put(defaultKeyWordsMap, SQLToken.TokenKind.PRIOR);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.PROCEDURE);

        // Q

        // R
        put(defaultKeyWordsMap, SQLToken.TokenKind.RAISE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RANGE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.READS);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REAL);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RECURSIVE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REF);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REFERENCES);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REFERENCING);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_AVGX);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_AVGY);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_COUNT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_INTERCEPT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_R2);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_SLOPE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_SXX);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_SXY);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REGR_SYY);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RELEASE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RESULT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.RETURN);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RETURNS);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.REVOKE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.RIGHT);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ROLLBACK);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ROLLUP);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ROW);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.ROWS);

        // S

        put(defaultKeyWordsMap, SQLToken.TokenKind.SELECT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SESSION_USER);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SET);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.SMALLINT);

//        put(defaultKeyWordsMap, SQLToken.TokenKind.START);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SUBPARTITION);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SYNONYM);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SYSTEM);
        put(defaultKeyWordsMap, SQLToken.TokenKind.SYSTEM_USER);


        // T
        put(defaultKeyWordsMap, SQLToken.TokenKind.TABLE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.THEN);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TIME);
        put(defaultKeyWordsMap, SQLToken.TokenKind.TIMESTAMP);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TIMEZONE_HOUR);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TIMEZONE_MINUTE);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TO);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TRAILING);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TRANSLATION);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TREAT);
        put(defaultKeyWordsMap, SQLToken.TokenKind.TRIGGER);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TRANSLATION);
//        put(defaultKeyWordsMap, SQLToken.TokenKind.TRUE);


        // U
        put(defaultKeyWordsMap, SQLToken.TokenKind.UNION);
        put(defaultKeyWordsMap, SQLToken.TokenKind.UNIQUE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.UPDATE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.USER);


        // V
//        put(defaultKeyWordsMap, SQLToken.TokenKind.VALUE); 不可用
        put(defaultKeyWordsMap, SQLToken.TokenKind.VALUES);
        put(defaultKeyWordsMap, SQLToken.TokenKind.VARCHAR);

        // W
        put(defaultKeyWordsMap, SQLToken.TokenKind.WHEN);
        put(defaultKeyWordsMap, SQLToken.TokenKind.WHERE);
        put(defaultKeyWordsMap, SQLToken.TokenKind.WITH);

        // X

        // Y
//        put(defaultKeyWordsMap, SQLToken.TokenKind.YEAR); 不可用

        // Z
        DEFAULT_ORACLE_RESERVED_WORDS = new SQLReservedWords(defaultKeyWordsMap);
        RESERVED_WORDS = DEFAULT_ORACLE_RESERVED_WORDS;
    }

    public OracleLexer(String sql) {
        this(sql, null);
    }

    public OracleLexer(String input, SQLLexerConfig config) {
        super(input, DBType.Oracle, config);
    }


//    public void scanColon() {
//        kind = SQLToken.TokenKind.COLON;
//        scanChar();
//        if (ch == '=') {
//            kind = SQLToken.TokenKind.ASSIGN;
//            scanChar();
//        }
//    }

    @Override
    public void scanNumeric() {
        // 默认值
        kind = SQLToken.TokenKind.INTEGER_LITERAL;

        if (ch == '-' || ch == '+') {
            // 处理空格
            putAndScanChar();
            scanWhitespace();
        }

        scanDigit();

        if (ch == '.') {
            if (charAt(pos + 1) == '.') {
                return;
            }
            kind = SQLToken.TokenKind.DECIMAL_LITERAL;
            putAndScanChar();
            scanDigit();
        }

        if (ch == 'e' || ch == 'E') {
            kind = SQLToken.TokenKind.FLOATING_POINT_LITERAL;
            putAndScanChar();
            if (ch == '-' || ch == '+') {
                putAndScanChar();
            }
            scanDigit();
        }


        if (ch == 'f' || ch == 'F') {
            kind = SQLToken.TokenKind.BINARY_FLOAT_LITERAL;
            scanChar();

        } else if (ch == 'd' || ch == 'D') {
            kind = SQLToken.TokenKind.BINARY_DOUBLE_LITERAL;
            scanChar();
        }
    }

    @Override
    public void scanSlash() {
        // 默认值
        kind = SQLToken.TokenKind.SLASH;
        int subPos = pos;
        char next = charAt(++subPos);
        if (next == '*') {
            next = charAt(++subPos);
            if (next == '+') {
                scanMultiLineHint();
            } else {
                scanMultiLineComment();
            }
        } else {
            scanChar();
        }
    }

    @Override
    public void scanSub() {
        // 默认值
        kind = SQLToken.TokenKind.SUB;

        int subPos = pos;
        char next = charAt(++subPos);
        if (next == '-') {
            next = charAt(++subPos);
            if (next == '+') {
                scanMinusHint();
            } else {
                scanMinusComment();
            }

        } else {
            // 处理空格
            for (; ; ) {
                if (isWhitespace(next)) {
                    if (next == '\n') {
                        incrementLine();
                    }
                    next = charAt(++subPos);
                    continue;
                }
                break;
            }

            if ((token == null || token.kind == LPAREN || token.kind == COMMA) && next >= '0' && next <= '9') {
                scanNumeric();
            } else {
                scanChar();
            }
        }
    }

    /**
     * /*   * /
     */
    @Override
    public void scanMultiLineComment() {
        if (config.keepComment) {
            putAndScanCommentChar();
            putAndScanCommentChar();
        } else {
            scanChar();
            scanChar();
        }

        for (; ; ) {
            if (ch == '*' && charAt(pos + 1) == '/') {
                if (config.keepComment) {
                    putAndScanCommentChar();
                    putAndScanCommentChar();
                } else {
                    scanChar();
                    scanChar();
                }

                break;
            }

            if (config.keepComment) {
                putAndScanCommentChar();
            } else {
                scanChar();
            }

        }
        addComment(SQLCommentType.MULTI_LINE);
        nextToken();
    }

    public void scanMinusComment() {
        super.scanMinusComment();
//        kind = SQLToken.TokenKind.MINUS_COMMENT;
//        for (;;) {
//            scanChar();
//            if (ch != '-') {
//                break;
//            }
//        }
    }


    @Override
    public void scanMultiLineHint() {
        super.scanMultiLineHint();
    }

    @Override
    public void scanMinusHint() {
        super.scanMinusHint();
    }
}
