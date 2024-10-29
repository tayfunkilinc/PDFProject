package org.example;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;

public class pdfKelimeArama {
    // ANSI escape kodları
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m"; // Renk sıfırlama

    public static void pdfKelimeArama(String klasorYolu, String arananKelime) {
        File klasor = new File(klasorYolu);
        File[] pdfDosyalari = klasor.listFiles((dir, name) -> name.endsWith(".pdf"));

        if (pdfDosyalari != null && pdfDosyalari.length > 0) {
            System.out.println("PDF dosyaları bulundu: " + pdfDosyalari.length + " adet.");

            for (File pdfDosyasi : pdfDosyalari) {
                System.out.println("İşleniyor: " + pdfDosyasi.getName());
                try (PDDocument document = PDDocument.load(pdfDosyasi)) {
                    PDFTextStripper pdfTextStripper = new PDFTextStripper();

                    int sayfaSayisi = document.getNumberOfPages();
                    boolean kelimeBulundu = false;

                    for (int i = 1; i <= sayfaSayisi; i++) {
                        pdfTextStripper.setStartPage(i);
                        pdfTextStripper.setEndPage(i);

                        String sayfaMetni = pdfTextStripper.getText(document);
                        if (sayfaMetni.contains(arananKelime)) {
                            // Kırmızı renkte çıktı
                            System.out.println(ANSI_RED + "'" + arananKelime + "' kelimesi "
                                    + pdfDosyasi.getName() + " dosyasında, sayfa " + i + " içinde bulundu." + ANSI_RESET);
                            kelimeBulundu = true; // Kelime bulundu
                        }
                    }

                    if (!kelimeBulundu) {
                        System.out.println("'" + arananKelime + "' kelimesi " + pdfDosyasi.getName() + " dosyasında bulunamadı.");
                    }
                } catch (IOException e) {
                    System.err.println("Dosya okunurken hata oluştu: " + pdfDosyasi.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Belirtilen klasörde PDF dosyası bulunamadı veya klasör yanlış.");
        }
    }
}
