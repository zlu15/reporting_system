package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.pojo.report.*;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * All unit test are trying to test the service level logic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportingSystemApplicationTests {

    @Autowired
    ExcelGenerationService reportService;

    @Autowired
    ExcelService excelService;

    ExcelData data = new ExcelData();

    @BeforeEach // We are using JUnit 5, @Before is replaced by @BeforeEach
    public void setUpData() {
        data.setTitle("Test book");
        data.setGeneratedTime(LocalDateTime.now());

        var sheets = new ArrayList<ExcelDataSheet>();
        var sheet1 = new ExcelDataSheet();
        sheet1.setTitle("First Sheet");

        var headersS1 = new ArrayList<ExcelDataHeader>();
        ExcelDataHeader header1 = new ExcelDataHeader();
        header1.setName("NameTest");
        //       header1.setWidth(10000);
        header1.setType(ExcelDataType.STRING);
        headersS1.add(header1);

        ExcelDataHeader header2 = new ExcelDataHeader();
        header2.setName("Age");
        //   header2.setWidth(10000);
        header2.setType(ExcelDataType.NUMBER);
        headersS1.add(header2);

        List<List<Object>> dataRows = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add("Dawei");
        row1.add(12);
        List<Object> row2 = new ArrayList<>();
        row2.add("Dawei2");
        row2.add(23);
        dataRows.add(row1);
        dataRows.add(row2);

        sheet1.setDataRows(dataRows);
        sheet1.setHeaders(headersS1);
        sheets.add(sheet1);
        data.setSheets(sheets);

        var sheet2 = new ExcelDataSheet();
        sheet2.setTitle("second Sheet");
        sheet2.setDataRows(dataRows);
        sheet2.setHeaders(headersS1);
        sheets.add(sheet2);
    }

    /**
     * This method will test if the excel file has been generated
     */
    @Test
    public void testExcelGeneration() {
        File file = null;
        try {
            file = reportService.generateExcelReport(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(file != null);
    }

    /**
     * Test for list all files when there is no file being created at all
     */
    @Test
    public void testExcelList1(){
        List<ExcelFile> files = excelService.getFiles();
        assertTrue(files.size() == 0);
    }

    /**
     * Test for list all files when there are two files being created
     */
    @Test
    public void testExcelList2(){
        List<ExcelFile> files = excelService.getFiles();
        File file = null;
        try {
            file = reportService.generateExcelReport(data);
            file = reportService.generateExcelReport(data);
        } catch (IOException e) {
            e.printStackTrace();
        }


        files = excelService.getFiles();
        assertTrue(files.size() == 2);

    }

    /**
     * Test download service when there exists a file for downloading
     */
    @Test
    public void testDownloadFile1(){
        File file = null;
        try {
            file = reportService.generateExcelReport(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream fis = excelService.getExcelBodyById("0");
        assertTrue(fis!=null);


        ExcelFile testFile = excelService.getExcelFileById("0");
        assertTrue(testFile!=null);

    }

//    @Test(expected = IndexOutOfBoundsException.class)
//    public void testDelete(){
//        File file = null;
//        try {
//            file = reportService.generateExcelReport(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        excelService.deleteFile("0");
//
//        excelService.getExcelFileById("0");
//
////        ExcelFile file1 = excelService.getExcelFileById("0");
//
////        assertTrue(file1==null);
//
//    }

//    @Test
//    public void shouldThrowException() {
//        assertThatThrownBy(() -> methodThrowingException()).hasCause(InetAddressException .class);
//    }
}
