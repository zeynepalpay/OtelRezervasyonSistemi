public class Main {

    //  ANA METOT
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("=== SKYLINE HOTEL - SİSTEM KONTROLÜ ===");
        System.out.println("============================================");
        System.out.println();

        // 1. AŞAMA: Ödeme ve Rezervasyon Mantığı Testi
        runUnitTests();

        System.out.println();
        System.out.println("********************************************");
        System.out.println();

        // 2. AŞAMA: Otel ve Odaların Genel Testi
        runIntegrationTests();

        System.out.println();
        System.out.println("=== TÜM İŞLEMLER SORUNSUZ TAMAMLANDI ===");
    }

    // ----------------------------------------------------------------
    // BÖLÜM 1: TEMEL KONTROLLER (Ödeme Sistemi Testi)
    // ----------------------------------------------------------------
    public static void runUnitTests() {
        System.out.println(">>> BÖLÜM 1: ÖDEME VE REZERVASYON TESTİ <<<");
        System.out.println();

        // Odayı hazırlıyorum
        DeluxeRoom luksOda = new DeluxeRoom(201, 1000.0);
        System.out.println("Oda Fiyatı: " + luksOda.calculatePrice() + " TL (Deluxe)");

        // ADIM 1: Başarılı Ödeme Senaryosu
        System.out.println("--- Adım 1: Doğru Kart Numarasıyla Rezervasyon ---");

        Customer musteri1 = new Customer(1, "Şevval Yılmaz", "0553-123-4567");
        System.out.println("Müşteri: " + musteri1.getName());

        // DİKKAT: Artık sona "1234123412341234" diye 16 haneli kart no ekledim.
        // Sistem bunu kabul etmeli.
        Reservation rez1 = new Reservation(musteri1, luksOda, "2.01.2026", 5, "1234123412341234");

        System.out.println();
        System.out.println(rez1.toString()); // Fişi yazdırıyorum

        System.out.println("--------------------------------------------");

        // ADIM 2: Hatalı Kart Testi (Eksik numara)
        System.out.println("--- Adım 2: Hatalı Kart Denemesi (Ödeme Reddedilmeli) ---");

        Customer musteri2 = new Customer(2, "Mehmet Bey", "555-999-8888");
        System.out.println("Müşteri: " + musteri2.getName());

        System.out.println(">> Mehmet Bey eksik numaralı (hatalı) kartla deniyor...");

        // Kart numarası olarak "123" giriyorum (16 hane değil)
        // Beklenen: Sistemin "HATA" vermesi ve rezervasyonu yapmaması.
        new Reservation(musteri2, luksOda, "03.01.2026", 2, "123");
    }

    // ----------------------------------------------------------------
    // BÖLÜM 2: GENEL SİSTEM TESTİ (Skyline Hotel Entegrasyonu)
    // ----------------------------------------------------------------
    public static void runIntegrationTests() {
        System.out.println(">>> BÖLÜM 2: OTEL VE ÖDEME ENTEGRASYONU <<<");
        System.out.println();

        // 1. Otelimi kuruyorum
        Hotel otel = new Hotel("Skyline Hotel");

        // Odaları ekliyorum
        Room r1 = new StandardRoom(100, 1000.0);
        Room r2 = new StandardRoom(105, 1000.0);
        Room r3 = new DeluxeRoom(500, 1000.0);

        otel.addRoom(r1);
        otel.addRoom(r2);
        otel.addRoom(r3);

        System.out.println(">> Otel ve Odalar Hazır.");
        otel.listAvailableRooms();

        // 2. Müşteri Rezervasyonu (Başarılı)
        System.out.println(">> Müşteri (Zeynep Kaya) Oda 100'ü kiralamak istiyor...");

        Customer c = new Customer(99, "Zeynep Kaya", "0555-444-3322");

        // Geçerli bir kart numarası giriyorum, ödeme alınsın.
        new Reservation(c, r1, "10.05.2026", 3, "1111222233334444");

        // 3. Kontrol ediyorum
        System.out.println(">> GÜNCEL MÜSAİT ODA LİSTESİ (100 Numaralı oda gitmiş olmalı):");
        otel.listAvailableRooms();
    }
}