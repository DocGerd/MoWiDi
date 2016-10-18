package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Enc extends Asn1Obj {

    private byte[] val;

    public Asn1Enc(byte[] enc) {
        super(Asn1Type.Null);
        val = enc;
    }

    // dummy function, since we override getEncodedLength()
    @Override
    public int getValueLength() {
        return 0;
    }

    @Override
    public int getEncodedLength() {
        return val.length;
    }

    // dummy function, since we override fillEncoded()
    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        return 0;
    }

    @Override
    int fillEncoded(byte[] enc, int off) {
        copyTo(enc, off, val);
        return (off + val.length);
    }
}
