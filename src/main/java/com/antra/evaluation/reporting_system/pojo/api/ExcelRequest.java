package com.antra.evaluation.reporting_system.pojo.api;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Excel Request getting from user http request when user what to generate
 * a single sheets excel file
 */
public class ExcelRequest {
    @NotNull
    private List<String> headers;
    @NotNull
    private String description;
    private List<List<Object>> data;
    public List<List<Object>> getData() {
        return data;
    }
    public void setData(List<List<Object>> data) {
        this.data = data;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getHeaders() {
        return headers;
    }
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
