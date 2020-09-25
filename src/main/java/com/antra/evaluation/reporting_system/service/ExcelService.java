package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    InputStream getExcelBodyById(String id);
    ExcelData singleSlicer(ExcelRequest request);
    ExcelData multiSlicer(MultiSheetExcelRequest request);
    ExcelFile deleteFile(String id);
    List<ExcelFile> getFiles();
    ExcelFile getExcelFileById(String id);
}
