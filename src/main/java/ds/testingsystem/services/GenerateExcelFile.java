package ds.testingsystem.services;

import ds.testingsystem.database.model.Group;
import ds.testingsystem.database.model.Test;
import ds.testingsystem.database.model.beans.UserTestPoints;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateExcelFile {
    public static String generateExcel(String fullPathName, HashMap<Group, LinkedList<UserTestPoints>> results, Test test){
        Path filePath = Paths.get(fullPathName+test.getName()+"_.xls");
        try {
            Workbook workbook = new HSSFWorkbook();
            for(Map.Entry<Group, LinkedList<UserTestPoints>> entry:results.entrySet()){
                Sheet sheet = workbook.createSheet(entry.getKey().getName());
                addHeaderRow(sheet, workbook);
                int i = 1;
                for(UserTestPoints utp : entry.getValue()){
                    addStudentRow(i++, sheet, workbook, utp);
                }
            }
            FileOutputStream fos = new FileOutputStream(filePath.toFile());
            workbook.write(fos);
        } catch (Exception e){
            e.printStackTrace();
        }
        return filePath.toAbsolutePath().toString();
    }

    private static void addHeaderRow(Sheet sheet, Workbook workbook){
        Row rowHeader = sheet.createRow(0);
        //CreateStyle
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.index);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //Create cells
        Cell firstName = rowHeader.createCell(0);
        firstName.setCellStyle(headerStyle);
        firstName.setCellValue("Ім'я");

        Cell lastName = rowHeader.createCell(1);
        lastName.setCellStyle(headerStyle);
        lastName.setCellValue("Прізвище");

        Cell points = rowHeader.createCell(2);
        points.setCellStyle(headerStyle);
        points.setCellValue("Оцінка");

        Cell datetime = rowHeader.createCell(3);
        datetime.setCellStyle(headerStyle);
        datetime.setCellValue("Дата та час оцінювання");
    }

    private static void addStudentRow(int i, Sheet sheet, Workbook workbook, UserTestPoints utp){
        Row row = sheet.createRow(i);
        //Create style
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        //Create cells
        Cell firstName = row.createCell(0);
        firstName.setCellStyle(style);
        firstName.setCellValue(utp.getUser().getFirstName());

        Cell lastName = row.createCell(1);
        lastName.setCellStyle(style);
        lastName.setCellValue(utp.getUser().getLastName());

        Cell points = row.createCell(2);
        points.setCellStyle(style);
        points.setCellValue(utp.getPoints());

        Cell datetime = row.createCell(3);
        datetime.setCellStyle(style);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm");
        datetime.setCellValue(utp.getDateTime().format(formatter));
    }
    public static String rfc5987_encode(final String s) {
        final byte[] s_bytes = s.getBytes(StandardCharsets.UTF_8);
        final int len = s_bytes.length;
        final StringBuilder sb = new StringBuilder(len << 1);
        final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        final byte[] attr_char = {'!', '#', '$', '&', '+', '-', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '|', '~'};
        for (final byte b : s_bytes) {
            if (Arrays.binarySearch(attr_char, b) >= 0)
                sb.append((char) b);
            else {
                sb.append('%');
                sb.append(digits[0x0f & (b >>> 4)]);
                sb.append(digits[b & 0x0f]);
            }
        }

        return sb.toString();
    }
}
