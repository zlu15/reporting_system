# Antra Final Evaluation Assessment #1

### Environment
In order to compile and run this assessment correctly, below environments are required:

* Java JDK 11+
* Maven
* Also you may need software to view the generated Excel files like Microsoft Office, LibreOffice, WPS, Google Doc etc.

### Objectives
To evaluate the candidates':
 * Java Coding proficiency.
 * Object oriented design.
 * Design and implementation of REST webservices/endpoints.
 * Basic compiling, packaging, building skill.
 * Testing skill.
 * Debug and issue fixing skill.
 * Code format and conventions.
 
 ### Install
 ####- Run the below command to compile and setup:
 * _`mvn install`_
  * or _`mvn install -Dmaven.test.skip=true`_ if the test fails during the building.
 ####- Run the below command to run the tests:
 * _`mvn test`_

 
 ### Project Explaination
 This project is simulating REST apis for the generation of Excel Files from user customizable data.
 The format of incoming data is not pre-defined, which means the user should pass data for each row and column. APIs are including: 
  * Generate Excel Files and store the files on the server for further downloading.
  * Excel Files could have 2 flavors: single sheet and multi-sheet.
  * User data should indicate which header to be used to split the data into multiple sheet.
  * An API for list and search excel files.
  * You can decide what meta data you need for the excel like generatedTime, filename, filesize.
  * An id is required for each generated file, of course.
  * Delete file by id.
  
 ### Functionality Implementation
 The implementation of this reporting system is based on REST EndPoint Deisgn Pattern. 
 All the controller level implementations are in the file ExcelGenerationController. 
 Functions includes:
   * Generate single-page .xlsx
   * Generate multi-page .xlsx
   * List all files in the database
   * Delete an existing file
   * Download an existing file
 
 ### Test Cases
 The test cases consists of two parts: unit test on Controller level and unit test on Service level
 
 ### Thread Safe Consideration
 Considering the fact that there might be multiple users to generate .xlsx files, it is necessary to make sure that fileId is a synchronized and global data.
 In order to achive that, I choose to use an AtomicInteger as fileId in ExcelGeneartionServiceImpl. Thus, each time when file is created, the fileId will be        increased for one and will not be interrupted by other users. 
 
 ### Exception Handler
 The exception that has not been covered in the skeleton might be the exception when user tried to delete or download an non-exisitng file. Thus, I have design an exception handler in controller that will handle the FileNotFound exception in the database.
 
 ### Validation for Pojo
 There are several pojos in the project to support the excel generations. I have checked and made certain attributes in each pojo to be NotNull. 
 
 
 
 
 

