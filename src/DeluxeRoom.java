/**
 * Lüks (Deluxe) Oda Sınıfı.
 * Room sınıfından miras alır.
 * Bu odalar standart fiyata ek olarak %20 hizmet bedeli içerir.
 */
public class DeluxeRoom extends Room {
    /**
     * Deluxe oda için gerekli bilgileri alan ve üst sınıfa gönderen kurucu metot.
     * @param roomNumber Odaya verilecek numara
     * @param price Odanın temel (baz) gecelik fiyatı
     */
    public DeluxeRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }

    /**
     * Deluxe odaya özel fiyat hesaplama mantığını çalıştırır.
     * Standart fiyata %20 zam ekleyerek son fiyatı belirler.
     * @return Hizmet bedeli eklenmiş toplam oda fiyatı
     */
    @Override
    public double calculatePrice() {
        // Room sınıfından miras alınan (abstract) metodu,
        // Deluxe oda tipine özgü kurallarla yeniden tanımlıyoruz (Override).
        return price * 1.20;
    }
}