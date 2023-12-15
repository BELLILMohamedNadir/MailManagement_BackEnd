package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.DailyAttendance;
import com.example.MailManagementApi.helper_classes.ReportResponse;
import com.example.MailManagementApi.model.*;
import com.example.MailManagementApi.repository.AttendanceRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class DailyReportServiceGeneratorImpl implements DailyReportServiceGenerator {


    @Autowired
    ReportService reportService;
    @Autowired
    AttendanceRepository attendanceRepository;

    PdfPTable reference;
    PdfPCell cellCode;

    @Override
    public ReportResponse generate(long structure_id) throws IOException {
        List<DailyAttendance> attendance=getAttendances(structure_id);
        if (attendance!=null && attendance.size()>0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String path = "listOfPresence_" + simpleDateFormat.format(Calendar.getInstance().getTime())
                    + "_" + directionFormat(attendance.get(0).getStructure()) + "daily.pdf";
            Date date = attendance.get(0).getDate();
            // Creating the Object of Document
            Document document = new Document(PageSize.A4);
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
            Paragraph paragraphDate = new Paragraph("Date :" + simpleDateFormat.format(date), fontDate);
            paragraphDate.setAlignment(Element.ALIGN_LEFT);
            paragraphDate.setSpacingBefore(10);
            Paragraph paragraphStructure = new Paragraph("Structure :" + attendance.get(0).getStructure(), fontDate);
            paragraphDate.setAlignment(Element.ALIGN_LEFT);
            paragraphDate.setSpacingBefore(10);
            Paragraph paragraph = new Paragraph("Liste des Présences", fontTitle);
            // Aligning the paragraph in the document
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.setSpacingBefore(60);
            // Creating a table of the 3 columns
            PdfPTable table = new PdfPTable(3);
            // Setting width of the table, its columns and spacing
            table.setWidthPercentage(50);
            table.setWidths(new int[]{20, 20, 10});
            table.setSpacingBefore(40);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
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
            cell.setPhrase(new Phrase("État", font));
            table.addCell(cell);


            Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font2.setSize(9);
            font2.setColor(CMYKColor.BLACK);
            // Iterating the list of attendance
            for (DailyAttendance dailyAttendance : attendance) {
                table.addCell(dailyAttendance.getName());
                table.addCell(dailyAttendance.getFirstName());

                cell.setPhrase(new Phrase());
                cell.setBackgroundColor(Color.WHITE);
                switch (dailyAttendance.getStat()) {
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
            // Adding the created table to the document
            document.add(paragraph);
            document.add(paragraphDate);
            document.add(paragraphStructure);
            document.add(table);
            document.add(codeAndReference(document));
            // Closing the document
            document.close();

            Report report = new Report(new Structure(attendance.get(0).getStructure_id()), path, date, true, "JOURNALIER");
            reportService.saveDailyReport(report, attendance.get(0).getStructure_id());

            return new ReportResponse(report.getId(), getPdfFile(report.getPath()), report.getDate(),simpleDateFormat.format(Calendar.getInstance().getTime()),"JOURNALIER", report.isApproved());
        }
        return new ReportResponse();
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

    private List<DailyAttendance> getAttendances(long structure_id){
        List<Tuple> list=attendanceRepository.getDailyAttendance(structure_id);
        List<DailyAttendance> attendance=new ArrayList<>();
        for (Tuple row : list){
            Date date=(Date) row.get("date");
            String stat=(String) row.get("stat");
            String name=(String) row.get("name");
            String firstName=(String) row.get("first_name");
            String structure=(String) row.get("designation_struct");
            Long struct_id=(Long) row.get("structure_id");
            attendance.add(new DailyAttendance(date,stat,name,firstName,structure,struct_id));
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

    public byte[] getPdfFile(String p) {
        try {
            File file = new File(p);
            Path path = file.toPath();
            byte[] pdfBytes = Files.readAllBytes(path);
            return pdfBytes;
        } catch (IOException e) {
            // handle exception
        }
        return null;
    }
}
