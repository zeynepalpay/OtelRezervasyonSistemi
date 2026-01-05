import java.util.Scanner;

public class Main {

    private static Hotel hotel;
    private static AuthService authService;
    private static Scanner scanner;

    public static void main(String[] args) {
        setupSystem();

        System.out.println("==========================================");
        System.out.println("🏨 SKYLINE HOTEL REZERVASYON SİSTEMİ");
        System.out.println("==========================================");

        System.out.println("\nLütfen sisteme giriş yapınız.");
        System.out.print("👤 Kullanıcı Adı: ");
        String username = scanner.nextLine();
        System.out.print("🔑 Şifre: ");
        String password = scanner.nextLine();

        User currentUser = authService.login(username, password);

        if (currentUser != null) {
            System.out.println("\n--------------------------------");
            System.out.println("👋 Hoşgeldiniz, Sayın " + currentUser.getFullName());
            System.out.println("🔰 Yetki: " + currentUser.getRole());
            System.out.println("--------------------------------");

            if (currentUser.getRole().equals("ADMIN")) {
                adminMenu();
            } else {
                customerMenu(currentUser);
            }
        } else {
            System.out.println("\n🔴 HATA: Kullanıcı adı veya şifre yanlış!");
            System.out.println("Sistem kapatılıyor...");
        }
    }

    private static void setupSystem() {
        scanner = new Scanner(System.in);
        authService = new AuthService();
        hotel = new Hotel("Skyline Hotel");

        hotel.addRoom(new StandardRoom(101, 1000.0));
        hotel.addRoom(new StandardRoom(102, 1000.0));
        hotel.addRoom(new DeluxeRoom(201, 1500.0));
        hotel.addRoom(new DeluxeRoom(202, 1500.0));
        hotel.addRoom(new StandardRoom(303, 1000.0));
    }

    private static void adminMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n🛠️ --- YÖNETİCİ (ADMIN) PANELİ ---");
            System.out.println("1. 📋 Tüm Odaları Listele");
            System.out.println("2. ➕ Yeni Oda Ekle");
            System.out.println("3. 🚪 Çıkış");
            System.out.print("Seçiminiz: ");

            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ Lütfen geçerli bir rakam giriniz!");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hotel.listAllRooms();
                    break;
                case 2:
                    System.out.print("Oda Numarası: ");
                    int roomNum = scanner.nextInt();
                    System.out.print("Gecelik Fiyat: ");
                    double price = scanner.nextDouble();
                    System.out.println("1: Standart, 2: Deluxe");
                    int type = scanner.nextInt();
                    scanner.nextLine();

                    if (type == 1) hotel.addRoom(new StandardRoom(roomNum, price));
                    else hotel.addRoom(new DeluxeRoom(roomNum, price));
                    System.out.println("✅ Oda başarıyla eklendi.");
                    break;
                case 3:
                    exit = true;
                    break;
            }
        }
    }

    private static void customerMenu(User user) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n🏖️ --- MÜŞTERİ PANELİ ---");
            System.out.println("1. 🏨 Müsait Odaları Listele");
            System.out.println("2. 📅 Rezervasyon Yap");
            System.out.println("3. 🍔 Oda Servisi (Yemek Siparişi)");
            System.out.println("4. 🚪 Otelden Çıkış ve Ödeme");
            System.out.print("Seçiminiz: ");

            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ HATA: Lütfen 1-4 arası bir sayı giriniz!");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hotel.listAvailableRooms();
                    break;

                case 2:
                    // --- REZERVASYON İŞLEMİ
                    System.out.println("\n--- REZERVASYON İŞLEMİ ---");
                    hotel.listAvailableRooms();

                    System.out.print("İstediğiniz Oda Numarası: ");
                    if (!scanner.hasNextInt()) { scanner.nextLine(); break; }
                    int roomNum = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Giriş Tarihi (Örn: 15.01.2026): ");
                    String checkInDate = scanner.nextLine();

                    System.out.print("Kaç gece konaklayacaksınız?: ");
                    if (!scanner.hasNextInt()) { scanner.nextLine(); break; }
                    int nights = scanner.nextInt();
                    scanner.nextLine();

                    Customer customer = new Customer(100, user.getFullName(), "İletişim Bilgisi Yok");

                    // Hotel metoduna artık tarihi de gönderiyoruz
                    boolean basariliMi = hotel.makeReservation(roomNum, customer, checkInDate, nights);

                    if (basariliMi) {
                        System.out.println("✨ Rezervasyonunuz " + checkInDate + " tarihi için onaylandı!");
                    } else {
                        System.out.println("⚠️ Hata: Seçtiğiniz oda dolmuş olabilir veya mevcut değil.");
                    }
                    break;

                case 3:
                    System.out.println("\n🍔 --- ODA SERVİSİ ---");
                    System.out.print("Hangi odada kalıyorsunuz? (Oda No): ");
                    if (!scanner.hasNextInt()) { scanner.nextLine(); break; }
                    int myRoomNum = scanner.nextInt();
                    scanner.nextLine();

                    Reservation foundRez = null;
                    for (Reservation r : hotel.getReservations()) {
                        if (r.getRoom().getRoomNumber() == myRoomNum) {
                            foundRez = r;
                            break;
                        }
                    }

                    if (foundRez != null) {
                        System.out.println("Merhaba " + foundRez.getCustomerName() + ", ne arzu edersiniz?");
                        System.out.println("1. Hamburger (250 TL)\n2. Pizza (300 TL)\n3. Türk Kahvesi (80 TL)");
                        System.out.print("Seçiminiz: ");

                        if (scanner.hasNextInt()) {
                            int foodChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (foodChoice == 1) foundRez.addOrder(new MenuItem("Hamburger", 250));
                            else if (foodChoice == 2) foundRez.addOrder(new MenuItem("Pizza", 300));
                            else if (foodChoice == 3) foundRez.addOrder(new MenuItem("Türk Kahvesi", 80));
                            System.out.println(">> Sipariş mutfağa iletildi.");
                        }
                    } else {
                        System.out.println("❌ HATA: Bu odada aktif bir konaklama bulunamadı.");
                    }
                    break;

                case 4:
                    System.out.println("\n🚪 --- OTELDEN ÇIKIŞ İŞLEMİ ---");
                    System.out.print("Lütfen Oda Numaranızı Giriniz: ");
                    if (!scanner.hasNextInt()) { scanner.nextLine(); break; }
                    int outRoomNum = scanner.nextInt();
                    scanner.nextLine();

                    Reservation checkoutRez = null;
                    for (Reservation r : hotel.getReservations()) {
                        if (r.getRoom().getRoomNumber() == outRoomNum) {
                            checkoutRez = r;
                            break;
                        }
                    }

                    if (checkoutRez != null) {
                        System.out.println(">> Toplam Hesap: " + checkoutRez.getTotalPrice() + " TL");
                        System.out.print("Ödeme için Kart Numarası (16 Hane): ");
                        String cardNo = scanner.nextLine();

                        checkoutRez.checkOut(cardNo);
                        exit = true;
                    } else {
                        System.out.println("❌ HATA: Çıkış yapılacak rezervasyon bulunamadı!");
                    }
                    break;

                default:
                    System.out.println("⚠️ Geçersiz seçim!");
            }
        }
    }
}