import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        // Her testten önce temiz bir otel nesnesi oluşturuyoruz
        hotel = new Hotel("Skyline Test Hotel");

        // İsteğe bağlı: Test başlamadan önce eski test dosyasını silebilirsin
        File testFile = new File("odalar.txt");
        // if(testFile.exists()) testFile.delete();
    }

    @Test
    void testRoomManagement() {
        System.out.println("🏨 OTEL YÖNETİM TESTİ BAŞLIYOR...");

        // 1. Odayı ekle
        Room r1 = new StandardRoom(505, 1200.0);
        hotel.addRoom(r1); // Bu işlem artık dosyaya da yazıyor

        // 2. Eklenen odayı sistemden geri çağır
        Room found = hotel.getRoom(505);

        // KONTROLLER
        assertNotNull(found, "HATA: Eklenen oda bulunamadı!");
        assertEquals(1200.0, found.calculatePrice(), "HATA: Fiyat yanlış!");

        // 3. DOSYA KONTROLÜ (Yeni eklediğimiz özellik)
        File file = new File("odalar.txt");
        assertTrue(file.exists(), "HATA: addRoom sonrası odalar.txt oluşmalıydı!");

        System.out.println("✅ Otel oda yönetimi ve dosya kaydı başarılı.");
    }

    @Test
    void testMakeReservationPersistence() {
        System.out.println("📅 REZERVASYON KAYIT TESTİ BAŞLIYOR...");

        Room r2 = new StandardRoom(606, 1000.0);
        hotel.addRoom(r2);

        Customer c = new Customer(1, "Test User", "123");

        // Rezervasyon yap (Bu odayı 'false' yapıp dosyaya yazmalı)
        hotel.makeReservation(606, c, "20.01.2026", 3);

        // Yeni bir hotel nesnesi oluşturup dosyadan yükleyelim (Kalıcılık testi)
        Hotel newHotelInstance = new Hotel("Skyline Re-Load");
        newHotelInstance.loadRoomsFromFile();

        Room persistedRoom = newHotelInstance.getRoom(606);
        assertNotNull(persistedRoom);
        assertFalse(persistedRoom.isAvailable(), "HATA: Dosyadan yüklenen oda hala 'müsait' görünüyor, 'dolu' olmalıydı!");

        System.out.println("✅ Rezervasyonun dosyaya kalıcı işlenmesi başarıyla doğrulandı.");
    }
}