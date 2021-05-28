package com.example.agilesavev2.utils;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class BarChartValueFormatter extends ValueFormatter {

    /**
     * DecimalFormat for formatting
     */
    protected DecimalFormat mFormat;

    protected int mDecimalDigits;

    protected String prefix;

    /**
     * Constructor that specifies to how many digits the value should be
     * formatted.
     *
     * @param digits
     */
    public BarChartValueFormatter(int digits) {
        setup(digits, "");
    }
    public BarChartValueFormatter(int digits, String prefix) {
        setup(digits, prefix);
    }

    /**
     * Sets up the formatter with a given number of decimal digits.
     *
     * @param digits
     */
    public void setup(int digits, String prefix) {

        this.mDecimalDigits = digits;
        this.prefix = prefix;

        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }

        mFormat = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    @Override
    public String getFormattedValue(float value) {

        // put more logic here ...
        // avoid memory allocations here (for performance reasons)
        if ((int)value == 0){
            return "";
        }
        else{
            return prefix + mFormat.format(value);
        }
    }

    /**
     * Returns the number of decimal digits this formatter uses.
     *
     * @return
     */
    public int getDecimalDigits() {
        return mDecimalDigits;
    }
}
