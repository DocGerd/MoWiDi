package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Null extends Asn1Obj {

    public Asn1Null() {
        super(Asn1Type.Null);
    }

    @Override
    public int getValueLength() {
        return 0;
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        return off;
    }
}
