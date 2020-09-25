package com.antra.evaluation.reporting_system.pojo.api;

import javax.validation.constraints.NotNull;

/**
 * Excel Request getting from user http request when user what to generate
 * a mutiple sheets excel file
 */

public class MultiSheetExcelRequest extends ExcelRequest{
    @NotNull
    private String splitBy;
    public String getSplitBy() {
        return splitBy;
    }
    public void setSplitBy(String splitBy) {
        this.splitBy = splitBy;
    }
}
