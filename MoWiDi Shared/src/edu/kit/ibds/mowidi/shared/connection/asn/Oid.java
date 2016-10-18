package edu.kit.ibds.mowidi.shared.connection.asn;

public enum Oid {

    /** {iso(1) member-body(2) us(840) x9-57(10040) x9algorithm(4) dsa-with-sha1(3)} */
    DSA_WITH_SHA1(1, 2, 840, 10040, 4, 3),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) commonName(3)} */
    CN(2, 5, 4, 3),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) countryName(6)} */
    CO(2, 5, 4, 6),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) localityName(7)} */
    LN(2, 5, 4, 7),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) stateOrProvinceName(8)} */
    ST(2, 5, 4, 8),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) organizationName(10)} */
    ON(2, 5, 4, 10),
    /** {joint-iso-itu-t(2) ds(5) attributeType(4) organizationUnitName(11)} */
    OU(2, 5, 4, 11);
    public final int[] val;

    private Oid(int... v) {
        this.val = v;
    }
}
