import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> rooms; // Oteldeki tüm odaların tutulduğu liste.

    // Kurucu Metot (Constructor)
    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>(); // Oda listesi burada başlatılır (initialize edilir).
    }

    // Otele yeni bir oda eklenmesini sağlayan metot
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Oteldeki müsait (boş) odaları bulup listeleyen metot
    public void listAvailableRooms() {
        System.out.println();
        System.out.println("--- " + name + " : MÜSAİT ODALAR LİSTESİ ---");

        boolean found = false; // Başlangıçta hiç boş oda yokmuş varsayılır.

        // Odalar listesindeki (rooms) her bir oda üzerinde döngü kurulur.
        for (Room oda : rooms) {
            // Reservable arayüzünden gelen metot ile odanın müsaitlik durumu kontrol edilir.
            if (oda.isAvailable()) {
                System.out.println("- Oda No: " + oda.getRoomNumber() +
                        " (" + (oda instanceof DeluxeRoom ? "Deluxe" : "Standart") + ")" +
                        " -> Fiyat: " + oda.calculatePrice() + " TL");
                found = true; // Eğer müsait bir oda bulunursa bu değişken true yapılır.
            }
        }

        // Döngü tamamlandığında eğer hiç oda bulunamadıysa kullanıcıya bilgi verilir.
        if (!found) {
            System.out.println("Üzgünüz, şu an hiç boş odamız kalmadı!");
        }
        System.out.println("---------------------------------------------");
    }
}