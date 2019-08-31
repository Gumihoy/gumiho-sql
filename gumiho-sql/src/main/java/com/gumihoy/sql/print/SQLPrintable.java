package com.gumihoy.sql.print;

import com.gumihoy.sql.basic.ast.ISQLObject;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.parser.SQLKeyWord;

import java.util.List;

/**
 * @author kent on 2019-06-15.
 */
public interface SQLPrintable {


    boolean isLowerCase();

    boolean isPrintAfterSemi();

    boolean isKeepComment();


    void print(char value);

    void print(int value);

    void print(long value);

    void print(float value);

    void print(double value);

    void print(CharSequence value);

    void print(boolean isLowerCase, CharSequence lower, CharSequence upper);

    void print(SQLKeyWord value);

    void print(ISQLASTEnum value);

    void printAccept(ISQLObject value);


    void print(List<? extends ISQLASTEnum> values, String separator);

    void printAccept(List<? extends ISQLObject> values, String separator);

    void printAccept(List<? extends ISQLObject> values, String separator, boolean paren);


    void printSpace();

    void printSpaceAfterValue(char value);

    void printSpaceAfterValue(CharSequence value);

    void printSpaceAfterValue(SQLKeyWord value);

    void printSpaceAfterValue(ISQLASTEnum value);

    void printSpaceAfterAccept(ISQLObject value);


    void printSpaceAfterValue(List<? extends ISQLASTEnum> values, String separator);

    void printSpaceAfterAccept(List<? extends ISQLObject> values, String separator);

    void printSpaceAfterAccept(List<? extends ISQLObject> values, String separator, boolean paren);

    void printSpaceAndLnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren);

    void printSpaceAndLnAndAccept(List<? extends ISQLObject> values, String separator, boolean paren);


    void println();

    void printlnAfterValue(CharSequence value);

    void printlnAfterValue(SQLKeyWord value);

    void printlnAfterValue(ISQLASTEnum value);

    void printlnAfterValue(List<? extends ISQLASTEnum> values);

    void printlnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren);


    void printlnAndAccept(ISQLObject value);

    void printlnAndAccept(List<? extends ISQLObject> values);

    void printlnAndAccept(List<? extends ISQLObject> values, String separator);

    void printlnAndAccept(List<? extends ISQLObject> values, String separator, boolean paren);


    void printIndent();

    void printIndentLnAfterValue(CharSequence value);

    void printIndentLnAfterValue(SQLKeyWord value);

    void printIndentLnAfterValue(ISQLASTEnum value);

    void printIndentLnAfterValue(List<? extends ISQLASTEnum> values);

    void printIndentLnAfterValue(List<? extends ISQLASTEnum> values, String separator, boolean paren);


    void printIndentLnAccept(ISQLObject value);

    void printIndentLnAccept(List<? extends ISQLObject> values);

    void printIndentLnAccept(List<? extends ISQLObject> values, String separator);

    void printIndentLnAccept(List<? extends ISQLObject> values, String separator, boolean paren);
}
