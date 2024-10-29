package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends pdfKelimeArama{
    public static void main(String[] args) {
        String klasorYolu = "src/main/resources/RefundCreditsCard";  // PDF klasörünün yolu
        String standardKelime = "-$";
        String arananKelime = standardKelime + "342.30";    // Aranacak kelime
        pdfKelimeArama(klasorYolu, arananKelime);
    }
}