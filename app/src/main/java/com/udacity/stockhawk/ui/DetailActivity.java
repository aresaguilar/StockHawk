package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.StockUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID_HISTORY = 22;
    public static final String EXTRA_STOCK = "stock";

    private String mStockSymbol;
    private LineChart mHistoryLineChart;

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /* Get intent extras */
        mStockSymbol = getIntent().getExtras().getString(EXTRA_STOCK);

        /* Get references to views */
        mHistoryLineChart = (LineChart) findViewById(R.id.lc_stock_history);

        this.setTitle(mStockSymbol);

        /* Start loaders */
        getSupportLoaderManager().initLoader(LOADER_ID_HISTORY, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri stockUri = Contract.Quote.makeUriForStock(mStockSymbol);
        return new CursorLoader(this,
                stockUri,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[0]),
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_ID_HISTORY) {
            /* Read history data */
            data.moveToFirst();
            String mStockHistory = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
            Float firstEntryTimestamp = StockUtils.getFirstEntryTimestamp(mStockHistory);

            List<Entry> entries = StockUtils.getEntryListFromHistory(mStockHistory);
            LineDataSet mStockHistoryDataSet = new LineDataSet(entries, "Stock price");
            mStockHistoryDataSet.setColor(Color.WHITE);
            mStockHistoryDataSet.setCircleColor(Color.WHITE);
            mStockHistoryDataSet.setHighLightColor(Color.WHITE);
            mStockHistoryDataSet.setValueTextColor(Color.WHITE);

            LineData mStockHistoryData = new LineData(mStockHistoryDataSet);

            XAxis xAxis = mHistoryLineChart.getXAxis();
            xAxis.setTextColor(Color.WHITE);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new XAxisFormatter(firstEntryTimestamp));

            YAxis yAxisLeft = mHistoryLineChart.getAxisLeft();
            yAxisLeft.setTextColor(Color.WHITE);

            YAxis yAxisRight = mHistoryLineChart.getAxisRight();
            yAxisRight.setTextColor(Color.WHITE);

            /* Create graph */
            mHistoryLineChart.setData(mStockHistoryData);
            mHistoryLineChart.setDescription(null);
            mHistoryLineChart.animateX(1000);
            mHistoryLineChart.setHighlightPerDragEnabled(false);
            mHistoryLineChart.setHighlightPerTapEnabled(false);
            mHistoryLineChart.getLegend().setEnabled(false);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
