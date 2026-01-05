import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> rooms; // Oteldeki tüm odaların tutulduğu liste.

    // Yapılan rezervasyonları sakladığımız liste (Rezervasyon Defteri)
    // Oda servisi siparişi verirken "Bu oda kimin?" diye bakmak için lazım.
    private List<Reservation> activeReservations;

    // Kurucu Metot (Constructor)
    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.activeReservations = new ArrayList<>(); // Rezervasyon listesini başlatıyoruz
    }

    // Otele yeni bir oda eklenmesini sağlayan metot
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Rezervasyonu deftere kaydeden metot
    public void addReservation(Reservation rez) {
        activeReservations.add(rez);
    }

    // Main sınıfında "Hangi odada kim kalıyor?" diye bakmak için listeyi veriyoruz.
    public List<Reservation> getReservations() {
        return activeReservations;
    }

    // MÜŞTERİ İÇİN: Sadece müsait (boş) odaları bulup listeleyen metot
    public void listAvailableRooms() {
        System.out.println();
        System.out.println("--- " + name + " : MÜSAİT ODALAR LİSTESİ ---");

        boolean found = false;

        for (Room oda : rooms) {
            if (oda.isAvailable()) {
                System.out.println("- Oda No: " + oda.getRoomNumber() +
                        " (" + (oda instanceof DeluxeRoom ? "Deluxe" : "Standart") + ")" +
                        " -> Fiyat: " + oda.calculatePrice() + " TL");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Üzgünüz, şu an hiç boş odamız kalmadı!");
        }
        System.out.println("---------------------------------------------");
    }

    // YÖNETİCİ İÇİN: Dolu veya boş fark etmeksizin TÜM odaları raporlar.
    public void listAllRooms() {
        System.out.println("\n📋 --- YÖNETİCİ RAPORU: TÜM ODALAR ---");
        System.out.println("----------------------------------------");

        for (Room room : rooms) {
            String durum = room.isAvailable() ? "✅ BOŞ" : "🔴 DOLU";
            String tip = (room instanceof DeluxeRoom) ? "Deluxe" : "Standart";

            System.out.println(String.format("Oda No: %d | Tip: %-8s | Fiyat: %.1f TL | Durum: %s",
                    room.getRoomNumber(), tip, room.calculatePrice(), durum));
        }
        System.out.println("----------------------------------------");
    }

    // Müşteri "101 nolu odayı istiyorum" dediğinde o odayı bulup getiren metot.
    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        System.out.println("⚠️ HATA: " + roomNumber + " numaralı oda sistemde yok!");
        return null;
    }
}