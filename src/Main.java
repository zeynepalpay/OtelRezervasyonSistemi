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
        // BÖLÜM 2: REZERVASYON SİSTEMİ (BAŞARILI SENARYO)
        // ---------------------------------------------------------
        System.out.println("--- 2. Adım: Rezervasyon Oluşturma (Şevval Yılmaz) ---");

        // Sisteme bir müşteri tanımlıyorum.
        Customer musteri1 = new Customer(1, "Şevval Yılmaz", "0553-123-4567");
        System.out.println("Müşteri Tanımlandı: " + musteri1.getName());

        // Şevval Hanım, 201 numaralı Deluxe odayı (luksOda) tutuyor.
        // BEKLENEN: "Oda 201 için rezervasyon yapıldı." yazısı çıkmalı.
        Reservation rez1 = new Reservation(musteri1, luksOda, "2.01.2026", 5);

        System.out.println();
        System.out.println(rez1.toString());

        System.out.println("--------------------------------------------");

        // ---------------------------------------------------------
        // BÖLÜM 3: ÇAKIŞMA TESTİ (BAŞARISIZ SENARYO)
        // ---------------------------------------------------------
        System.out.println("--- 3. Adım: Aynı Odaya İkinci Kişi (HATA KONTROLÜ) ---");

        // Yeni bir müşteri: Mehmet Bey
        Customer musteri2 = new Customer(2, "Mehmet Bey", "555-999-8888");
        System.out.println("Yeni Müşteri Geldi: " + musteri2.getName());

        System.out.println(">> Mehmet Bey, Şevval Hanım'ın odasını (201) istiyor...");

        // Mehmet Bey, AZ ÖNCE DOLAN 'luksOda'yı (201) tutmaya çalışıyor.
        // BEKLENEN: "HATA: Oda 201 zaten dolu!" yazısı çıkmalı.
        Reservation rez2 = new Reservation(musteri2, luksOda, "03.01.2026", 2);

        System.out.println();
        System.out.println("=== TÜM TESTLER BAŞARIYLA TAMAMLANDI ===");
    }
}