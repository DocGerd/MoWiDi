package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Seq extends Asn1Obj {

    protected Asn1Obj[] val;

    public Asn1Seq(Asn1Type type, Asn1Obj[] objs) {
        super(type);
        val = objs;
    }

    public Asn1Seq(Asn1Obj... objs) {
        this(Asn1Type.Sequence, objs);
    }

    @Override
    public int getValueLength() {
        int len = 0;

        for (Asn1Obj o : val) {
            len += o.getEncodedLength();
        }

        return len;
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        int i = off;

        for (Asn1Obj o : val) {
            i = o.fillEncoded(enc, i);
        }

        return i;
    }
}
