import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    // 1. TEST: Deluxe Oda Fiyatı %20 Zamlı mı?
    @Test
    void testDeluxeRoomPrice() {
        // Odayı 1000 TL baz fiyatla oluşturuyoruz
        Room deluxe = new DeluxeRoom(201, 1000.0);

        // Beklenen: 1000 * 1.20 = 1200 TL
        double beklenenFiyat = 1200.0;

        assertEquals(beklenenFiyat, deluxe.calculatePrice(), "HATA: Deluxe oda fiyatı yanlış hesaplanıyor!");
    }

    // 2. TEST: Standard Oda Fiyatı Aynı mı?
    @Test
    void testStandardRoomPrice() {
        Room standard = new StandardRoom(101, 1000.0);

        // Beklenen: 1000 TL (Değişiklik olmamalı)
        assertEquals(1000.0, standard.calculatePrice(), "HATA: Standart oda fiyatı bozuk!");
    }

    // 3. TEST: Rezervasyon Yapınca Oda "Dolu" Oluyor mu?
    // Bu test, Abstract Room sınıfındaki makeReservation() metodunu test eder.
    @Test
    void testRoomAvailability() {
        Room room = new DeluxeRoom(205, 500.0);

        // A. Başlangıçta oda müsait olmalı (Boş)
        assertTrue(room.isAvailable(), "HATA: Yeni oluşturulan oda 'Dolu' gözüküyor!");

        // B. Rezervasyon yapıyoruz
        room.makeReservation();

        // C. Artık oda müsait olmamalı (Dolu)
        assertFalse(room.isAvailable(), "HATA: Rezervasyon yapıldı ama oda hala 'Müsait' gözüküyor!");

        // D. İptal ediyoruz
        room.cancelReservation();

        // E. Tekrar müsait olmalı
        assertTrue(room.isAvailable(), "HATA: İptal sonrası oda boşa çıkmadı!");
    }
}