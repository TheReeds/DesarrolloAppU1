package org.example.mscurso.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.mscurso.dto.AulaNotaDto;
import org.example.mscurso.dto.NotaDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfService {

    public ByteArrayInputStream generateNotasPdf(AulaNotaDto aulaNotaDto) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Notas del Aula " + aulaNotaDto.getAula().getNombre(), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            // Create table
            PdfPTable table = new PdfPTable(aulaNotaDto.getCurso().getNumeroDePeriodos() + 2); // +2 for Alumno ID and Nombre
            table.setWidthPercentage(100);
            table.setWidths(getColumnWidths(aulaNotaDto.getCurso().getNumeroDePeriodos()));

            // Add table headers
            addTableHeader(table, aulaNotaDto.getCurso().getNumeroDePeriodos());

            // Add rows
            for (NotaDto nota : aulaNotaDto.getNotas()) {
                table.addCell(String.valueOf(nota.getAlumnoId()));
                table.addCell(getAlumnoNombre(nota.getAlumnoId()));
                for (int periodo = 1; periodo <= aulaNotaDto.getCurso().getNumeroDePeriodos(); periodo++) {
                    table.addCell(getNotaValor(aulaNotaDto.getNotas(), nota.getAlumnoId(), periodo));
                }
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private float[] getColumnWidths(int numPeriodos) {
        float[] widths = new float[numPeriodos + 2];
        widths[0] = 1; // Alumno ID
        widths[1] = 3; // Nombre
        for (int i = 2; i < widths.length; i++) {
            widths[i] = 1; // Periodos
        }
        return widths;
    }

    private void addTableHeader(PdfPTable table, int numPeriodos) {
        Stream.of("ID Alumno", "Nombre")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });
        for (int i = 1; i <= numPeriodos; i++) {
            PdfPCell header = new PdfPCell();
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase("Periodo " + i, headFont));
            table.addCell(header);
        }
    }

    private String getAlumnoNombre(Integer alumnoId) {
        // Implementar lógica para obtener el nombre del alumno según su ID
        return "Nombre del Alumno"; // Placeholder
    }

    private String getNotaValor(List<NotaDto> notas, Integer alumnoId, int periodo) {
        return notas.stream()
                .filter(n -> n.getAlumnoId().equals(alumnoId) && n.getPeriodo() == periodo)
                .map(n -> String.valueOf(n.getValor()))
                .findFirst()
                .orElse("0");
    }
}