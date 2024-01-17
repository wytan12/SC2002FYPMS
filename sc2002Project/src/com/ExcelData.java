package com;

import java.io.File;  
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.util.LinkedHashMap; 
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.ss.usermodel.CellType;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * The ExcelData class reads data from input Excel files. <br>
 * LinkedHashMap objects are used to store information about students, supervisors, projects, FYP coordinators and different requests
*/
public class ExcelData {  
	/**
	 * LinkedHashMap containing Student objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<String, Student> studentDatabase = new LinkedHashMap<String, Student>();
	
	/**
	 * LinkedHashMap containing Supervisor objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<String, Supervisor> supervisorDatabase = new LinkedHashMap<String, Supervisor>();
	
	/**
	 * LinkedHashMap containing Project objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<Integer, Project> projectDatabase = new LinkedHashMap<Integer, Project>();
	
	/**
	 * LinkedHashMap containing Supervisor objects with their respective names as keys.
	*/
	public static LinkedHashMap<String, Supervisor> supervisornameDatabase = new LinkedHashMap<String, Supervisor>();
	
	/**
	 * LinkedHashMap containing FYP objects with their respective titles as keys.
	*/
	public static LinkedHashMap<String, FYP> FYPDatabase = new LinkedHashMap<String, FYP>();
	
	/**
	 * LinkedHashMap containing StuToFYPReq objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<Integer, StuToFYPReq> stutofypreqDatabase = new LinkedHashMap<Integer, StuToFYPReq>();
	
	/**
	 * LinkedHashMap containing StuToSupReq objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<Integer, StuToSupReq> stutosupreqDatabase = new LinkedHashMap<Integer, StuToSupReq>();
	
	/**
	 * LinkedHashMap containing SupToFYPReq objects with their respective IDs as keys.
	*/
	public static LinkedHashMap<Integer, SupToFYPReq> suptofypreqDatabase = new LinkedHashMap<Integer, SupToFYPReq>();
	
	private static Student student;
	private static Project p;
	/**
	 * Constructs an ExcelData object by reading data from Excel files and storing it in various data structures.
	 * @throws Exception if there is an error reading the Excel files
	*/
	public ExcelData()   
	{  
		try  
		{  	
			//Test if file exists:
	        File file = new File("FYP_database.xlsx");
	        if(file.exists()) {
	            File FYPdatabase_file = new File("FYP_database.xlsx"); //creating a new file instance  
	            File FYPfile = new File("FYP coordinator.xlsx");
	            FileInputStream FYPdatabase_bytes = new FileInputStream(FYPdatabase_file);//obtaining bytes from the file
	            FileInputStream fyp = new FileInputStream(FYPfile);
	            XSSFWorkbook fypwb = new XSSFWorkbook(fyp);
				XSSFWorkbook FYPDatabase_wb = new XSSFWorkbook(FYPdatabase_bytes);   //creating Workbook instance that refers to .xlsx file
				XSSFSheet Student_sheet = FYPDatabase_wb.getSheet("Student");
				XSSFSheet Supervisor_sheet = FYPDatabase_wb.getSheet("FYPSupervisor");
				XSSFSheet Project_sheet = FYPDatabase_wb.getSheet("Project");
				XSSFSheet StuToFYPReq_sheet = FYPDatabase_wb.getSheet("StuToFYPReq");
				XSSFSheet StuToSupReq_sheet = FYPDatabase_wb.getSheet("StuToSupReq");
				XSSFSheet SupToFYPReq_sheet = FYPDatabase_wb.getSheet("SupToFYPReq");
				XSSFSheet sheet4 = fypwb.getSheetAt(0);
				
				
				//--------------Initalize objects-------------------------
				// Write project data
				for (int r = 1; r <= Project_sheet.getLastRowNum(); r++) { 
					// Fetch data from the spreadsheet
		             String project_id = Project_sheet.getRow(r).getCell(0).getRawValue(); 
		             String project_title = Project_sheet.getRow(r).getCell(1).getStringCellValue();
		             String project_status = Project_sheet.getRow(r).getCell(2).getStringCellValue();		             
		             Project project = new Project(project_title); //write project title
		             project.setProjectId((int)Double.parseDouble(project_id)); // write project id
		             if(project_status !="") { //write project status
		            	 project.setProjectStatus(ProjectStatus.valueOf(project_status.toUpperCase())); 
		            	 
		             }
		             project.setProjectTitle(project_title); // write project title
		             projectDatabase.put((int)Double.parseDouble(project_id), project); 
		             
		         }
				// WRit student data
				for (int r = 1; r <= Student_sheet.getLastRowNum(); r++) { 
					// Fetch data from student spreadsheet
		             String userId = Student_sheet.getRow(r).getCell(0).getStringCellValue(); 
		             String userName = Student_sheet.getRow(r).getCell(1).getStringCellValue(); 
		             Student student = new Student(userId, userName); 
		             // Write password
		             student.setPassword(Student_sheet.getRow(r).getCell(2).getStringCellValue());  
					// Link project object to student class
		             if(Student_sheet.getRow(r).getCell(3)!=null){
		            	 String project_id = Double.toString(Student_sheet.getRow(r).getCell(3).getNumericCellValue()); 
			             for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
//		            		 System.out.println(entry.getKey());
//		            		 System.out.println((int) Double.parseDouble((project_id)));
			            	 if(entry.getKey()== (int) Double.parseDouble((project_id))) {			            		 
			            		 student.setProject(entry.getValue());
			            	 }
			             }
		             }
		             if(Student_sheet.getRow(r).getCell(4)!=null) {
			             if(Student_sheet.getRow(r).getCell(4).getRawValue()=="1") {
			            	 student.setDeregisteredFYP(true);
			             }
			             else {
			            	 student.setDeregisteredFYP(false);
			             }	 
		             }

		             studentDatabase.put(userId, student); 
		         }
				// Write Supervisor data
				for (int r = 1; r <= Supervisor_sheet.getLastRowNum(); r++) { 
					// Fetch data from student spreadsheet
		             String userId = Supervisor_sheet.getRow(r).getCell(1).getStringCellValue(); 
		             String userName = Supervisor_sheet.getRow(r).getCell(0).getStringCellValue(); 
		             Supervisor supervisor = new Supervisor(userId, userName); 
		             // Write password
		             supervisor.setPassword(Supervisor_sheet.getRow(r).getCell(2).getStringCellValue());    
		             supervisorDatabase.put(userId, supervisor);
				}
				
				// Write count of projects allocated to supervisor
				for (int r = 1; r <= Project_sheet.getLastRowNum(); r++) {
//					System.out.println(Project_sheet.getRow(r).getCell(2).getStringCellValue());
					if(Project_sheet.getRow(r).getCell(2).getStringCellValue().equals("Allocated")) {
						
						for(Entry<String, Supervisor> entry : supervisorDatabase.entrySet()) {
//							System.out.println(entry.getValue().getuserId());
//							System.out.println(Project_sheet.getRow(r).getCell(4).getStringCellValue());
							if(entry.getValue().getUserId()==Project_sheet.getRow(r).getCell(4).getStringCellValue()) {

								
								entry.getValue().increaseNumOfSupervisedProjects();
							}
	
						}
					}
					
				}
				
				// Write FYPcoord data
				for (int r = 1; r <= sheet4.getLastRowNum(); r++) { 
		             String userName = sheet4.getRow(r).getCell(0).getStringCellValue(); 
		             String userId = sheet4.getRow(r).getCell(1).getStringCellValue(); 
		             userId = userId.replace("@NTU.EDU.SG", "");
		             FYP fypc = new FYP(userId, userName); 
		             // Link student object to project object 
		             for (FYP f : ExcelData.FYPDatabase.values()) {
		            	 if (p.getSupervisor().getUserId()== userId) {
		            		 f.setProject(p);
		            	 }
		             }
		             FYPDatabase.put(userId, fypc); 
				}
				// Link supervisor object to project class
				// Iterate through the hashmap to find the project object that corresponds with the excel
				for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
					for (int r = 1; r <= Project_sheet.getLastRowNum(); r++) { 
						
						double project_id = Double.parseDouble(Project_sheet.getRow(r).getCell(0).getRawValue());
						String supervisor_id = Project_sheet.getRow(r).getCell(4).getStringCellValue();
						int formatted_project_id = (int)project_id;
						if(formatted_project_id == entry.getValue().getprojectId()) {
							for(Map.Entry<String, Supervisor> entry_1 : supervisorDatabase.entrySet()) {
								if(entry_1.getValue().getUserId()==supervisor_id) {
									entry.getValue().setSupervisor(entry_1.getValue());
//									System.out.println(entry.getValue().getSupervisor().getuserId());
								}
							}
						}
					}
				}
				
				// Link student object to project class
				// Iterate through the hashmap to find the project object that corresponds with the excel
				for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
					for (int r = 1; r <= Project_sheet.getLastRowNum(); r++) { 
						double project_id = Double.parseDouble(Project_sheet.getRow(r).getCell(0).getRawValue());
						String student_id = Project_sheet.getRow(r).getCell(3).getStringCellValue();
						int formatted_project_id = (int)project_id;
						if(formatted_project_id == entry.getValue().getprojectId()) {
							for(Map.Entry<String, Student> entry_1 : studentDatabase.entrySet()) {
								if(entry_1.getValue().getUserId()==student_id) {
									entry.getValue().setStudent(entry_1.getValue());
//									System.out.println(entry.getValue().getStudent().getuserId());
								}
							}
						}
					}
				}
				
				// Write StuToFYPReq
				for (int r = 1; r <= StuToFYPReq_sheet.getLastRowNum(); r++) { 
					StuToFYPReq StuToFYPReq_obj = new StuToFYPReq(null, null); 
					for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
						
						double project_id = Double.parseDouble(StuToFYPReq_sheet.getRow(r).getCell(0).getRawValue());
						int formatted_project_id = (int)project_id;
						String request_status = StuToFYPReq_sheet.getRow(r).getCell(2).getStringCellValue();
						String request_type = StuToFYPReq_sheet.getRow(r).getCell(3).getStringCellValue();
						String student_id = StuToFYPReq_sheet.getRow(r).getCell(4).getStringCellValue();
						String FYPsup_id = StuToFYPReq_sheet.getRow(r).getCell(5).getStringCellValue();
						if(formatted_project_id==entry.getValue().getprojectId()) {
//							System.out.println(formatted_project_id);
							StuToFYPReq_obj.setProject(entry.getValue());
							if(request_status !="") { //write project status
								StuToFYPReq_obj.setRequestStatus(RequestStatus.valueOf(request_status.toUpperCase()));
				            }
							if(request_type !="") { //write project status
//								System.out.println("debug");
								StuToFYPReq_obj.setRequestType(RequestType.valueOf(request_type.toUpperCase()));
							for(Map.Entry<String, Student> entry_1 : studentDatabase.entrySet()) {
								if(entry_1.getValue().getUserId()==student_id) {
									StuToFYPReq_obj.setStudent(entry_1.getValue());
//									System.out.println(StuToSupReq_obj.getStudent().getuserId());
								}
							}
							for(Entry<String, FYP> entry_2 : FYPDatabase.entrySet()) {
								if(FYPsup_id.toString().equals(entry_2.getValue().getUserId().toString())) {
									StuToFYPReq_obj.setFyp(entry_2.getValue());
								}
							}
						}
						stutofypreqDatabase.put(formatted_project_id, StuToFYPReq_obj); 
						}
					}
				}
				
				// Write StuToSupReq
				for (int r = 1; r <= StuToSupReq_sheet.getLastRowNum(); r++) { 
					StuToSupReq StuToSupReq_obj = new StuToSupReq(null, null, null, null); 
					double project_id = Double.parseDouble(StuToSupReq_sheet.getRow(r).getCell(0).getRawValue());
					int formatted_project_id = (int)project_id;
					String project_title = StuToSupReq_sheet.getRow(r).getCell(1).getStringCellValue();
					String request_status = StuToSupReq_sheet.getRow(r).getCell(2).getStringCellValue();
					String request_type = StuToSupReq_sheet.getRow(r).getCell(3).getStringCellValue();
					String student_id = StuToSupReq_sheet.getRow(r).getCell(4).getStringCellValue();
					String supervisor_id = StuToSupReq_sheet.getRow(r).getCell(5).getStringCellValue();
					String project_oldtitle = StuToSupReq_sheet.getRow(r).getCell(6).getStringCellValue();
					
					
					for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
						if(entry.getValue().getprojectId()==formatted_project_id) {
//							System.out.println("debug");
							StuToSupReq_obj.setProject(entry.getValue());
						}
					}
					StuToSupReq_obj.setNewTitle(project_title);
					for(Map.Entry<String, Student> entry_1 : studentDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()==student_id) {
//							System.out.println("debug");
							StuToSupReq_obj.setStudent(entry_1.getValue());
						}
					}
					if(request_status !="") { //write project status
						StuToSupReq_obj.setRequestStatus(RequestStatus.valueOf(request_status.toUpperCase()));
		            }
					if(request_type !="") { //write project status
//						System.out.println("debug");
						StuToSupReq_obj.setRequestType(RequestType.valueOf(request_type.toUpperCase()));
					}
					for(Map.Entry<String, Supervisor> entry_1 : supervisorDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()==supervisor_id) {
//							System.out.println("debug");
							StuToSupReq_obj.setSupervisor(entry_1.getValue());
						}
					}
					StuToSupReq_obj.setOldTitle(project_oldtitle);
					stutosupreqDatabase.put(formatted_project_id, StuToSupReq_obj); 
				}
				
				// Write SupToFYPReq
				for (int r = 1; r <= SupToFYPReq_sheet.getLastRowNum(); r++) { 
					SupToFYPReq SupToFYPReq_obj = new SupToFYPReq(null, null, null);
					String supervisor_id = SupToFYPReq_sheet.getRow(r).getCell(0).getStringCellValue();
					String FYPcoord_id = SupToFYPReq_sheet.getRow(r).getCell(1).getStringCellValue();
					double project_id = Double.parseDouble(SupToFYPReq_sheet.getRow(r).getCell(2).getRawValue());
					int formatted_project_id = (int)project_id;
					String new_supervisor_id = SupToFYPReq_sheet.getRow(r).getCell(3).getStringCellValue();
					String request_status = SupToFYPReq_sheet.getRow(r).getCell(4).getStringCellValue();
					String request_type = SupToFYPReq_sheet.getRow(r).getCell(5).getStringCellValue();
					
					for(Map.Entry<String, Supervisor> entry_1 : supervisorDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()==supervisor_id) {
//							System.out.println("debug");
							SupToFYPReq_obj.setSupervisor(entry_1.getValue());
						}
					}
					for(Entry<String, FYP> entry_2 : FYPDatabase.entrySet()) {
						if(FYPcoord_id.toString().equals(entry_2.getValue().getUserId().toString())) {
//							System.out.println("debug");
							SupToFYPReq_obj.setFyp(entry_2.getValue());
						}
					}
					for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
						if(entry.getValue().getprojectId()==formatted_project_id) {
//							System.out.println("debug");
							SupToFYPReq_obj.setProject(entry.getValue());
						}
					}
					for(Map.Entry<String, Supervisor> entry_1 : supervisorDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()==new_supervisor_id) {
//							System.out.println("debug");
							SupToFYPReq_obj.setNewSup(entry_1.getValue());
						}
					}
					if(request_status !="") { //write project status
//						System.out.println("debug");
						SupToFYPReq_obj.setRequestStatus(RequestStatus.valueOf(request_status.toUpperCase()));
					}
					if(request_type !="") { //write project status
//						System.out.println("debug");
						SupToFYPReq_obj.setRequestType(RequestType.valueOf(request_type.toUpperCase()));
					}
					suptofypreqDatabase.put(formatted_project_id, SupToFYPReq_obj); 
				}
				
				// Write student objects object back into student class
				for(Map.Entry<String, Student> entry_1 : studentDatabase.entrySet()) {
					// Write suptoFYPreq object back to student class
					for(Entry<Integer, StuToSupReq> entry_2 : stutosupreqDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()== entry_2.getValue().getStudent().getUserId()) {
//							System.out.println("debug");
							entry_1.getValue().setStuToSupReq(entry_2.getValue());
						}
					}
					// Write StuTosupReq object back to student class
					for(Entry<Integer, StuToFYPReq> entry_3 : stutofypreqDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()== entry_3.getValue().getStudent().getUserId()) {
//							System.out.println("debug");
							entry_1.getValue().setStuToFYPReq(entry_3.getValue());
						}

					}
				}
				
				// Write Supervisor objects back into supervisor class
				for(Entry<String, Supervisor> entry_1 : supervisorDatabase.entrySet()) {
					// Write stutosup object back to supervisor class
					for(Entry<Integer, StuToSupReq> entry_2 : stutosupreqDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()== entry_2.getValue().getSupervisor().getUserId()) {
//							System.out.println("debug");
							entry_1.getValue().setStuToSupReq(entry_2.getValue());
						}
					}
					// Write suptofyp object back to student class
					for(Entry<Integer, SupToFYPReq> entry_3 : suptofypreqDatabase.entrySet()) {
//						System.out.println(entry_1.getValue().getuserId());
//						System.out.println(entry_3.getValue().getSupervisor().getuserId());
						if(entry_1.getValue().getUserId()== entry_3.getValue().getSupervisor().getUserId()) {
//							System.out.println("debug");
							entry_1.getValue().setSupToFYPReq(entry_3.getValue());
						}

					}
				}
				
				// Write FYP objects back into FYP class
				for(Entry<String, FYP> entry_1 : FYPDatabase.entrySet()) {
					// Write suptoFYPreq object back to student class
					for(Entry<Integer, StuToFYPReq> entry_2 : stutofypreqDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()== entry_2.getValue().getFyp().getUserId()) {
//							System.out.println("debug");
//							entry_1.getValue().setStuToSupReq(entry_2.getValue());
						}
					}
					// Write StuTosupReq object back to student class
					for(Entry<Integer, SupToFYPReq> entry_3 : suptofypreqDatabase.entrySet()) {
						if(entry_1.getValue().getUserId()== entry_3.getValue().getFyp().getUserId()) {
//							System.out.println("debug");
							entry_1.getValue().setSupToFYPReq(entry_3.getValue());
						}

					}
				}
				
				
				
	        }
	        //Import the file
	        else {
				File studentfile = new File("student list.xlsx");   //creating a new file instance  
				File facultyfile = new File("faculty_list.xlsx");
				File projectfile = new File("rollover project.xlsx");
				File FYPfile = new File("FYP coordinator.xlsx");
				
				FileInputStream stu = new FileInputStream(studentfile);   //obtaining bytes from the file  
				FileInputStream fac = new FileInputStream(facultyfile); 
				FileInputStream pro = new FileInputStream(projectfile);
				FileInputStream fyp = new FileInputStream(FYPfile);
				
				XSSFWorkbook stuwb = new XSSFWorkbook(stu);   //creating Workbook instance that refers to .xlsx file
				XSSFSheet sheet = stuwb.getSheetAt(0);     //creating a Sheet object to retrieve object  
				XSSFWorkbook facwb = new XSSFWorkbook(fac);   //creating Workbook instance that refers to .xlsx file
				XSSFSheet sheet2 = facwb.getSheetAt(0);
				XSSFWorkbook prowb = new XSSFWorkbook(pro);   //creating Workbook instance that refers to .xlsx file
				XSSFSheet sheet3 = prowb.getSheetAt(0);
				XSSFWorkbook fypwb = new XSSFWorkbook(fyp);   //creating Workbook instance that refers to .xlsx file
				XSSFSheet sheet4 = fypwb.getSheetAt(0);
				
				for (int r = 1; r <= sheet.getLastRowNum(); r++) { 
		             String userName = sheet.getRow(r).getCell(0).getStringCellValue(); 
		             String userId = sheet.getRow(r).getCell(1).getStringCellValue(); 
		             userId = userId.replace("@e.ntu.edu.sg;", "");
		             Student student = new Student(userId, userName); 
		             studentDatabase.put(userId, student); 
		         }
				
				for (int r = 1; r <= sheet4.getLastRowNum(); r++) { 
		             String userName = sheet4.getRow(r).getCell(0).getStringCellValue(); 
		             String userId = sheet4.getRow(r).getCell(1).getStringCellValue(); 
		             userId = userId.replace("@NTU.EDU.SG", "");
		             FYP fypc = new FYP(userId, userName); 
		             FYPDatabase.put(userId, fypc); 
				}
				 
				for (int r = 1; r <= sheet2.getLastRowNum(); r++) { 
					Row row = sheet2.getRow(r);
					if(row != null) {
						Cell userNameCell = row.getCell(0);
				        Cell userIdCell = row.getCell(1);
				        if (userNameCell != null && userIdCell != null) {
				            String userName = userNameCell.getStringCellValue(); 
				            String userId = userIdCell.getStringCellValue(); 
				            userId = userId.toUpperCase().replace("@NTU.EDU.SG", "");
				            Supervisor supervisor = new Supervisor(userId, userName); 
				            supervisorDatabase.put(userId, supervisor); 
				        }
					}
				}
				
				for (int r = 1; r <= sheet2.getLastRowNum(); r++) { 
					Row row = sheet2.getRow(r);
					if(row != null) {
						Cell userNameCell = row.getCell(0);
				        Cell userIdCell = row.getCell(1);
				        if (userNameCell != null && userIdCell != null) {
				            String userName = userNameCell.getStringCellValue(); 
				            String userId = userIdCell.getStringCellValue(); 
				            userId = userId.toUpperCase().replace("@NTU.EDU.SG", ""); 
				            supervisornameDatabase.put(userName, supervisorDatabase.get(userId)); 
				        }
					}
				}
				
				
				for (int r = 1; r <= sheet3.getLastRowNum(); r++) { 
					Row row = sheet3.getRow(r);
					if(row != null) {
						Cell projectSupCell = row.getCell(0);
				        Cell projectTitleCell = row.getCell(1);
				        if (projectSupCell != null && projectTitleCell != null) {
				        	String projectSupervisor = projectSupCell.getStringCellValue();
							String projectTitle = projectTitleCell.getStringCellValue();
							Project project = new Project(r, projectTitle, supervisornameDatabase.get(projectSupervisor));
						    projectDatabase.put(r, project);
				        }
					}
		         }
		        stuwb.close();
		        stu.close();
		        
		        facwb.close();
		        fac.close();	         
	        }
	    }
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}  
	}  
	
	
	/**
	 * Exports student LinkedHashMap to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_student() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
            	workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "Student";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
        
	    // Create header row with userid, password, project
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    Cell header_4 = header.createCell(3);
	    Cell header_5 = header.createCell(4);
	    header_1.setCellValue("Student_id");
	    header_2.setCellValue("Name");
	    header_3.setCellValue("Password");
	    header_4.setCellValue("Project_id");
	    header_5.setCellValue("Deregistered_status");
	    
	    
    
	    // Input data within the data rows 
	    int rowNumber = 1;
	    for(Map.Entry<String, Student> entry : studentDatabase.entrySet()) {
	    	// Get the student ID
	        String userid_str = entry.getKey();
	        // Get the student object
	        Student student_obj = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        // Write userID
	        Cell userid_cell = row.createCell(0);
	        userid_cell.setCellValue(userid_str);
	        // Write user name
	        Cell username_cell = row.createCell(1);
	        username_cell.setCellValue(student_obj.getName());
        	// Write password
	        Cell password_cell = row.createCell(2);
	        password_cell.setCellValue(student_obj.getPassword());
	        // Write project ID
	        Cell projectid_cell = row.createCell(3);
	        for (Project p : ExcelData.projectDatabase.values()) {
	        	Student projectStudent = p.getStudent();
	        	if (projectStudent != null && projectStudent.equals(student_obj)) {
	        		projectid_cell.setCellValue(p.getprojectId());
	        	}
	        }	 
	        // Write Deregistered_status
	        Cell deregistered_status_cell = row.createCell(4);
	        if(student_obj.isDeregisteredFYP()==true) {
	        	deregistered_status_cell.setCellValue("1");
	        }
	        else {
	        	deregistered_status_cell.setCellValue("0");
	        }
	        
	        
	    }
	    
	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
	}
	
	/**
	 * Exports supervisor LinkedHashMap to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_supervisor() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "FYPSupervisor";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
	    
	    // Create header row with userid, password, project
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    header_1.setCellValue("Name");
	    header_2.setCellValue("Supervisor_id");
	    header_3.setCellValue("Password");
	    
    
	    // Input data within the data rows
	    int rowNumber = 1;
	    for(Map.Entry<String, Supervisor> entry : supervisorDatabase.entrySet()) {
	    	// Get the supervisor ID
	        String userid_str = entry.getKey();
	        // Get the supervisor object
	        Supervisor supervisor_obj = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        // Write user's name
	        Cell username_cell = row.createCell(0);
	        username_cell.setCellValue(supervisor_obj.getName());
	        // Write userID
	        Cell userid_cell = row.createCell(1);
	        userid_cell.setCellValue(userid_str);
        	// Write password
	        Cell password_cell = row.createCell(2);
	        password_cell.setCellValue(supervisor_obj.getPassword());        
	    }

	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();        
	}
		
	/**
	 * Exports project LinkedHashMap to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_project() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "Project";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
	    
	    // Create header row with Project_id, 
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    Cell header_4 = header.createCell(3);
	    Cell header_5 = header.createCell(4);
	    header_1.setCellValue("Project_id");
	    header_2.setCellValue("Project_title");
	    header_3.setCellValue("Project_status");
	    header_4.setCellValue("Student_id");
	    header_5.setCellValue("Supervisor_id");
	    
	    // Input data within the data rows
	    int rowNumber = 1;
	    for(Map.Entry<Integer, Project> entry : projectDatabase.entrySet()) {
	    	// Get the projectID
	    	Integer project_id = entry.getKey();
	        // Get the project object
	    	Project project_object = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        
	        // Write project ID
	        Cell projectid_cell = row.createCell(0);
	        projectid_cell.setCellValue(project_object.getprojectId());
	        // Write ProjectTitle
	        Cell projecttitle_cell = row.createCell(1);
	        projecttitle_cell.setCellValue(project_object.getprojectTitle());
	        // Write ProjectStatus
	        Cell projectstatus_cell = row.createCell(2);
	        projectstatus_cell.setCellValue(project_object.getProjectStatus().projectStatus);
	        // Write student_id
	        Cell Studentid_cell = row.createCell(3);
	        if(project_object.getStudent()==null) {
	        	Studentid_cell.setCellValue("");
	        }
	        else {
	        	Studentid_cell.setCellValue(project_object.getStudent().getUserId());
	        }
	        // Write supervisor_id
	        Cell Supervisorid_cell = row.createCell(4);
	        if(project_object.getSupervisor()==null) {
	        	Supervisorid_cell.setCellValue("");
	        }
	        else {
	        	Supervisorid_cell.setCellValue(project_object.getSupervisor().getUserId());
	        }
	    }

	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();        
	}
	
	/**
	 * Exports project StuToFYPReq to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_StuToFYPReq() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "StuToFYPReq";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
	    
	    // Create header row with Project_id, 
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    Cell header_4 = header.createCell(3);
	    Cell header_5 = header.createCell(4);
	    Cell header_6 = header.createCell(4);

	    // Student
	    header_1.setCellValue("Project_id");
	    header_2.setCellValue("Project_title");
	    header_3.setCellValue("Request_Status");
	    header_4.setCellValue("Request_Type");
	    header_5.setCellValue("Student_id"); 
	    header_6.setCellValue("Fypcoord_id"); 

	    
	    // Input data within the data rows
	    int rowNumber = 1;
	    for(Map.Entry<Integer, StuToFYPReq> entry : stutofypreqDatabase.entrySet()) {
	    	// Get the projectID
	    	Integer project_id = entry.getKey();
	        // Get the project object
	    	StuToFYPReq request_object = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        
	        // Write project ID
	        Cell projectid_cell = row.createCell(0);
	        projectid_cell.setCellValue(request_object.getProject().getprojectId());
	        // Write ProjectTitle
	        Cell projecttitle_cell = row.createCell(1);
	        projecttitle_cell.setCellValue(request_object.getProject().getprojectTitle());
	        // Write request_status
	        Cell projectstatus_cell = row.createCell(2);
	        projectstatus_cell.setCellValue(request_object.getRequestStatus().toString());
	        // Write request_type
	        Cell requesttype_cell = row.createCell(3);
	        requesttype_cell.setCellValue(request_object.getRequestType().toString());
	        // Write student_id
	        Cell Studentid_cell = row.createCell(4);
	        if(request_object.getStudent()==null) {
	        	Studentid_cell.setCellValue("");
	        }
	        else {
	        	Studentid_cell.setCellValue(request_object.getStudent().getUserId());
	        }       
	        // Write Fypcoord_id
	        Cell Fypcoord_id_cell = row.createCell(5);
	        if(request_object.getFyp()==null) {
	        	Fypcoord_id_cell.setCellValue("");
	        }
	        else {
	        	Fypcoord_id_cell.setCellValue(request_object.getFyp().getUserId());
	        }
	    }

	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();        
	}
	
	/**
	 * Exports project StuToSupReq to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_StuToSupReq() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "StuToSupReq";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
	    
	    // Create header row with Project_id, 
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    Cell header_4 = header.createCell(3);
	    Cell header_5 = header.createCell(4);
	    Cell header_6 = header.createCell(5);
	    Cell header_7 = header.createCell(6);

	    // Student
	    header_1.setCellValue("Project_id");
	    header_2.setCellValue("Project_title");
	    header_3.setCellValue("Request_Status");
	    header_4.setCellValue("Request_type");
	    header_5.setCellValue("Student_id"); 
	    header_6.setCellValue("Supervisor_id"); 
	    header_7.setCellValue("Project_oldtitle");

	    
	    // Input data within the data rows
	    int rowNumber = 1;
	    for(Map.Entry<Integer, StuToSupReq> entry : stutosupreqDatabase.entrySet()) {
	    	// Get the projectID
	    	Integer project_id = entry.getKey();
	        // Get the project object
	    	StuToSupReq request_object = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        
	        // Write project ID
	        Cell projectid_cell = row.createCell(0);
	        projectid_cell.setCellValue(request_object.getProject().getprojectId());
	        // Write ProjectTitle
	        Cell projecttitle_cell = row.createCell(1);
	        projecttitle_cell.setCellValue(request_object.getProject().getprojectTitle());
	        // Write ProjectStatus
	        Cell projectstatus_cell = row.createCell(2);
	        projectstatus_cell.setCellValue(request_object.getRequestStatus().toString());
	        // Write request_type
	        Cell requesttype_cell = row.createCell(3);
	        requesttype_cell.setCellValue(request_object.getRequestType().toString());
	        // Write student_id
	        Cell Studentid_cell = row.createCell(4);
	        if(request_object.getStudent()==null) {
	        	Studentid_cell.setCellValue("");
	        }
	        else {
	        	Studentid_cell.setCellValue(request_object.getStudent().getUserId());
	        }
	        // Write supervisor_id
	        Cell Supervisorid_cell = row.createCell(5);
	        if(request_object.getSupervisor()==null) {
	        	Supervisorid_cell.setCellValue("");
	        }
	        else {
	        	Supervisorid_cell.setCellValue(request_object.getSupervisor().getUserId());
	        }
	        Cell oldtitle_cell = row.createCell(6);
	        oldtitle_cell.setCellValue(request_object.getOldTitle());
	    }

	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();        
	}
	
	/**
	 * Exports project SupToFYPReq to excel
	 * @throws InvalidFormatException 
	*/
	public static void exportExcel_SupToFYPReq() throws IOException, InvalidFormatException {
        // Specify the file path of the Excel workbook
        String filePath = "FYP_database.xlsx";
        File file = new File(filePath);

        // Create a new workbook or open an existing workbook
        Workbook workbook = null;
        if (file.exists()) {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        // Create a new sheet in the workbook       
        String sheetname = "SupToFYPReq";
        Sheet sheet = workbook.getSheet(sheetname);
        if (sheet == null) {
        	sheet = workbook.createSheet(sheetname);
        } 
	    
	    // Create header row with Project_id, 
	    Row header = sheet.createRow(0);
	    Cell header_1 = header.createCell(0);
	    Cell header_2 = header.createCell(1);
	    Cell header_3 = header.createCell(2);
	    Cell header_4 = header.createCell(3);
	    Cell header_5 = header.createCell(4);
	    Cell header_6 = header.createCell(5);

	    // Student
	    header_1.setCellValue("Supervisor_id");
	    header_2.setCellValue("FYPCoord_id");
	    header_3.setCellValue("Project_id"); 
	    header_4.setCellValue("NewSupervisor_id"); 
	    header_5.setCellValue("Request_Status");
	    header_6.setCellValue("Request_type");

	    
	    // Input data within the data rows
	    int rowNumber = 1;
	    for(Map.Entry<Integer, SupToFYPReq> entry : suptofypreqDatabase.entrySet()) {
	    	// Get the projectID
	    	Integer project_id = entry.getKey();
	        // Get the project object
	    	SupToFYPReq request_object = entry.getValue();
	        
	        Row row = sheet.createRow(rowNumber++);
	        
	        // Write supervisor_id
	        Cell Supervisorid_cell = row.createCell(0);
	        if(request_object.getSupervisor()==null) {
	        	Supervisorid_cell.setCellValue("");
	        }
	        else {
	        	Supervisorid_cell.setCellValue(request_object.getSupervisor().getUserId());
	        }
	        // Write FYPCoord_id
	        Cell FYPcoordid_cell = row.createCell(1);
	        if(request_object.getFyp()==null) {
	        	FYPcoordid_cell.setCellValue("");
	        }
	        else {
	        	FYPcoordid_cell.setCellValue(request_object.getFyp().getUserId());
	        }
	        // Write Project_id
	        Cell projectid_cell = row.createCell(2);
	        projectid_cell.setCellValue(request_object.getProject().getprojectId());
	        // Write NewSupervisor_id_cell
	        Cell NewSupervisor_id_cell = row.createCell(3);
	        if(request_object.getSupervisor()==null) {
	        	NewSupervisor_id_cell.setCellValue("");
	        }
	        else {
	        	NewSupervisor_id_cell.setCellValue(request_object.getNewSup().getUserId());
	        }
	        // Write ProjectStatus
	        Cell projectstatus_cell = row.createCell(4);
	        projectstatus_cell.setCellValue(request_object.getRequestStatus().toString());
	        // Write request_type
	        Cell requesttype_cell = row.createCell(5);
	        requesttype_cell.setCellValue(request_object.getRequestType().toString());

	    }

	    // Save and close the workbook
	    FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();        
	}
	
	
}  
