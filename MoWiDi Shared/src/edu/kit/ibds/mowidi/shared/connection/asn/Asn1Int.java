package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Int extends Asn1Obj {

    private byte[] val;

    private void setupVal(byte[] bval) {
        int unnecessaryBytes = 0;

        if (bval[0] == -1 || bval[0] == 0) {
            int i;
            byte last = bval[0];
            unnecessaryBytes = 0;
            for (i = 1; i < bval.length; i++) {
                int diff = last ^ bval[i];
                if ((diff & 0x80) > 0) {
                    break;
                }
                unnecessaryBytes++;
                if (diff > 0) {
                    break;
                }

                last = bval[i];
            }
        }

        if (unnecessaryBytes > 0) {
            byte[] newval = new byte[bval.length - unnecessaryBytes];
            for (int i = 0; i < newval.length; i++) {
                newval[i] = bval[i + unnecessaryBytes];
            }
            val = newval;
        } else {
            val = bval;
        }
    }

    public Asn1Int(int intval, boolean forceUnsigned) {
        super(Asn1Type.Integer);

        byte[] b;
        int i;

        b = new byte[5];

        for (i = 1; i < 5; i++) {
            b[i] = (byte) (intval >> (8 * (4 - i)));
        }
        b[0] = (byte) (forceUnsigned ? 0 : (b[1] & 0x80) > 0 ? -1 : 0);

        setupVal(b);
    }

    public Asn1Int(int intval) {
        this(intval, false);
    }

    public Asn1Int(byte[] b) {
        super(Asn1Type.Integer);
        setupVal(b);
    }

    // XXX Todo: Asn1Int(byte[] b, boolean forceUnsigned)
    @Override
    public int getValueLength() {
        return val.length;
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        copyTo(enc, off, val);
        return (off + val.length);
    }
}
