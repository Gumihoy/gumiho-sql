package com.gumihoy.sql.enums;

/**
 * @author kent on 2019-08-29.
 */
public interface DBVersion {

    String version();
    DBVersion defaultVersion();


    enum Oracle implements DBVersion {
        ;

        @Override
        public String version() {
            return null;
        }

        @Override
        public DBVersion defaultVersion() {
            return null;
        }


    }

    enum MySQL implements DBVersion {
        VERSION_5_6,
        VERSION_5_7,
        VERSION_8_0;

        @Override
        public String version() {
            return null;
        }

        @Override
        public DBVersion defaultVersion() {
            return null;
        }


    }

    enum EDB implements DBVersion {
        VERSION_9_6,
        VERSION_10
        ;

        @Override
        public String version() {
            return null;
        }

        @Override
        public DBVersion defaultVersion() {
            return null;
        }


    }

    enum PostgreSQL implements DBVersion {

        VERSION_9_6,
        VERSION_10
        ;

        @Override
        public String version() {
            return null;
        }

        @Override
        public DBVersion defaultVersion() {
            return null;
        }


    }
}
