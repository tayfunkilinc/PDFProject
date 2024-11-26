package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends pdfKelimeArama{
    public static void main(String[] args) {
        String girilecekDeger="";
       while (!girilecekDeger.equals("X")) {
           String klasorYolu = "src/main/resources/RefundCreditsCard";  // PDF klasörünün yolu
           String standardKelime = "-$";
           Scanner input = new Scanner(System.in);
           System.out.println();
           System.out.println("<<<<<<<<<---------------->>>>>>>>");
           System.err.print("---> | Aranan Kelime: ");
           girilecekDeger = input.nextLine();
           String arananKelime = standardKelime + girilecekDeger;    // Aranacak kelime
           pdfKelimeArama(klasorYolu, arananKelime);
       }
    }
}