public class Main {
    //  ANA METOT
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("=== SKYLINE HOTEL - SİSTEM KONTROLÜ ===");
        System.out.println("============================================");
        System.out.println();

        // 1. AŞAMA: Temel Ödeme ve Çıkış Testi
        runUnitTests();

        System.out.println();
        System.out.println("********************************************");
        System.out.println();

        // 2. AŞAMA: Otel, Oda Servisi ve İptal Testi
        runIntegrationTests();

        System.out.println();
        System.out.println("=== TÜM İŞLEMLER SORUNSUZ TAMAMLANDI ===");
    }

    // ----------------------------------------------------------------
    // BÖLÜM 1: TEMEL KONTROLLER (Ödeme Sistemi Testi)
    // ----------------------------------------------------------------
    public static void runUnitTests() {
        System.out.println(">>> BÖLÜM 1: ÇIKIŞ VE ÖDEME TESTİ <<<");
        System.out.println();

        // Odayı hazırlıyorum
        DeluxeRoom luksOda = new DeluxeRoom(201, 1000.0);

        // ADIM 1: Başarılı Ödeme Senaryosu
        System.out.println("--- Adım 1: Rezervasyon ve Başarılı Çıkış ---");

        Customer musteri1 = new Customer(1, "Şevval Yılmaz", "0553-123-4567");

        // DİKKAT: Artık burada kart numarası vermiyoruz! Sadece odayı tutuyoruz.
        Reservation rez1 = new Reservation(musteri1, luksOda, "2.01.2026", 5);

        // Çıkış yaparken kartı veriyoruz
        System.out.println(">> Şevval Hanım çıkış yapıyor...");
        rez1.checkOut("1234123412341234"); // Geçerli kart

        System.out.println("--------------------------------------------");

        // ADIM 2: Hatalı Kart Testi
        System.out.println("--- Adım 2: Hatalı Kart Denemesi (Ödeme Reddedilmeli) ---");

        Customer musteri2 = new Customer(2, "Mehmet Bey", "555-999-8888");
        // Odayı tekrar boşa çıkaralım (manuel olarak) çünkü az önce tuttuk
        luksOda.cancelReservation();

        Reservation rez2 = new Reservation(musteri2, luksOda, "03.01.2026", 2);

        System.out.println(">> Mehmet Bey eksik numaralı kartla ödeme deniyor...");

        // Kart numarası hatalı olduğu için checkOut içinde hata mesajı vermeli
        rez2.checkOut("123");
    }

    // ----------------------------------------------------------------
    // BÖLÜM 2: GENEL SİSTEM TESTİ (FULL SENARYO)
    // ----------------------------------------------------------------
    public static void runIntegrationTests() {
        System.out.println(">>> BÖLÜM 2: ODA SERVİSİ VE ENTEGRASYON <<<");
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

        // ---------------------------------------------------------
        // SENARYO A: ODA SERVİSİ VE BAŞARILI KONAKLAMA
        // ---------------------------------------------------------
        System.out.println("\n--- Senaryo A: Rezervasyon ve Oda Servisi Siparişi---");
        Customer c = new Customer(99, "Zeynep Kaya", "0555-444-3322");

        // Kart no olmadan rezervasyon
        Reservation rez = new Reservation(c, r1, "10.05.2026", 3);

        // YENİ: Oda Servisi Çağırılıyor 🍔
        System.out.println("\n>> Müşteri acıktı, sipariş veriyor...");
        MenuItem burger = new MenuItem("Cheese Burger", 250.0);
        MenuItem kola = new MenuItem("Coca Cola", 50.0);

        rez.addOrder(burger);
        rez.addOrder(kola);

        // Hesap kontrolü
        System.out.println("\n>> Ara Hesap Kontrolü:");
        System.out.println(rez.toString());

        // Çıkış ve Ödeme
        System.out.println("\n>> Tatil bitti, çıkış yapılıyor...");
        rez.checkOut("1111222233334444");

        // ---------------------------------------------------------
        // SENARYO B: İPTAL İŞLEMİ
        // ---------------------------------------------------------
        System.out.println("\n--------------------------------------------");
        System.out.println("--- Senaryo B: İptal Edilen Rezervasyon ---");

        // Başka bir müşteri
        Customer c2 = new Customer(101, "Ali Kahraman", "555-000-0000");
        Reservation iptalRez = new Reservation(c2, r2, "20.06.2026", 2);

        System.out.println(">> Ali Bey vazgeçti, rezervasyonu İPTAL ediyor...");
        iptalRez.cancel(); // Ödeme alınmadan iptal edildi.

        // Final Kontrol (Oda 105 boşa çıkmış olmalı)
        System.out.println(">> Final Kontrol: Oda 105 listeye geri döndü mü?");
        otel.listAvailableRooms();
    }
}