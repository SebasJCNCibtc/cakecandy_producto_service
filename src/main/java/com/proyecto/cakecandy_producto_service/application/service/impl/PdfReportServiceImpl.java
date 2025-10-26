package com.proyecto.cakecandy_producto_service.application.service.impl;

import com.proyecto.cakecandy_producto_service.domain.model.Producto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfReportServiceImpl {

    public byte[] generateProductsReport(List<Producto> productos) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                addWatermark(contentStream, page);
                addHeader(contentStream);

                writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16, 50, 720, "Reporte de Inventario de Productos");
                drawTable(contentStream, productos, 700); // Ajustamos 'y' inicial
                addFooter(contentStream, 1);
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private void addHeader(PDPageContentStream contentStream) throws IOException {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10, 450, 790, "Fecha: " + fecha);
        writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12, 50, 790, "Cake Candy");
    }

    private void addFooter(PDPageContentStream contentStream, int pageNum) throws IOException {
        writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10, 270, 30, "Página " + pageNum);
    }

    private void addWatermark(PDPageContentStream contentStream, PDPage page) throws IOException {
        PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
        gs.setNonStrokingAlphaConstant(0.1f);
        contentStream.setGraphicsStateParameters(gs);
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 120);
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.beginText();
        contentStream.transform(new org.apache.pdfbox.util.Matrix((float) Math.cos(Math.toRadians(45)), (float) Math.sin(Math.toRadians(45)), -(float) Math.sin(Math.toRadians(45)), (float) Math.cos(Math.toRadians(45)), page.getMediaBox().getWidth() / 4, page.getMediaBox().getHeight() / 4));
        contentStream.showText("Cake Candy");
        contentStream.endText();
        gs.setNonStrokingAlphaConstant(1.0f);
        contentStream.setGraphicsStateParameters(gs);
        contentStream.setNonStrokingColor(Color.BLACK);
    }

    // --- MÉTODO DE TABLA MEJORADO ---
    private void drawTable(PDPageContentStream contentStream, List<Producto> productos, float y) throws IOException {
        final int rows = productos.size() + 1;
        final float rowHeight = 20f;
        final float tableWidth = 500f;
        final float margin = 50;

        float[] colWidths = {40, 260, 50, 70, 80}; // Anchos de columna

        // Dibuja el fondo de la cabecera
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.addRect(margin, y - rowHeight, tableWidth, rowHeight);
        contentStream.fill();

        // Dibuja las líneas de las filas
        contentStream.setStrokingColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, y - (i * rowHeight));
            contentStream.lineTo(margin + tableWidth, y - (i * rowHeight));
            contentStream.stroke();
        }

        // Dibuja las líneas de las columnas
        float nextX = margin;
        for (float colWidth : colWidths) {
            contentStream.moveTo(nextX, y);
            contentStream.lineTo(nextX, y - (rows * rowHeight));
            contentStream.stroke();
            nextX += colWidth;
        }
        contentStream.moveTo(margin + tableWidth, y);
        contentStream.lineTo(margin + tableWidth, y - (rows * rowHeight));
        contentStream.stroke();

        // Escribe el texto de la cabecera
        contentStream.setNonStrokingColor(Color.WHITE);
        String[] headers = {"ID", "Nombre del Producto", "Stock", "P. Costo", "P. Venta"};
        float textX = margin + 5;
        float textY = y - 15;
        for(int i = 0; i < headers.length; i++) {
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10, textX, textY, headers[i]);
            textX += colWidths[i];
        }

        // Escribe el contenido de las filas
        contentStream.setNonStrokingColor(Color.BLACK);
        textY -= rowHeight;
        for (Producto producto : productos) {
            textX = margin + 5;
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9, textX, textY, String.valueOf(producto.getIdProducto()));
            textX += colWidths[0];
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9, textX, textY, producto.getNombreProducto());
            textX += colWidths[1];
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9, textX, textY, String.valueOf(producto.getStock()));
            textX += colWidths[2];
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9, textX, textY, "S/ " + producto.getPrecioCosto());
            textX += colWidths[3];
            writeText(contentStream, new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9, textX, textY, "S/ " + producto.getPrecioVenta());
            textY -= rowHeight;
        }
    }

    // --- MÉTODO 'writeText' UNIFICADO ---
    private void writeText(PDPageContentStream stream, PDType1Font font, int fontSize, float x, float y, String text) throws IOException {
        stream.setFont(font, fontSize);
        stream.beginText();
        stream.newLineAtOffset(x, y);
        stream.showText(text != null ? text : "");
        stream.endText();
    }
}