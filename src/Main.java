import java.util.Scanner;

public class Main {

    // Tüm sistemin erişebileceği (Global) değişkenler
    private static Hotel hotel;
    private static AuthService authService;
    private static Scanner scanner;

    public static void main(String[] args) {
        // 1. SİSTEM KURULUMU (Başlangıç verilerini yüklüyoruz)
        setupSystem();

        System.out.println("==========================================");
        System.out.println("🏨 SKYLINE HOTEL REZERVASYON SİSTEMİ");
        System.out.println("==========================================");

        // 2. GİRİŞ EKRANI (LOGIN)
        System.out.println("\nLütfen sisteme giriş yapınız.");

        System.out.print("👤 Kullanıcı Adı: ");
        String username = scanner.nextLine();

        System.out.print("🔑 Şifre: ");
        String password = scanner.nextLine();

        // AuthService üzerinden kontrol ediyoruz
        User currentUser = authService.login(username, password);

        if (currentUser != null) {
            // --- GİRİŞ BAŞARILI ---
            System.out.println("\n--------------------------------");
            System.out.println("👋 Hoşgeldiniz, Sayın " + currentUser.getFullName());
            System.out.println("🔰 Yetki: " + currentUser.getRole());
            System.out.println("--------------------------------");

            // Kullanıcının rolüne göre ilgili menüye yönlendir
            if (currentUser.getRole().equals("ADMIN")) {
                adminMenu();
            } else {
                customerMenu(currentUser);
            }

        } else {
            // --- GİRİŞ BAŞARISIZ ---
            System.out.println("\n🔴 HATA: Kullanıcı adı veya şifre yanlış!");
            System.out.println("Sistem kapatılıyor...");
        }
    }

    // Sistemi başlatırken oteli, odaları ve kullanıcı servisini hazırlar
    private static void setupSystem() {
        scanner = new Scanner(System.in);
        authService = new AuthService(); // Kullanıcı listesi yüklendi
        hotel = new Hotel("Skyline Hotel");

        // Otelin odalarını oluşturuyoruz
        hotel.addRoom(new StandardRoom(101, 1000.0));
        hotel.addRoom(new StandardRoom(102, 1000.0));
        hotel.addRoom(new DeluxeRoom(201, 1500.0));
        hotel.addRoom(new DeluxeRoom(202, 1500.0));
        hotel.addRoom(new StandardRoom(303, 1000.0));
    }

    // Menü Taslakları
    private static void adminMenu() {
        System.out.println("\n🛠️ --- YÖNETİCİ PANELİ ---");
        System.out.println("1. Tüm Odaları Listele");
        System.out.println("2. Yeni Oda Ekle");
        System.out.println("3. Sistemden Çıkış");
    }

    private static void customerMenu(User user) {
        System.out.println("\n🏖️ --- MÜŞTERİ PANELİ ---");
        System.out.println("1. Odaları Gör ve Rezervasyon Yap");
        System.out.println("2. Rezervasyonlarım");
        System.out.println("3. Çıkış Yap (Ödeme)");
    }
}