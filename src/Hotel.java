import java.util.ArrayList;
import java.util.List;
import java.io.*;
/**
 * Otelimizin tüm operasyonlarını yöneten ana yönetim merkezidir.
 * Odaların listesini tutar, rezervasyon işlemlerini yapar ve verilerin
 * 'odalar.txt' dosyasında kalıcı olarak saklanmasını sağlar.
 */
public class Hotel {

    private String name;
    private List<Room> rooms; // Oteldeki tüm odaların listesi
    private List<Reservation> activeReservations; // Aktif rezervasyonların defteri
    private final String FILE_NAME = "odalar.txt"; // Odaların tutulduğu dosya

    /**
     * Otel nesnesini başlatan kurucu metot.
     * @param name Otelin tabelasında görünecek isim.
     */
    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.activeReservations = new ArrayList<>();
    }

    /**
     * Program açıldığında 'odalar.txt' dosyasındaki verileri okur.
     * Bu metot sayesinde programı kapatıp açsak bile odaların fiyatları,
     * tipleri ve doluluk durumları geri yüklenir.
     */
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
    /**
     * Mevcut oda listesindeki tüm güncel bilgileri 'odalar.txt' dosyasına yazar.
     * Bu işlem verilerin kalıcı olmasını sağlar; yani bir oda dolduğunda
     * bu bilgi anında dosyaya işlenir.
     */
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
     * --- GÜVENLİ REZERVASYON METODU  ---
     */
    public boolean makeReservation(int roomNumber, Customer customer, String checkInDate, int nights) {
        Room selectedRoom = getRoom(roomNumber);

        if (selectedRoom != null) {
            if (!selectedRoom.isAvailable()) {
                System.out.println("\n❌ DURDURULDU: " + roomNumber + " nolu oda zaten dolu!");
                return false;
            }

            // Odayı sistemde rezerve ediyoruz
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
    /**
     * Oda numarasına göre oda listesinde arama yapar.
     * @param roomNumber Aranacak oda numarası.
     * @return Bulunursa oda nesnesini, bulunamazsa null döner.
     */
    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) return room;
        }
        return null;
    }
    /**
     * Belirtilen oda numarasına ait aktif bir rezervasyonu iptal eder.
     * Odayı tekrar müsait hale getirir ve dosyaya kaydeder.
     * @param roomNumber İptal edilecek oda numarası.
     * @return İptal işlemi başarılıysa true, rezervasyon bulunamadıysa false döner.
     */
    public boolean cancelReservation(int roomNumber) {
        Reservation foundRez = null;

        // 1. İptal edilecek rezervasyonu aktif listesinde bul
        for (Reservation r : activeReservations) {
            if (r.getRoom().getRoomNumber() == roomNumber) {
                foundRez = r;
                break;
            }
        }

        // 2. Eğer bulunduysa listeden sil ve odayı boşalt
        if (foundRez != null) {
            activeReservations.remove(foundRez);
            foundRez.getRoom().setAvailable(true); // Odayı müsait (true) yap

            // 3. KRİTİK: Dosyadaki oda durumunu anında güncelle
            saveRoomsToFile(); //

            System.out.println("✅ İŞLEM BAŞARILI: Oda " + roomNumber + " rezervasyonu iptal edildi.");
            return true;
        } else {
            System.out.println("❌ HATA: Bu odaya ait aktif bir konaklama/rezervasyon bulunamadı.");
            return false;
        }
    }
}