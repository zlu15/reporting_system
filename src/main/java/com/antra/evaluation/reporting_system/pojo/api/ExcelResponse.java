package com.antra.evaluation.reporting_system.pojo.api;

import javax.validation.constraints.NotNull;

public class ExcelResponse {

    @NotNull
    private String fileId;
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPath() {
        return fileName;
    }

    public void setPath(String path) {
        this.fileName = path;
    }
}
