package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;

public class pdfKelimeArama {
    // ANSI escape kodları
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m"; // Renk sıfırlama
    private static final String ANSI_CYAN = "\u001B[36m"; // Cyan renk

    public static void pdfKelimeArama(String klasorYolu, String arananKelime) {
        File klasor = new File(klasorYolu);
        File[] pdfDosyalari = klasor.listFiles((dir, name) -> name.endsWith(".pdf"));
        Map<String, List<String>> bulunanPdfDetaylari = new HashMap<>(); // PDF, geçiş detayları

        if (pdfDosyalari != null && pdfDosyalari.length > 0) {
            // Başlık ve çizgiler
            System.out.println(ANSI_CYAN + "----------------------------------------");
            System.out.println("Toplam " + pdfDosyalari.length + " PDF dosyası içinde arama yapılıyor...");
            System.out.println("----------------------------------------" + ANSI_RESET);

            for (File pdfDosyasi : pdfDosyalari) {
                try (PDDocument document = PDDocument.load(pdfDosyasi)) {
                    PDFTextStripper pdfTextStripper = new PDFTextStripper();

                    List<String> detaylar = new ArrayList<>(); // Geçiş detayları

                    for (int i = 1; i <= document.getNumberOfPages(); i++) {
                        pdfTextStripper.setStartPage(i);
                        pdfTextStripper.setEndPage(i);

                        String sayfaMetni = pdfTextStripper.getText(document);

                        // Satır satır işlemi yapmak için sayfa metnini satırlara ayırıyoruz
                        String[] satirlar = sayfaMetni.split("\n");
                        for (int satirNumarasi = 0; satirNumarasi < satirlar.length; satirNumarasi++) {
                            String satir = satirlar[satirNumarasi];

                            // Satırda aranan kelimeyi kontrol et
                            if (satir.contains(arananKelime)) {
                                // İlk 6 karakteri al
                                String baslangic = satir.length() > 6 ? satir.substring(0, 6) : satir;

                                // Detayı kaydet
                                String detay = "-> " + baslangic + " ... " + ANSI_CYAN + "(Satır: " + (satirNumarasi + 1) + ")" + ANSI_RESET;
                                detaylar.add(detay); // Detayı ekle
                            }
                        }
                    }

                    if (!detaylar.isEmpty()) {
                        bulunanPdfDetaylari.put(pdfDosyasi.getName(), detaylar); // PDF ve detayları
                    }
                } catch (IOException e) {
                    System.err.println("Dosya okunurken hata oluştu: " + pdfDosyasi.getName());
                    e.printStackTrace();
                }
            }

            // Arama sonuçları
            System.out.println("\nBulunan PDF Dosyaları:");
            if (!bulunanPdfDetaylari.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : bulunanPdfDetaylari.entrySet()) {
                    String dosyaAdi = entry.getKey();
                    List<String> detaylar = entry.getValue();

                    // PDF ismini yazdır
                    System.out.println(ANSI_BLUE + dosyaAdi + ANSI_RESET);

                    // Detayları yazdır
                    for (String detay : detaylar) {
                        System.out.println(ANSI_CYAN + detay + ANSI_RESET); // Detayları yazdır
                    }
                }
            } else {
                System.out.println("Aranan kelime hiçbir PDF dosyasında bulunamadı.");
            }
        } else {
            System.out.println("Belirtilen klasörde PDF dosyası bulunamadı veya klasör yanlış.");
        }
    }
}
