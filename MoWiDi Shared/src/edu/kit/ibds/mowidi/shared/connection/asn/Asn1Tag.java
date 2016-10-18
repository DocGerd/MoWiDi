package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Tag extends Asn1Obj {

    private byte[] val;

    public Asn1Tag(Asn1Type type, byte[] enc) {
        super(type);
        val = enc;
    }

    public Asn1Tag(Asn1Type type, Asn1Obj obj) {
        this(type, obj.encodeDER());
    }

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
