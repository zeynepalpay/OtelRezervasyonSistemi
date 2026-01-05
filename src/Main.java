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

    // --- MENÜLER ---

    // YÖNETİCİ MENÜSÜ
    private static void adminMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n🛠️ --- YÖNETİCİ (ADMIN) PANELİ ---");
            System.out.println("1. 📋 Tüm Odaları Listele (Dolu/Boş Raporu)");
            System.out.println("2. ➕ Yeni Oda Ekle");
            System.out.println("3. 🚪 Çıkış");
            System.out.print("Seçiminiz: ");

            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ Lütfen geçerli bir sayı giriniz!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizliği

            switch (choice) {
                case 1:
                    hotel.listAllRooms();
                    break;
                case 2:
                    System.out.println("\n--- YENİ ODA EKLEME SİHİRBAZI ---");
                    System.out.print("Oda Numarası: ");
                    int roomNum = scanner.nextInt();
                    System.out.print("Gecelik Fiyat: ");
                    double price = scanner.nextDouble();
                    System.out.println("Oda Tipi Seçiniz:");
                    System.out.println("1. Standart Oda");
                    System.out.println("2. Deluxe Oda (%20 Hizmet bedelli)");
                    int type = scanner.nextInt();

                    if (type == 1) {
                        hotel.addRoom(new StandardRoom(roomNum, price));
                        System.out.println("✅ Standart Oda (" + roomNum + ") başarıyla eklendi!");
                    } else if (type == 2) {
                        hotel.addRoom(new DeluxeRoom(roomNum, price));
                        System.out.println("✅ Deluxe Oda (" + roomNum + ") başarıyla eklendi!");
                    } else {
                        System.out.println("❌ Hatalı seçim yaptınız, oda eklenemedi.");
                    }
                    break;
                case 3:
                    System.out.println("Yönetici panelinden güvenli çıkış yapılıyor...");
                    exit = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    // MÜŞTERİ MENÜSÜ (GÜNCELLENDİ: Çıkışta Puan Gösterimi Eklendi)
    private static void customerMenu(User user) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n🏖️ --- MÜŞTERİ PANELİ ---");
            System.out.println("1. 🏨 Müsait Odaları Listele");
            System.out.println("2. 📅 Rezervasyon Yap");
            System.out.println("3. 🍔 Oda Servisi (Yemek Siparişi)");
            System.out.println("4. 🚪 Otelden Çıkış ve Ödeme"); // Menü ismi güncellendi
            System.out.print("Seçiminiz: ");

            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ Lütfen geçerli bir sayı giriniz!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizliği

            switch (choice) {
                case 1:
                    hotel.listAvailableRooms();
                    break;

                case 2:
                    // --- REZERVASYON YAPMA ---
                    System.out.println("\n--- REZERVASYON İŞLEMİ ---");
                    System.out.print("İstediğiniz Oda Numarası: ");
                    int roomNum = scanner.nextInt();

                    Room selectedRoom = hotel.getRoom(roomNum);

                    if (selectedRoom != null) {
                        System.out.print("Giriş Tarihi (Örn: 10.05.2026): ");
                        scanner.nextLine();
                        String date = scanner.nextLine();

                        System.out.print("Kaç gece kalacaksınız?: ");
                        int nights = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("İletişim için Telefon Numaranız: ");
                        String phone = scanner.nextLine();

                        Customer customer = new Customer(100, user.getFullName(), phone);
                        Reservation rez = new Reservation(customer, selectedRoom, date, nights);

                        // Rezervasyonu otelin defterine kaydediyoruz
                        hotel.addReservation(rez);

                        System.out.println("\n✅ İŞLEM TAMAM! Rezervasyonunuz alındı.");
                        System.out.println(rez.toString());
                    }
                    break;

                case 3:
                    // --- ODA SERVİSİ ---
                    System.out.println("\n🍔 --- ODA SERVİSİ ---");
                    System.out.print("Hangi odada kalıyorsunuz? (Oda No): ");
                    int myRoomNum = scanner.nextInt();

                    Reservation foundRez = null;

                    for (Reservation r : hotel.getReservations()) {
                        if (r.getRoom().getRoomNumber() == myRoomNum) {
                            foundRez = r;
                            break;
                        }
                    }

                    if (foundRez != null) {
                        System.out.println("Merhaba " + foundRez.getCustomerName() + ", ne arzu edersiniz?");
                        System.out.println("1. Hamburger (250 TL)");
                        System.out.println("2. Pizza (300 TL)");
                        System.out.println("3. Kola (50 TL)");
                        System.out.println("4. Türk Kahvesi (80 TL)");
                        System.out.print("Seçiminiz: ");

                        int foodChoice = scanner.nextInt();

                        if (foodChoice == 1) foundRez.addOrder(new MenuItem("Hamburger", 250));
                        else if (foodChoice == 2) foundRez.addOrder(new MenuItem("Pizza", 300));
                        else if (foodChoice == 3) foundRez.addOrder(new MenuItem("Kola", 50));
                        else if (foodChoice == 4) foundRez.addOrder(new MenuItem("Türk Kahvesi", 80));
                        else System.out.println("❌ Geçersiz ürün seçimi!");

                        System.out.println(">> Sipariş mutfağa iletildi. Çıkışta faturanıza yansıtılacaktır.");
                    } else {
                        System.out.println("❌ HATA: " + myRoomNum + " numaralı odada aktif bir rezervasyon bulunamadı.");
                        System.out.println("Lütfen önce rezervasyon yapınız.");
                    }
                    break;

                case 4:
                    //  ÇIKIŞ VE PUAN HESAPLAMA ---
                    System.out.println("\n🚪 --- OTELDEN ÇIKIŞ İŞLEMİ ---");
                    System.out.print("Lütfen Oda Numaranızı Giriniz: ");
                    int outRoomNum = scanner.nextInt();
                    scanner.nextLine(); // Buffer temizliği

                    // 1. Odaya ait rezervasyonu buluyoruz
                    Reservation checkoutRez = null;
                    for (Reservation r : hotel.getReservations()) {
                        if (r.getRoom().getRoomNumber() == outRoomNum) {
                            checkoutRez = r;
                            break;
                        }
                    }

                    if (checkoutRez != null) {
                        // 2. Fiyatı gösterip ödeme alıyoruz
                        System.out.println(">> Toplam Hesap: " + checkoutRez.getTotalPrice() + " TL");
                        System.out.print("Ödeme için Kart Numarası (16 Hane): ");
                        String cardNo = scanner.nextLine();

                        // checkOut metodu çağrılınca ekrana PUAN mesajı da otomatik gelecek
                        checkoutRez.checkOut(cardNo);

                        exit = true; // İşlem bitti, menüden çık
                    } else {
                        System.out.println("❌ HATA: Bu numarada çıkış yapacak bir müşteri bulunamadı!");
                    }
                    break;

                default:
                    System.out.println("Geçersiz seçim, lütfen 1-4 arası bir sayı girin.");
            }
        }
    }
}