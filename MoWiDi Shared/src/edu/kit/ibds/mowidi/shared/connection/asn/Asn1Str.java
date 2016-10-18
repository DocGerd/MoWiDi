package edu.kit.ibds.mowidi.shared.connection.asn;

public class Asn1Str extends Asn1Obj {

    private String val;

    public Asn1Str(String str) {
        super(Asn1Type.PrintableString);

        // XXX HACK
        try {
            setupVal(str);
        } catch (InvalidArgumentException e) {
            val = "InvalidArgument";
        }
    }

    private void setupVal(String str) throws InvalidArgumentException {
        if (str.matches("\\A[A-Za-z0-9 '()+,./:=?-]*\\z")) {
            val = str;
        } else {
            throw new InvalidArgumentException();
        }
    }

    @Override
    public int getValueLength() {
        return val.length();
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        byte[] strrep;

        try {
            strrep = val.getBytes("US-ASCII");
        } catch (java.io.UnsupportedEncodingException e) {
            // XXX HACK
            strrep = new byte[val.length()];
        }
        copyTo(enc, off, strrep);

        return (off + strrep.length);
    }
}
