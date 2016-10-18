package edu.kit.ibds.mowidi.shared.connection.asn;

public enum Asn1Type {

    Integer(2),
    Bitstring(3),
    Null(5),
    Oid(6),
    Sequence(16 | 0x20),
    Set(17 | 0x20),
    PrintableString(19),
    Utc(23),
    CtxCZero(0x80 | 0x20 | 0x00),;
    public final byte tag;

    private Asn1Type(int i) {
        tag = (byte) i;
    }
}
