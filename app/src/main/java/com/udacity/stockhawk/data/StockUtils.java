package com.udacity.stockhawk.data;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockUtils {

    public static List<Entry> getEntryListFromHistory(String history) {
        List<Entry> entries = new ArrayList<>();
        List<Float> timestamps = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        String[] lines = history.split("\\r?\\n");
        for (String line : lines) {
            /* Split by commas, deleting spaces */
            String[] entry = line.split(",[ ]*");
            Float timestamp = Float.valueOf(entry[0]);
            Float value = Float.valueOf(entry[1]);
            timestamps.add(timestamp);
            values.add(value);
        }

        Collections.reverse(timestamps);
        Collections.reverse(values);

        Float firstTimestamp = timestamps.get(0);
        for (int i = 0; i < timestamps.size(); i++) {
            entries.add(new Entry(timestamps.get(i) - firstTimestamp, values.get(i)));
        }

        return entries;
    }

    public static Float getFirstEntryTimestamp(String history) {
        String[] lines = history.split("\\r?\\n");
        return Float.valueOf(lines[lines.length - 1].split(",[ ]*")[0]);
    }
}
