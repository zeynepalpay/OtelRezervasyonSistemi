import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testTotalPriceWithRoomService() {
        // 1. HAZIRLIK (Setup)
        // Geceliği 1000 TL olan bir oda
        Room room = new StandardRoom(101, 1000.0);
        Customer customer = new Customer(1, "Test Müşteri", "555-0000");

        // 2 gece rezervasyon (Oda Fiyatı: 2 x 1000 = 2000 TL)
        Reservation rez = new Reservation(customer, room, "01.01.2026", 2);

        // 2. İŞLEM (Action)
        // Siparişleri ekle
        rez.addOrder(new MenuItem("Hamburger", 250.0));
        rez.addOrder(new MenuItem("Kola", 50.0));

        // 3. KONTROL (Assertion)
        // Beklenen: 2000 (Oda) + 250 (Burger) + 50 (Kola) = 2300 TL
        double beklenenFiyat = 2300.0;
        double gerceklesenFiyat = rez.getTotalPrice();

        // assertEquals(Beklenen, Gerçekleşen, "Hata Mesajı")
        assertEquals(beklenenFiyat, gerceklesenFiyat, "HATA: Oda servisi fiyatı yanlış hesaplandı!");
    }

    @Test
    void testLoyaltyPointsAfterCheckout() {
        // 1. HAZIRLIK
        Room room = new StandardRoom(102, 1000.0);
        // Puanı başta 0 olan bir müşteri
        Customer customer = new Customer(2, "Ece Demir", "552-920-8011");

        // 3 gece konaklama (3 x 10 = 30 puan kazanmalı)
        Reservation rez = new Reservation(customer, room, "10.05.2026", 3);

        // Test Başlamadan Önce: Puan 0 mı?
        assertEquals(0, customer.getLoyaltyPoints());

        // 2. İŞLEM: Çıkış Yap ve Öde
        // Kart numarasını burada veriyoruz.
        rez.checkOut("1234123412341234");

        // 3. KONTROL
        // Müşteriye 30 puan yüklenmiş olmalı
        assertEquals(30, customer.getLoyaltyPoints(), "HATA: Sadakat puanı yüklenmedi!");
    }
}