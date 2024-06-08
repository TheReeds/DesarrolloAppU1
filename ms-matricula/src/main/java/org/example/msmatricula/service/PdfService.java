package org.example.msmatricula.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.msmatricula.entity.Matricula;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfService {

    public ByteArrayInputStream generateMatriculasPdf(List<Matricula> matriculas) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Lista de Estudiantes Matriculados", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            // Create table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 3});

            // Add table headers
            Stream.of("ID", "Nombre", "Apellidos", "Grado")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            // Add rows
            for (Matricula matricula : matriculas) {
                table.addCell(String.valueOf(matricula.getId()));
                table.addCell(matricula.getAlumnoDto().getNombre());
                table.addCell(matricula.getAlumnoDto().getApellidos());
                table.addCell(matricula.getAlumnoDto().getGrado().getNombre());
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream generateConstanciaMatriculaPdf(Matricula matricula) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add header with logo and other images
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{7, 3});

            // Left side of header
            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.addElement(new Paragraph("Ministerio De Educación", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
            leftCell.addElement(new Paragraph("Dirección Regional de Educación Puno", FontFactory.getFont(FontFactory.HELVETICA, 10)));
            leftCell.addElement(new Paragraph("Unidad de Gestión Educativa Local San Román", FontFactory.getFont(FontFactory.HELVETICA, 10)));
            leftCell.addElement(new Paragraph("“Año del Bicentenario y de la consolidación de nuestra Independencia”", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            leftCell.addElement(new Paragraph("“Puno Capital Andina del Perú”", FontFactory.getFont(FontFactory.HELVETICA, 10)));
            table.addCell(leftCell);

            // Right side of header with logo
            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);
            Image logo = Image.getInstance("path/to/logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_RIGHT);
            rightCell.addElement(logo);
            table.addCell(rightCell);

            document.add(table);

            document.add(Chunk.NEWLINE);

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Paragraph title = new Paragraph("CONSTANCIA DE MATRÍCULA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Content
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            String contentText = String.format(
                    "EL DIRECTOR DE LA INSTITUCIÓN EDUCATIVA N° 123456 SANTA MARIA, DISTRITO DE JULIACA, PROVINCIA DE SAN ROMÁN, REGIÓN PUNO, QUE AL FINAL SUSCRIBE.\n\nHACE CONSTAR:\n\n" +
                            "Que, la alumna(o) %s, %s, se encuentra matriculado(a) en esta Institución Educativa N°123456 SANTA MARÍA de Juliaca, en el  %s de educación en el presente año %s, el cual consta en su matrícula.\n\n" +
                            "DNI del educando: %s\n\n" +
                            "Se emite la presente constancia de matrícula a solicitud del interesado, para los fines que estime por conveniente.\n\n" +
                            "Juliaca, %s.",
                    matricula.getAlumnoDto().getApellidos(),
                    matricula.getAlumnoDto().getNombre(),
                    matricula.getAlumnoDto().getGrado().getNombre(),
                    LocalDate.now().getYear(),
                    matricula.getAlumnoDto().getDni(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"))
            );
            Paragraph content = new Paragraph(contentText, contentFont);
            content.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(content);

            document.add(Chunk.NEWLINE);

            // Signature
            Paragraph signature = new Paragraph("_________________________\nFirma del Director", contentFont);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
