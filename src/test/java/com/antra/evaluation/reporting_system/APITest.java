package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.endpoint.ExcelGenerationController;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * All unit tests are trying to test the controller level logics
 */

public class APITest {

//    @Autowired
//    private MockMvc mockMvc;
private static final Logger log = LoggerFactory.getLogger(ExcelGenerationController.class);
    @Mock
    ExcelService excelService;

    @Mock
    ExcelGenerationService excelGenerationService;

    @Mock
    ExcelRepository excelRepository;

    @BeforeEach
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new ExcelGenerationController(excelService));
    }

    /**
     * This method test when the user try to download a file from database
     * @throws FileNotFoundException
     */
    @Test
    public void testFileDownload() throws FileNotFoundException {
        ExcelFile testFile = new ExcelFile("0", "/Users/zhongyuanlu/IdeaProjects/reporting_system/temp.xlsx");
        Mockito.when(excelService.getExcelBodyById(anyString())).thenReturn(new FileInputStream("temp.xlsx"));
        Mockito.when(excelService.getExcelFileById(anyString())).thenReturn(testFile);
        given().accept("application/json").get("/excel/0/content").peek().
                then().assertThat()
                .statusCode(200);
    }

    /**
     * This method test when user what to see all files user stored in database
     * @throws FileNotFoundException
     */
    @Test
    public void testListFiles() throws FileNotFoundException {
        // Mockito.when(excelService.getExcelBodyById(anyString())).thenReturn(new FileInputStream("temp.xlsx"));
        List<ExcelFile> list = new ArrayList<>();
        Mockito.when(excelService.getFiles()).thenReturn(list);

        given().accept("application/json").get("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }


    /**
     * This method test when user what to delete a file
     * @throws IOException
     */
    @Test
    public void testDeleteFileFound() throws IOException{
        ExcelFile testFile = new ExcelFile();
        Mockito.when(excelService.deleteFile(anyString())).thenReturn(testFile);
        given().accept("application/json").delete("/excel/0").peek().
                then().assertThat()
                .statusCode(200);
    }

    /**
     * This method test when user what to delete a file but the file does not exist
     * @throws IOException
     */
    @Test
    public void testDeleteFileNotFound() throws IOException{
        ExcelFile testFile = new ExcelFile();
        Mockito.when(excelService.deleteFile(anyString())).thenReturn(null);
        given().accept("application/json").delete("/excel/0").peek().
                then().assertThat()
                .statusCode(404);
    }

    /**
     * This method test when user what to generate a file
     * @throws IOException
     */
    @Test
//    @Disabled
    public void testExcelGeneration() throws IOException {
        // Mockito.when(excelService.getExcelBodyById(anyString())).thenReturn(new FileInputStream("temp.xlsx"));
        ExcelRequest testRequest = new ExcelRequest();
        ExcelData testData = new ExcelData();
        File file =  new File("/Users/zhongyuanlu/IdeaProjects/reporting_system/temp.xlsx");
        Mockito.when(excelService.singleSlicer(testRequest)).thenReturn(testData);
        Mockito.when(excelGenerationService.generateExcelReport(testData)).thenReturn(file);
        given().accept("application/json").contentType(ContentType.JSON).body("{\"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]]}").post("/excel").peek().
                then().assertThat()
                .statusCode(400);
    }



}