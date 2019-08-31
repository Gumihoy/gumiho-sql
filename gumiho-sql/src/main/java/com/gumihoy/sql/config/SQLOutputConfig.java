package com.gumihoy.sql.config;

/**
 * @author kent on 2019-06-15.
 */
public class SQLOutputConfig extends SQLParseConfig {

    public boolean lowerCase = false;
    public boolean printAfterSemi = false;
    public int lineMaxLength = 50;

    public Parameterized parameterized;


    public class Parameterized {
        public boolean mergeInList = false;
    }
}
