public class Main {

    // --- ANA METOT (Buradan sistemin genel kontrolünü yapıyorum) ---
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("=== OTEL REZERVASYON SİSTEMİ KONTROLÜ ===");
        System.out.println("============================================");
        System.out.println();

        // 1. AŞAMA: Önce temel sınıfların (Oda, Müşteri) tek başına doğru çalışıp çalışmadığına bakıyorum.
        runUnitTests();

        System.out.println();
        System.out.println("********************************************");
        System.out.println();

        // 2. AŞAMA: Şimdi de Hotel sınıfını devreye sokup genel sistemi test ediyorum.
        runIntegrationTests();

        System.out.println();
        System.out.println("=== TÜM İŞLEMLER SORUNSUZ TAMAMLANDI ===");
    }

    // ----------------------------------------------------------------
    // BÖLÜM 1: TEMEL KONTROLLER (Oda Fiyatları ve Tekil Rezervasyon)
    // ----------------------------------------------------------------
    public static void runUnitTests() {
        System.out.println(">>> BÖLÜM 1: ODA VE REZERVASYON MANTIĞI <<<");
        System.out.println();

        // ADIM 1: Fiyatlar doğru hesaplanıyor mu?
        System.out.println("--- Adım 1: Oda Fiyat Hesaplamaları ---");

        StandardRoom stdOda = new StandardRoom(101, 1000.0);
        System.out.println("Standart Oda (No: 101) Fiyatı: " + stdOda.calculatePrice() + " TL");

        DeluxeRoom luksOda = new DeluxeRoom(201, 1000.0);
        System.out.println("Deluxe Oda (No: 201) Fiyatı: " + luksOda.calculatePrice() + " TL (Beklenen: %20 zamlı)");

        System.out.println();

        // ADIM 2: Rezervasyon yapabiliyor muyum?
        System.out.println("--- Adım 2: Örnek Rezervasyon Oluşturuyorum ---");

        Customer musteri1 = new Customer(1, "Şevval Yılmaz", "0553-123-4567");
        System.out.println("Müşteri: " + musteri1.getName());

        // Şevval Hanım için rezervasyon yapıyorum
        Reservation rez1 = new Reservation(musteri1, luksOda, "2.01.2026", 5);
        System.out.println();
        System.out.println(rez1.toString());

        System.out.println("--------------------------------------------");

        // ADIM 3: Dolu odayı başkasına vermeyi engelliyor mu?
        System.out.println("--- Adım 3: Çifte Rezervasyon (Hata) Kontrolü ---");

        Customer musteri2 = new Customer(2, "Mehmet Bey", "555-999-8888");
        System.out.println("Yeni Müşteri: " + musteri2.getName());
        System.out.println(">> Mehmet Bey, Şevval Hanım'ın tuttuğu odayı (201) almaya çalışıyor...");

        // Burada sistemin HATA vermesini bekliyorum.
        Reservation rez2 = new Reservation(musteri2, luksOda, "03.01.2026", 2);
    }

    // ----------------------------------------------------------------
    // BÖLÜM 2: GENEL SİSTEM TESTİ (Otel Sınıfı ve Listeleme)
    // ----------------------------------------------------------------
    public static void runIntegrationTests() {
        System.out.println(">>> BÖLÜM 2: OTEL YÖNETİMİ VE LİSTELEME SİSTEMİ <<<");
        System.out.println();

        // 1. Otelimi kuruyorum
        Hotel otel = new Hotel("Skyline Hotel");
        System.out.println(">> Otel Kuruldu: " + "Skyline Hotel");

        // 2. Odaları oluşturup otele ekliyorum
        // Fiyatları standart tutuyorum: Standart 1000 TL, Deluxe 1000 TL (Hesaplayınca 1200 TL olacak)
        Room r1 = new StandardRoom(100, 1000.0);
        Room r2 = new StandardRoom(105, 1000.0);
        Room r3 = new DeluxeRoom(500, 1000.0); // Kral Dairesi

        otel.addRoom(r1);
        otel.addRoom(r2);
        otel.addRoom(r3);

        System.out.println(">> Sisteme 3 adet oda ekledim (100, 105, 500).");

        // 3. Müsait odaları listeliyorum (Hepsi gelmeli)
        otel.listAvailableRooms();

        // 4. Müşteri geliyor ve odayı tutuyor
        System.out.println(">> Müşteri (Zeynep Kaya) Oda 100'ü kiralamak istiyor...");

        Customer c = new Customer(99, "Zeynep Kaya", "0555-444-3322");

        // Rezervasyon yapıldığı an oda durumu "Dolu"ya çekilmeli
        new Reservation(c, r1, "10.05.2026", 3);

        // 5. Listeyi tekrar kontrol ediyorum
        System.out.println(">> GÜNCEL MÜSAİT ODA LİSTESİ (100 Numaralı oda artık listede olmamalı):");
        otel.listAvailableRooms();
    }
}