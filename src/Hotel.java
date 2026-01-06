import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Hotel {

    private String name;
    private List<Room> rooms; // Oteldeki tüm odaların listesi
    private List<Reservation> activeReservations; // Aktif rezervasyonların defteri
    private final String FILE_NAME = "odalar.txt"; // Odaların tutulduğu dosya

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.activeReservations = new ArrayList<>();
    }

    // --- DOSYADAN OKUMA METODU ---
    public void loadRoomsFromFile() {
        rooms.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                int no = Integer.parseInt(parts[0]);
                String type = parts[1];
                double price = Double.parseDouble(parts[2]);
                boolean isAvailable = Boolean.parseBoolean(parts[3]);

                Room room;
                if (type.equalsIgnoreCase("Deluxe")) {
                    room = new DeluxeRoom(no, price);
                } else {
                    room = new StandardRoom(no, price);
                }

                // Odanın durumunu dosyadan gelen veriye göre ayarla
                room.setAvailable(isAvailable);
                rooms.add(room);
            }
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ Bilgi: odalar.txt henüz oluşturulmamış, yeni dosya oluşturulacak.");
        } catch (IOException e) {
            System.out.println("❌ HATA: Odalar yüklenirken bir sorun oluştu: " + e.getMessage());
        }
    }

    // --- DOSYAYA KAYDETME METODU ---
    public void saveRoomsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Room room : rooms) {
                String type = (room instanceof DeluxeRoom) ? "Deluxe" : "Standard";
                // Format: No, Tip, Fiyat, Müsaitlik (true/false)
                pw.println(room.getRoomNumber() + "," + type + "," +
                        room.calculatePrice() + "," + room.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("❌ HATA: Odalar dosyaya kaydedilemedi!");
        }
    }

    // Otele yeni oda ekleme (Admin kullandığında dosyaya da yazar)
    public void addRoom(Room room) {
        rooms.add(room);
        saveRoomsToFile(); // Her yeni oda eklemede dosyayı güncelle
    }

    /**
     * --- GÜVENLİ REZERVASYON METODU (Dosya Güncelleme Dahil) ---
     */
    public boolean makeReservation(int roomNumber, Customer customer, String checkInDate, int nights) {
        Room selectedRoom = getRoom(roomNumber);

        if (selectedRoom != null) {
            if (!selectedRoom.isAvailable()) {
                System.out.println("\n❌ DURDURULDU: " + roomNumber + " nolu oda zaten dolu!");
                return false;
            }

            // Odayı kilitliyoruz
            selectedRoom.setAvailable(false);

            Reservation newRez = new Reservation(customer, selectedRoom, checkInDate, nights);
            activeReservations.add(newRez);

            // KRİTİK: Odanın durumunu dosyada da "false" (dolu) yapıyoruz
            saveRoomsToFile();

            System.out.println("✅ SİSTEM: Oda " + roomNumber + " rezerve edildi ve kaydedildi.");
            return true;
        }
        return false;
    }

    public List<Reservation> getReservations() {
        return activeReservations;
    }

    // MÜŞTERİ İÇİN: Sadece müsait olanlar
    public void listAvailableRooms() {
        System.out.println("\n--- " + name + " : MÜSAİT ODALAR LİSTESİ ---");
        boolean found = false;
        for (Room oda : rooms) {
            if (oda.isAvailable()) {
                String tip = (oda instanceof DeluxeRoom ? "Deluxe" : "Standart");
                System.out.println("- Oda No: " + oda.getRoomNumber() + " (" + tip + ") -> " + oda.calculatePrice() + " TL");
                found = true;
            }
        }
        if (!found) System.out.println("Üzgünüz, boş oda kalmadı!");
    }

    // YÖNETİCİ İÇİN Rapor
    public void listAllRooms() {
        System.out.println("\n📋 --- YÖNETİCİ RAPORU: TÜM ODALAR ---");
        for (Room room : rooms) {
            String durum = room.isAvailable() ? "✅ BOŞ" : "🔴 DOLU";
            String tip = (room instanceof DeluxeRoom) ? "Deluxe" : "Standart";
            System.out.println(String.format("Oda No: %d | Tip: %-8s | Durum: %s",
                    room.getRoomNumber(), tip, durum));
        }
    }

    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) return room;
        }
        return null;
    }
}