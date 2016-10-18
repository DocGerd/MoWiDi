/******************************************************************
 *
 *	CyberUtil for Java
 *
 *	Copyright (C) Satoshi Konno 2002-2003
 *
 *	File: FileUtil.java
 *
 *	Revision:
 *
 *	01/12/03
 *		- first revision.
 *
 ******************************************************************/
package org.cybergarage.util;

public final class StringUtil {

    public static boolean hasData(final String value) {
        if (value == null) {
            return false;
        }
        if (value.length() <= 0) {
            return false;
        }
        return true;
    }

    public static int toInteger(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            Debug.warning(e);
        }
        return 0;
    }

    public static long toLong(final String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            Debug.warning(e);
        }
        return 0;
    }

    public static int findOf(final String str, final String chars, final int startIdx,
            final int endIdx, final int offset, final boolean isEqual) {
        if (offset == 0) {
            return -1;
        }
        int charCnt = chars.length();
        int idx = startIdx;
        while (true) {
            if (0 < offset) {
                if (endIdx < idx) {
                    break;
                }
            } else {
                if (idx < endIdx) {
                    break;
                }
            }
            char strc = str.charAt(idx);
            int noEqualCnt = 0;
            for (int n = 0; n < charCnt; n++) {
                char charc = chars.charAt(n);
                if (isEqual) {
                    if (strc == charc) {
                        return idx;
                    }
                } else {
                    if (strc != charc) {
                        noEqualCnt++;
                    }
                    if (noEqualCnt == charCnt) {
                        return idx;
                    }
                }
            }
            idx += offset;
        }
        return -1;
    }

    public static int findFirstOf(final String str, final String chars) {
        return findOf(str, chars, 0, (str.length() - 1), 1, true);
    }

    public static int findFirstNotOf(final String str, final String chars) {
        return findOf(str, chars, 0, (str.length() - 1), 1, false);
    }

    public static int findLastOf(final String str, final String chars) {
        return findOf(str, chars, (str.length() - 1), 0, -1, true);
    }

    public static int findLastNotOf(final String str, final String chars) {
        return findOf(str, chars, (str.length() - 1), 0, -1, false);
    }

    public static String trim(final String trimStr, final String trimChars) {
        int spIdx = findFirstNotOf(trimStr, trimChars);
        if (spIdx < 0) {
            String buf = trimStr;
            return buf;
        }
        String trimStr2 = trimStr.substring(spIdx, trimStr.length());
        spIdx = findLastNotOf(trimStr2, trimChars);
        if (spIdx < 0) {
            String buf = trimStr2;
            return buf;
        }
        String buf = trimStr2.substring(0, spIdx + 1);
        return buf;
    }
}
