package edu.kit.ibds.mowidi.shared.connection.asn;

public abstract class Asn1Obj {

    public final byte tag;

    public abstract int fillEncodedValue(byte[] enc, int off);

    public abstract int getValueLength();

    public Asn1Obj(Asn1Type type) {
        this.tag = type.tag;
    }

    public static int getLengthLength(int len) {
        int enclen;

        if (len < 128) {
            enclen = 1;
        } else {
            enclen = 2;
            for (; len >= 256; len /= 256) {
                enclen++;
            }
        }

        return enclen;
    }

    public int getLengthLength() {
        return getLengthLength(getValueLength());
    }

    public static int fillEncodedLength(byte[] enc, int off, int lenval) {
        int lenlen;

        if (lenval < 128) {
            enc[off + 0] = (byte) lenval;
            lenlen = 1;
        } else {
            lenlen = getLengthLength(lenval);
            enc[off + 0] = (byte) (0x80 | (lenlen - 1));
            for (int i = lenlen - 1; i >= 1; i--) {
                enc[off + i] = (byte) lenval;
                lenval /= 256;
            }
        }

        return (off + lenlen);
    }

    public int fillEncodedLength(byte[] enc, int off) {
        return fillEncodedLength(enc, off, getValueLength());
    }

    public int getEncodedLength() {
        return (1 + getLengthLength() + getValueLength());
    }

    int fillEncoded(byte[] enc, int off) {
        int i;

        enc[off + 0] = tag;
        i = off + 1;
        i = fillEncodedLength(enc, i);
        i = fillEncodedValue(enc, i);

        return i;
    }

    public byte[] encodeDER() {
        byte[] enc = new byte[getEncodedLength()];

        fillEncoded(enc, 0);
        return enc;
    }

    void copyTo(byte[] to, int off, byte[] from) {
        for (int i = 0; i < from.length; i++) {
            to[off + i] = from[i];
        }
    }
}
