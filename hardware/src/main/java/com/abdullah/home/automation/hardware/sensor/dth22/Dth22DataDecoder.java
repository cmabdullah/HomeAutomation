package com.abdullah.home.automation.hardware.sensor.dth22;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Dth22DataDecoder {

    protected double getReadingValueFromBytes(final byte hi, final byte low) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(hi);
        bb.put(low);
        short shortVal = bb.getShort(0);
        return (double) shortVal / 10;
    }
}
