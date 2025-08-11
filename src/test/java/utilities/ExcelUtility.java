package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {

    private String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    // Check if file exists
    private void validateFileExists() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Excel file not found at: " + path);
        }
    }

    // Get total number of rows in a sheet
    public int getRowCount(String sheetName) throws IOException {
        validateFileExists();
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found.");
            }
            return sheet.getLastRowNum();
        }
    }

    // Get total number of cells in a row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        validateFileExists();
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found.");
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                throw new IOException("Row " + rownum + " not found in sheet '" + sheetName + "'.");
            }
            return row.getLastCellNum();
        }
    }

    // Read cell data
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        validateFileExists();
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found.");
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                throw new IOException("Row " + rownum + " not found.");
            }

            Cell cell = row.getCell(colnum);
            if (cell == null) {
                throw new IOException("Cell at row " + rownum + ", column " + colnum + " is empty.");
            }

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        }
    }

    // Write cell data
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File file = new File(path);

        XSSFWorkbook workbook;
        XSSFSheet sheet;
        Row row;
        Cell cell;

        // Create new file if not exists
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(sheetName);
            row = sheet.createRow(rownum);
            cell = row.createCell(colnum);
            cell.setCellValue(data);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
            workbook.close();
            return;
        }

        try (FileInputStream fi = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(fi);
        }

        // Get or create sheet
        if (workbook.getSheetIndex(sheetName) == -1) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.getSheet(sheetName);
        }

        // Get or create row
        row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        // Create cell
        cell = row.createCell(colnum);
        cell.setCellValue(data);

        try (FileOutputStream fo = new FileOutputStream(path)) {
            workbook.write(fo);
        }
        workbook.close();
    }

    // Fill cell with green background
    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        validateFileExists();
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found.");
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                throw new IOException("Row " + rownum + " not found.");
            }

            Cell cell = row.getCell(colnum);
            if (cell == null) {
                throw new IOException("Cell at row " + rownum + ", column " + colnum + " is empty.");
            }

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    // Fill cell with red background
    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        validateFileExists();
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found.");
            }

            Row row = sheet.getRow(rownum);
            if (row == null) {
                throw new IOException("Row " + rownum + " not found.");
            }

            Cell cell = row.getCell(colnum);
            if (cell == null) {
                throw new IOException("Cell at row " + rownum + ", column " + colnum + " is empty.");
            }

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }
}
