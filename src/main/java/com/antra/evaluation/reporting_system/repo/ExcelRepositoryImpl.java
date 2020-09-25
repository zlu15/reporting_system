package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope
public class ExcelRepositoryImpl implements ExcelRepository {
    Map<String, ExcelFile> excelData = new ConcurrentHashMap<>();

    /**
     * This method will get the file id number from user and then call
     * the database to return the file
     * @param id
     * @return
     */
    @Override
    public ExcelFile getFileById(String id) {
        ExcelFile excelFile = this.excelData.get(id);
        return excelFile;
    }

    /**
     * This method will save the file information created by user
     * to the database
     * @param file
     */
    @Override
    public void saveFile(ExcelFile file) {
        String id = file.getId();
        excelData.put(id, file);
    }

    /**
     *This method will delete a file user created before into the database
     * @param id
     * @return
     */
    @Override
    public ExcelFile deleteFile(String id) {
        return excelData.remove(id);
    }


    /**
     * This method is used to get all files in the database
     * @return
     */
    @Override
    public List<ExcelFile> getFiles() {
        List<ExcelFile> list = new ArrayList<>();
        Iterator<Map.Entry<String, ExcelFile>> iterator = excelData.entrySet().iterator();
        while(iterator.hasNext()){
            list.add((ExcelFile) iterator.next().getValue());
        }
        return list;
    }
}

