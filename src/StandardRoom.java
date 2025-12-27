/**
 * Standart Oda Sınıfı.
 * Room sınıfından miras alır (extends Room).
 * Bu odalarda ekstra hizmet yoktur, standart fiyat geçerlidir.
 */
public class StandardRoom extends Room {

    // Kurucu metot (Constructor)
    // Oda numarasını ve fiyatı alıp üst sınıfa (Room) gönderir.
    public StandardRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }

    // Room sınıfındaki soyut metodu burada doldurmak ZORUNDAYIZ.
    @Override
    public double calculatePrice() {
        // Standart odada ek ücret yok, direkt belirlenen fiyatı döndür.
        return price;
    }
}