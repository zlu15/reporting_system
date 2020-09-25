package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.*;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;

    @Override
    public InputStream getExcelBodyById(String id) {

        ExcelFile fileInfo = excelRepository.getFileById(id);

        if(fileInfo==null){
            return null;
        }

        String path = fileInfo.getPath();
        // if (fileInfo.isPresent()) {
            File file = new File(path);
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
      //  }
        return null;
    }

    @Override
    public ExcelData singleSlicer(ExcelRequest request) {
        ExcelData data = new ExcelData();

        String description = request.getDescription();
        List<String> headers = request.getHeaders();
        List<List<Object>> rows = request.getData();

        //ExcelData title
        data.setTitle("Test book");
        //ExcelData
        data.setGeneratedTime(LocalDateTime.now());
        //ExcelData Sheet
            //ExcelDataSheet headers
            List<ExcelDataHeader> tempHeaders = new ArrayList<>(headers.size());
            for(String s: headers){
                ExcelDataHeader tempHeader = new ExcelDataHeader();
                tempHeader.setName(s);
                tempHeader.setType(ExcelDataType.STRING);//hardcode as String type for now and could be adjusted based client's daily data structure
                tempHeader.setWidth(1000);//hardcode size for now and could be adjusted based client's daily data structure
                tempHeaders.add(tempHeader);
            }



        var sheet = new ExcelDataSheet();
        //ExcelDataSheet title
        sheet.setTitle(description);
            sheet.setHeaders(tempHeaders);
            //ExcelDataSheet dataRows
            sheet.setDataRows(rows);

        List<ExcelDataSheet> tempSheets = new ArrayList<>(1); //This 1 means it only generate a single sheet excel file
        tempSheets.add(sheet);
        data.setSheets(tempSheets);
        return data;
    }

    @Override
    public ExcelData multiSlicer(MultiSheetExcelRequest request) {
        ExcelData data = new ExcelData();

        String description = request.getDescription();
        List<String> headers = request.getHeaders();
        List<List<Object>> rows = request.getData();
        String splitBy = request.getSplitBy();
        int splitIndex = 0;

        //ExcelData title
        data.setTitle("Test book");
        //ExcelData
        data.setGeneratedTime(LocalDateTime.now());
        //ExcelData Sheet

        //ExcelDataSheet headers
        List<ExcelDataHeader> tempHeaders = new ArrayList<>(headers.size());
        for(String s: headers){
            if(!s.equals(splitBy)){ splitIndex++;}
            ExcelDataHeader tempHeader = new ExcelDataHeader();
            tempHeader.setName(s);
            tempHeader.setType(ExcelDataType.STRING);//hardcode as String type for now and could be adjusted based client's daily data structure
            tempHeader.setWidth(1000);//hardcode size for now and could be adjusted based client's daily data structure
            tempHeaders.add(tempHeader);
        }



        //ExcelDataSheet dataRows (inside setup ExcelDataSheet title)
        List<ExcelDataSheet> tempSheets = new ArrayList<>();
            //sort the data into different sheets
//        int finalSplitIndex = splitIndex;
        Map<String, List<List<Object>>> collect = new HashMap<>();
        for (List<Object> l : rows) {
            collect.computeIfAbsent((String) l.get(splitIndex), k -> new ArrayList<>()).add(l);
        }

        Iterator<Map.Entry<String, List<List<Object>>>> it = collect.entrySet().iterator();
        // iterating every set of entry in the HashMap.
        while (it.hasNext()) {
            Map.Entry<String, List<List<Object>>> set = it.next();
            //setup ExcelDataSheet headers
            var tempSheet = new ExcelDataSheet();
            tempSheet.setHeaders(tempHeaders);
            //ExcelDataSheet title
            tempSheet.setTitle(set.getKey());
            List<List<Object>> myTempList = set.getValue();
            tempSheet.setDataRows(set.getValue());
            tempSheets.add(tempSheet);
        }
        data.setSheets(tempSheets);




        return data;
    }

    @Override
    public ExcelFile deleteFile(String id) {
        return excelRepository.deleteFile(id);
    }

    @Override
    public List<ExcelFile> getFiles() {
        return excelRepository.getFiles();
    }

    @Override
    public ExcelFile getExcelFileById(String id) {

        List<ExcelFile> list = this.getFiles();
        ExcelFile file = list.get(Integer.parseInt(id));
        return file;
    }

}
