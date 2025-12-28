/**
 * Lüks (Deluxe) Oda Sınıfı.
 * Room sınıfından miras alır.
 * Bu odalar standart fiyata ek olarak %20 hizmet bedeli içerir.
 */
public class DeluxeRoom extends Room {

    // Kurucu Metot
    public DeluxeRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }

    // Room sınıfından miras alınan (abstract) metodu,
    // Deluxe oda tipine özgü (%20 fiyat artışı) kurallarla yeniden tanımlıyoruz (Override).
    @Override
    public double calculatePrice() {
        return price * 1.20;
    }
}