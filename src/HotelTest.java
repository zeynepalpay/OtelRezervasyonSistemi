import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    @Test
    void testRoomManagement() {
        System.out.println("🏨 OTEL YÖNETİM TESTİ BAŞLIYOR...");
        Hotel hotel = new Hotel("Skyline Test Hotel");

        // 1. Odayı ekle
        Room r1 = new StandardRoom(505, 1200.0);
        hotel.addRoom(r1);

        // 2. Eklenen odayı sistemden geri çağır
        Room found = hotel.getRoom(505);

        // KONTROLLER:
        assertNotNull(found, "HATA: Eklenen oda otel listesinde bulunamadı!");
        assertEquals(505, found.getRoomNumber(), "HATA: Yanlış oda numarası döndü!");

        // 3. Olmayan bir odayı sorgula
        Room ghost = hotel.getRoom(999);
        assertNull(ghost, "HATA: Sistemde olmayan oda için 'null' dönmeliydi!");

        System.out.println("✅ Otel oda yönetimi testleri başarıyla geçti.");
    }
}