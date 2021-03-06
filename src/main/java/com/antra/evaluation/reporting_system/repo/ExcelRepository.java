package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

import java.util.List;

public interface ExcelRepository {
    ExcelFile getFileById(String id);
    void saveFile(ExcelFile file);
    ExcelFile deleteFile(String id);
    List<ExcelFile> getFiles();
}
