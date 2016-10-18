package edu.kit.ibds.mowidi.shared.connection.asn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Asn1Utc extends Asn1Obj {

    private final Date val;

    public Asn1Utc(Date date) {
        super(Asn1Type.Utc);
        val = date;
    }

    @Override
    public int getValueLength() {
        return 13;
    }

    @Override
    public int fillEncodedValue(byte[] enc, int off) {
        final SimpleDateFormat form = new SimpleDateFormat("yyMMddHHmmss'Z'");
        form.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String datestr = form.format(val);
        final byte[] datebytes = datestr.getBytes();

        System.arraycopy(datebytes, 0, enc, off, 13);
//        for (int i = 0; i < 13; i++) {
//            enc[off + i] = datebytes[i];
//        }

        return (off + 13);
    }
}
