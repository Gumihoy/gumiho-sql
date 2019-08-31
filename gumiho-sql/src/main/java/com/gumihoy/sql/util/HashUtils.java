package com.gumihoy.sql.util;

/**
 * @author kent on 2019-06-14.
 */
public final class HashUtils {

    // ---------------- FVN ----------------------------
    // https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function#FNV-1a_hash

    public final static long FNV_1A_64_PRIME = 0x100000001b3L;
    public final static long FNV_1A_64_BASIC = 0xcbf29ce484222325L;

    public static long fnv_1a_64(String value) {
        if (value == null) {
            return 0;
        }
        long hash = FNV_1A_64_BASIC;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            hash ^= c;
            hash *= FNV_1A_64_PRIME;
        }

        return hash;
    }

    public static long fnv_1a_64_lower(String key) {
        long hash = FNV_1A_64_BASIC;
        for (int i = 0; i < key.length(); ++i) {
            char c = key.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                c = (char) (c + 32);
            }

            hash ^= c;
            hash *= FNV_1A_64_PRIME;
        }

        return hash;
    }

    public static long fnv_1a_64(char ch) {
        long hash = FNV_1A_64_BASIC;

        hash ^= ch;
        hash *= FNV_1A_64_PRIME;

        return hash;
    }


    public static long fnv_1a_64_lower(char ch) {
        long hash = FNV_1A_64_BASIC;

        if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch + 32);
        }

        hash ^= ch;
        hash *= FNV_1A_64_PRIME;

        return hash;
    }


    public static long fnv_1a_64(long basic, char ch) {
        long hash = basic;

        hash ^= ch;
        hash *= FNV_1A_64_PRIME;

        return hash;
    }


    public static long fnv_1a_64_lower(long basic, char ch) {
        long hash = basic;

        if (ch >= 'A' && ch <= 'Z') {
            ch = (char) (ch + 32);
        }

        hash ^= ch;
        hash *= FNV_1A_64_PRIME;

        return hash;
    }


    public static void main(String[] args) {
        String s= "FOREIGN";
        System.out.println(fnv_1a_64_lower(s));
    }
}
