package edu.kit.ibds.mowidi.shared.connection.asn;

import java.util.Comparator;
import java.util.Arrays;

public class Asn1Set extends Asn1Seq {

    /*
     * XXX Hack: Comparator assumes that we don't stuff Asn1Obj of
     * the same type into a Set
     */
    private static class Asn1ObjCmp implements Comparator<Asn1Obj> {

        @Override
        public int compare(Asn1Obj a, Asn1Obj b) {
            // mask out the primitive / constructed bit
            return ((a.tag & 0xdf) - (b.tag & 0xdf));
        }
//        public boolean equals(Asn1Obj a, Asn1Obj b) {
//            return (a.tag == b.tag);
//        }
    }

    public Asn1Set(Asn1Obj... objs) {
        super(Asn1Type.Set, objs);
        Arrays.sort(val, new Asn1ObjCmp());
    }
}
