public class Main {
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("=== OTEL REZERVASYON SİSTEMİ TESTİ ===");
        System.out.println("============================================");
        System.out.println();

        // ---------------------------------------------------------
        // BÖLÜM 1: ODA VE FİYAT TESTLERİ
        // ---------------------------------------------------------
        System.out.println("--- 1. Adım: Oda Fiyatlarının Kontrolü ---");

        // Standart oda oluşturup fiyatını doğruluyorum.
        StandardRoom stdOda = new StandardRoom(101, 1000.0);
        System.out.println("Standart Oda (No: 101) Fiyatı: " + stdOda.calculatePrice() + " TL");

        // Deluxe oda oluşturup %20 zammın yansıyıp yansımadığını kontrol ediyorum.
        DeluxeRoom luksOda = new DeluxeRoom(201, 1000.0);
        System.out.println("Deluxe Oda (No: 201) Fiyatı: " + luksOda.calculatePrice() + " TL");

        System.out.println(); // Boşluk

        // ---------------------------------------------------------
        // BÖLÜM 2: REZERVASYON SİSTEMİ TESTİ
        // ---------------------------------------------------------
        System.out.println("--- 2. Adım: Rezervasyon Oluşturma ---");

        // Sisteme bir müşteri tanımlıyorum.
        Customer musteri = new Customer(1, "Şevval Yılmaz", "0553-123-4567");
        System.out.println("Müşteri Tanımlandı: " + musteri.getName());

        // Yukarıda oluşturduğum Deluxe odayı (luksOda) Şevval Hanım'a 5 geceliğine rezerve ediyorum.
        // Sistem; Müşteri, Oda ve Gece sayısını alıp toplam tutarı hesaplamalı.
        Reservation yeniRezervasyon = new Reservation(musteri, luksOda, "2.01.2026", 5);

        System.out.println();
        // Rezervasyonun tüm detaylarını (toString metodu ile) ekrana yazdırıyorum.
        System.out.println(yeniRezervasyon.toString());

        System.out.println();
        System.out.println("=== TÜM TESTLER BAŞARIYLA TAMAMLANDI ===");
    }
}