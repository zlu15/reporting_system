package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.exception.FileNotFoundException;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import com.antra.evaluation.reporting_system.response.ErrorResponse;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExcelGenerationController {

    private static final Logger log = LoggerFactory.getLogger(ExcelGenerationController.class);
    ExcelService excelService;

    @Autowired
    ExcelRepository excelRepository;

    @Autowired
    ExcelGenerationService excelGenerationService;

    @Autowired
    public ExcelGenerationController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/excel")
    @ApiOperation("Generate Excel")
    public ResponseEntity<ExcelResponse> createExcel(@RequestBody @Validated ExcelRequest request) {
//
//        System.out.println("test1");
//
//        System.out.println("####################################################");
//        String description = request.getDescription();
//        System.out.println(description);
//
//        System.out.println("####################################################");
//        List<String> headers = request.getHeaders();
//        for(String s:headers){
//            System.out.println(s);
//        }
//
//        System.out.println("####################################################");
//        List<List<Object>> rows = request.getData();
//        for(List l: rows){
//            for(Object o: l){
//                System.out.println(o);
//            }
//        }
//        ExcelGenerationService excelGenerationService = new ExcelGenerationServiceImpl();


        ExcelData excelData = excelService.singleSlicer(request);

        try {
            excelGenerationService.generateExcelReport(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ExcelResponse response = new ExcelResponse();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/excel/auto")
    @ApiOperation("Generate Multi-Sheet Excel Using Split field")
    public ResponseEntity<ExcelResponse> createMultiSheetExcel(@RequestBody @Validated MultiSheetExcelRequest request) {

//        ExcelGenerationService excelGenerationService = new ExcelGenerationServiceImpl();
        ExcelData excelData = excelService.multiSlicer(request);

        try {
            excelGenerationService.generateExcelReport(excelData);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ExcelResponse response = new ExcelResponse();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/excel")
    @ApiOperation("List all existing files")
    public ResponseEntity<List<ExcelResponse>> listExcels() {
        var response = new ArrayList<ExcelResponse>();
        List<ExcelFile> files = excelService.getFiles();

        for(ExcelFile f: files){
            ExcelResponse excelResponse = new ExcelResponse();
            String id = f.getId();
            String path = f.getPath();
            path = path.substring(49);
            excelResponse.setFileId(id);
            excelResponse.setPath(path);
            response.add(excelResponse);
        }
//        System.out.println("Following files are all exsiting files in current database");
//        System.out.println(files);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/excel/{id}/content")
    public void downloadExcel(@PathVariable String id, HttpServletResponse response) throws IOException {
        InputStream fis = excelService.getExcelBodyById(id);

        if(fis==null){
            String error = new String("DOWNLOAD_FILE_NOT_FOUND");
            throw new FileNotFoundException(error);
        }

        ExcelFile file = excelService.getExcelFileById(id);
        String path = file.getPath();
        path = path.substring(49);
        String myFileName = "attachment; filename="+path;
        //InputStream fis = new FileInputStream("/Users/zhongyuanlu/IdeaProjects/reporting_system/file0.xlsx");
        response.setHeader("Content-Type","application/vnd.ms-excel");
        //response.setHeader("Content-Disposition","attachment; filename=\"myCopy.xlsx\""); //

        response.setHeader("Content-Disposition",myFileName);
        FileCopyUtils.copy(fis, response.getOutputStream());
    }

    @DeleteMapping("/excel/{id}")
    public ResponseEntity<ExcelResponse> deleteExcel(@PathVariable String id) {
        var response = new ExcelResponse();
        ExcelFile file = excelService.deleteFile(id);
        if(file == null){
            String error = new String("DELETE_FILE_NOT_FOUND");
            throw new FileNotFoundException(error);
        }
        response.setFileId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
// Log
// Exception handling
// Validation
