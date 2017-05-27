package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class XAxisFormatter implements IAxisValueFormatter {

    Float mFirstEntryTimestamp;

    public XAxisFormatter(Float firstEntryTimestamp) {
        mFirstEntryTimestamp = firstEntryTimestamp;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        return new SimpleDateFormat("dd/mm/yy", Locale.getDefault()).format(new Date((long) (v + mFirstEntryTimestamp)));
    }
}
