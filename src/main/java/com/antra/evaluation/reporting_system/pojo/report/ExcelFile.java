package com.antra.evaluation.reporting_system.pojo.report;

import javax.validation.constraints.NotNull;

/**
 * User generated a new excel file then this pojo will be stored with file ID
 * and store as a key-value pair into the concurrent hashmap
 */
public class ExcelFile {
    @NotNull
    private String id;
    @NotNull
    private String path;
    public ExcelFile() {
    }
    public ExcelFile(String id, String path) {
        this.id = id;
        this.path = path;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "ExcelFile{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
