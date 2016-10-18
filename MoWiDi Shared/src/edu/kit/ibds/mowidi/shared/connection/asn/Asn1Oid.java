package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Oid extends Asn1Obj {

    private int[] val;

    public Asn1Oid(Oid id) {
        super(Asn1Type.Oid);
        val = id.val;
    }

    private static int getIntLength(int v) {
        int nBits = 32 - Integer.numberOfLeadingZeros(v);
        int nSeptets = (nBits + 6) / 7;
        return nSeptets;
    }

    @Override
    public int getValueLength() {
        int i, len;

        len = getIntLength(40 * val[0] + val[1]);
        for (i = 2; i < val.length; i++) {
            len += getIntLength(val[i]);
        }

        return len;
    }

    private int encodeInt(byte[] bc, int bi, int v) {
        int nBits = 32 - Integer.numberOfLeadingZeros(v);
        int nSeptets = (nBits + 6) / 7;
        int i;

        for (i = nSeptets - 1; i >= 0; i--) {
            bc[bi] = (byte) (0x80 | ((v >> (7 * i)) & 0x7f));
            bi++;
        }
        bc[bi - 1] &= 0x7f;

        return nSeptets;
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        int bi = 0;
        int vi;

        bi += encodeInt(enc, off + bi, 40 * val[0] + val[1]);
        for (vi = 2; vi < val.length; vi++) {
            bi += encodeInt(enc, off + bi, val[vi]);
        }

        return (off + bi);
    }
}
