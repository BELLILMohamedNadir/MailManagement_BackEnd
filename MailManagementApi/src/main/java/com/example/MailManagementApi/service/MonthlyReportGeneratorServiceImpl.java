package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.EmployeeAttendance;
import com.example.MailManagementApi.helper_classes.AttendanceByStructure;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.AttendanceRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@Slf4j
@Service
@Transactional

public class MonthlyReportGeneratorServiceImpl implements MonthlyReportGeneratorService {

    @Autowired
    @Lazy
    ReportService reportService;
    @Autowired
    @Lazy
    AttendanceRepository attendanceRepository;

    PdfPTable reference;

    PdfPCell cellCode;

    @Scheduled(cron = "0 0 0 1 * *")
    @Override
    public void generate() throws IOException {
        List<AttendanceByStructure> structureAttendances=getAttendances();
        if (!structureAttendances.isEmpty()){
            for (AttendanceByStructure attendances :structureAttendances){
                generateReport(attendances);
            }
        }
    }

    private void generateReport(AttendanceByStructure att) throws FileNotFoundException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String path = "listOfPresence_" + simpleDateFormat.format(Calendar.getInstance().getTime())
                + "_" + directionFormat(att.getStructure()) + "monthly.pdf";
        String date = attendanceRepository.getPreviousMonth();
        // Creating the Object of Document
        Document document = new Document(PageSize.A4.rotate());
        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, new FileOutputStream(path));
        // Opening the created document to change it
        document.open();
        //create a new page
        document.newPage();
        // Creating font
        // Setting font style and size
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Font fontDate = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontDate.setSize(13);
        // Creating paragraph
        Paragraph paragraphDate = new Paragraph("Date :" + date, fontDate);
        paragraphDate.setAlignment(Element.ALIGN_LEFT);
        paragraphDate.setSpacingBefore(10);
        Paragraph paragraphStructure = new Paragraph("Structure :" + att.getStructure(), fontDate);
        paragraphDate.setAlignment(Element.ALIGN_LEFT);
        paragraphDate.setSpacingBefore(10);
        Paragraph paragraph = new Paragraph("Liste des Présences", fontTitle);
        // Aligning the paragraph in the document
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph.setSpacingBefore(5);

        // Creating a table of the 33 columns
        PdfPTable table = new PdfPTable(33);
        // Setting width of the table, its columns and spacing
        table.setWidthPercentage(105);
        table.setWidths(new int[]{10,10,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3});
        table.setSpacingBefore(40);
        // Create Table Cells for the table header
        PdfPCell cell = new PdfPCell();
        // Setting the background color and padding of the table cell
        cell.setBackgroundColor(CMYKColor.BLACK);
        cell.setPadding(5);
        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(10);
        font.setColor(CMYKColor.WHITE);
        // Adding headings in the created table cell or  header
        // Adding Cell to table
        cell.setNoWrap(false);
        cell.setBackgroundColor(new Color(0, 0, 0));
        cell.setPhrase(new Phrase("Nom", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Prénom", font));
        table.addCell(cell);





        //create the number of days in the header it's depend on the current month
        for (int i = 1; i <32; i++) {
            cell.setPhrase(new Phrase(i + "", font));
            table.addCell(cell);
        }





        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font2.setSize(9);
        font2.setColor(CMYKColor.BLACK);
        cell.setBackgroundColor(Color.WHITE);
        // Iterating the list of employees stats
        for (EmployeeAttendance attendance : att.getAttendanceS()) {
            // employee info's
            table.addCell(attendance.getName());
            table.addCell(attendance.getFirstName());

            // Iterating the list of employee attendance's
            int i=-1;
            for (String s : attendance.getStats()) {
                i++;
                if (i==31)
                    break;
                cell.setBackgroundColor(Color.WHITE);
                switch (s) {
                    case "MUTATION" -> cell.setPhrase(new Phrase("MUT", font2));
                    case "SUSPENDU DE SES FONCTIONS" -> cell.setPhrase(new Phrase("SUS", font2));
                    case "FORMATION" -> cell.setPhrase(new Phrase("FOR", font2));
                    case "CONGÉ  ANNUEL" -> cell.setPhrase(new Phrase("CA", font2));
                    case "CONGÉ   EXEPTIONNEL " -> cell.setPhrase(new Phrase("CE", font2));
                    case "RÉCUPÉRATION" -> cell.setPhrase(new Phrase("RC", font2));
                    case "CONGÉ  SANS SOLDE" -> cell.setPhrase(new Phrase("CS", font2));
                    case "MISSION COMMANDEE" -> cell.setPhrase(new Phrase("MIS", font2));
                    case "ABSENCE REMUNEREE" -> cell.setPhrase(new Phrase("ABR", font2));
                    case "JOUR FÉRIER" -> cell.setPhrase(new Phrase("F", font2));
                    case "RUPTURE DE CONTRAT" -> cell.setPhrase(new Phrase("RUP", font2));
                    case "PRÉSENT DEMI JOURNEE" -> cell.setPhrase(new Phrase("P/A", font2));
                    case "ABSENCE IRREGULIERE" -> cell.setPhrase(new Phrase("ABI", font2));
                    case "ABSENCE  AUTORISEE" -> cell.setPhrase(new Phrase("ANR", font2));
                    case "MALADIE" -> cell.setPhrase(new Phrase("MAC", font2));
                    case "CONGÉ  MATERNITE" -> cell.setPhrase(new Phrase("MAT", font2));
                    case "FIN DE CONTRAT" -> cell.setPhrase(new Phrase("FC", font2));
                    case "MISE EN DISPONIBILITE" -> cell.setPhrase(new Phrase("MD", font2));
                    case "WEEK -END" -> cell.setPhrase(new Phrase("W", font2));
                    case "DÉTACHE" -> cell.setPhrase(new Phrase("DT", font2));
                    case "ÉLECTION" -> cell.setPhrase(new Phrase("EL", font2));
                    default -> cell.setPhrase(new Phrase("P", font2));
                }
                table.addCell(cell);
            }
            if (i<31){
                for (int j=i;j<31;j++){
                    cell.setBackgroundColor(Color.WHITE);
                    cell.setPhrase(new Phrase("", font2));
                    table.addCell(cell);
                }
            }

        }
        // Adding the created table to the document
        document.add(paragraph);
        document.add(paragraphDate);
        document.add(paragraphStructure);
        document.add(table);
        document.add(codeAndReference(document));
        // Closing the document
        document.close();

        reportService.saveReport(new Report(new Structure(att.getStructureId()),path, Calendar.getInstance().getTime(),false,"MENSUELLE"));

    }


    private PdfPTable codeAndReference(Document document) {
        document.newPage();
        reference = new PdfPTable(2);
        // Setting width of the table, its columns and spacing
        reference.setWidthPercentage(38);
        reference.setWidths(new int[] {8,30});
        reference.setSpacingBefore(5);
        reference.setHorizontalAlignment(Element.ALIGN_LEFT);
        // Create Table Cells for the table header
        cellCode = new PdfPCell();
        // Setting the background color and padding of the table cell
        cellCode.setBackgroundColor(new Color(0,0,0));
        cellCode.setPadding(5);
        // Creating font
        // Setting font style and size
        Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font1.setSize(10);
        font1.setColor(CMYKColor.WHITE);
        cellCode.setPhrase(new Phrase("code",font1));
        reference.addCell(cellCode);
        cellCode.setPhrase(new Phrase("Réference",font1));
        reference.addCell(cellCode);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(9);
        font.setColor(CMYKColor.BLACK);
        cellCode.setBackgroundColor(new Color(232,232,232));
        addCell("MUT","MUTATION",font);
        addCell("SUS","SUSPENDU DE SES FONCTIONS",font);
        addCell("FOR","FORMATION",font);
        addCell("CA","CONGÉ ANNUEL",font);
        addCell("CE","CONGÉ EXEPTIONNEL",font);
        addCell("RC","RÉCUPERATION",font);
        addCell("CS","CONGE  SANS SOLDE",font);
        addCell("MIS","MISSION COMMANDEE",font);
        addCell("ABR","ABSENCE REMUNEREE",font);
        addCell("F","JOUR FERIER",font);
        addCell("RUP","RUPTURE DE CONTRAT",font);
        addCell("P/A","PRÉSENT DEMI JOURNEE",font);
        addCell("ABI","ABSENCE IRREGULIERE",font);
        addCell("ANR","ABSENCE  AUTORISEE",font);
        addCell("MAC","MALADIE ",font);
        addCell("P","PRÉSENT",font);
        addCell("MAT","CONGÉ  MATERNITE",font);
        addCell("FC","FIN DE CONTRAT ",font);
        addCell("MD","MISE EN DISPONIBILITE",font);
        addCell("W","WEEK –END",font);
        addCell("DT","DÉTACHE",font);
        addCell("EL","ÉLECTION",font);

        return reference;
    }

    private void addCell(String code,String r,Font font) {
        font.setColor(CMYKColor.BLACK);
        cellCode.setPhrase(new Phrase(code,font));
        cellCode.setBackgroundColor(Color.WHITE);
        reference.addCell(cellCode);
        cellCode.setBackgroundColor(Color.WHITE);
        cellCode.setPhrase(new Phrase(r,font));
        reference.addCell(cellCode);
    }

    private List<AttendanceByStructure> getAttendances(){

        List<Tuple> list=attendanceRepository.getAttendances();

        List<AttendanceByStructure> attendance=new ArrayList<>();

        long ids=-2;
        String struct="";

        if (list!=null){

            for (Tuple row : list){
                Long id=(Long) row.get("employee_id");
                Date date=(Date) row.get("date");
                String stat=(String) row.get("stat");
                String name=(String) row.get("name");
                String firstName=(String) row.get("first_name");
                String structure=(String) row.get("designation_struct");
                Long structure_id=(Long) row.get("structure_id");
                if (!structure.equals(struct)){
                    //structure employee's list
                    List<EmployeeAttendance> attendanceE=new ArrayList<>();
                    //stats list of the first employee
                    List<String> stats=new ArrayList<>();
                    stats.add(stat);
                    attendanceE.add(new EmployeeAttendance(id,name,firstName,date,stats));
                    //create the first structure
                    attendance.add(new AttendanceByStructure(structure,structure_id,attendanceE));
                    //make a new value in struct
                    struct=structure;
                    ids=id;
                }else{
                    if (ids==id){
                        //we're in the same employee
                        List<EmployeeAttendance> attendanceS1=attendance.get(attendance.size()-1).getAttendanceS();
                        attendanceS1.get(attendanceS1.size()-1).getStats().add(stat);
                    }else{
                        //stats list of an employee
                        List<String> stats=new ArrayList<>();
                        stats.add(stat);
                        //An employee attendance's
                        EmployeeAttendance employee=new EmployeeAttendance(id,name,firstName,date,stats);
                        //add the new employee to the same structure
                        attendance.get(attendance.size()-1).getAttendanceS().add(employee);
                        //make a new value in ids
                        ids=id;
                    }

                }

            }
        }

        return attendance;
    }


    private String directionFormat(String s){
        // Convert the string to a StringBuilder
        StringBuilder stringBuilder = new StringBuilder(s);

        for (int i=0;i<s.length();i++){
            if (s.charAt(i)==' ')
                stringBuilder.setCharAt(i,'_');
        }
        return stringBuilder.toString();
    }

}



