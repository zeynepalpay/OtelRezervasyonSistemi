import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> rooms; // Oteldeki tüm odaların listesi
    private List<Reservation> activeReservations; // Aktif rezervasyonların defteri

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.activeReservations = new ArrayList<>();
    }

    // Otele yeni oda ekleme metodu
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * --- GÜVENLİ REZERVASYON METODU (Giriş Tarihi ve Gece Sayısı Dahil) ---
     * Kullanıcıdan gelen tarih bilgisini parametre olarak alır.
     */
    public boolean makeReservation(int roomNumber, Customer customer, String checkInDate, int nights) {
        Room selectedRoom = getRoom(roomNumber); // Önce odayı buluyoruz.

        if (selectedRoom != null) {
            // Oda müsait mi kontrolü
            // Eğer isAvailable() false ise sistem işlemi burada durdurur.
            if (!selectedRoom.isAvailable()) {
                System.out.println("\n❌ DURDURULDU: " + roomNumber + " nolu oda zaten dolu!");
                System.out.println("⚠️ Bu oda rezerve edildiği için başka bir işlem yapılamaz.");
                return false;
            }

            // 2. ADIM: Odayı derhal "DOLU" yapıyoruz.
            // Bu satır sayesinde oda, "Müsait Odalar Listesi"nden anında düşer.
            selectedRoom.setAvailable(false);

            // 3. ADIM: Rezervasyonu kullanıcının girdiği tarih ve gece sayısıyla kaydediyoruz.
            Reservation newRez = new Reservation(customer, selectedRoom, checkInDate, nights);
            activeReservations.add(newRez);

            System.out.println("✅ SİSTEM: Oda " + roomNumber + ", " + checkInDate +
                    " tarihi için başarıyla rezerve edildi.");
            return true;
        }
        return false;
    }

    // Aktif rezervasyon listesini döndürür (Oda servisi vb. işlemler için)
    public List<Reservation> getReservations() {
        return activeReservations;
    }

    // MÜŞTERİ İÇİN: Sadece müsait (isAvailable = true) odaları listeler
    public void listAvailableRooms() {
        System.out.println();
        System.out.println("--- " + name + " : MÜSAİT ODALAR LİSTESİ ---");
        boolean found = false;

        for (Room oda : rooms) {
            if (oda.isAvailable()) { // Sadece müsait olanlar
                String tip = (oda instanceof DeluxeRoom ? "Deluxe" : "Standart");
                System.out.println("- Oda No: " + oda.getRoomNumber() +
                        " (" + tip + ") -> Gecelik Fiyat: " + oda.calculatePrice() + " TL");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Üzgünüz, şu an hiç boş odamız kalmadı!");
        }
        System.out.println("---------------------------------------------");
    }

    // YÖNETİCİ İÇİN Rapor
    public void listAllRooms() {
        System.out.println("\n📋 --- YÖNETİCİ RAPORU: TÜM ODALAR ---");
        System.out.println("----------------------------------------");
        for (Room room : rooms) {
            String durum = room.isAvailable() ? "✅ BOŞ" : "🔴 DOLU";
            String tip = (room instanceof DeluxeRoom) ? "Deluxe" : "Standart";
            System.out.println(String.format("Oda No: %d | Tip: %-8s | Durum: %s",
                    room.getRoomNumber(), tip, durum));
        }
        System.out.println("----------------------------------------");
    }

    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}