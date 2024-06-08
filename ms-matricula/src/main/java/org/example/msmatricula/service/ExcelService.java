package org.example.msmatricula.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.msmatricula.entity.Matricula;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream generateMatriculasExcel(List<Matricula> matriculas) {
        String[] COLUMNs = {"ID", "Nombre", "Apellidos", "Grado"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Matriculas");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Create data rows
            int rowIdx = 1;
            for (Matricula matricula : matriculas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(matricula.getId());
                row.createCell(1).setCellValue(matricula.getAlumnoDto().getNombre());
                row.createCell(2).setCellValue(matricula.getAlumnoDto().getApellidos());
                row.createCell(3).setCellValue(matricula.getAlumnoDto().getGrado().getNombre());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);
        return headerCellStyle;
    }
}
