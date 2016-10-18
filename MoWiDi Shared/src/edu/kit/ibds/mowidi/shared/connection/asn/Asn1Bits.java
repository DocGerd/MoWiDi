package edu.kit.ibds.mowidi.shared.connection.asn;

/*
 * XXX Assumption:  We won't have any unused bits in the bitstring.
 * This should be true for java crypto signatures.
 */
public class Asn1Bits extends Asn1Obj {

    private byte[] val;

    public Asn1Bits(byte[] bits) {
        super(Asn1Type.Bitstring);
        val = bits;
    }

    @Override
    public int getValueLength() {
        return (val.length + 1);
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {

        enc[off] = 0; /* unused bits */
        copyTo(enc, off + 1, val);

        return (off + 1 + val.length);
    }
}
