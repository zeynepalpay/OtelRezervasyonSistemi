/**
 * Rezervasyon işlemlerini yönettiğim sınıf.
 * Bir Müşteri (Customer) ile bir Odayı (Room) ilişkilendirerek rezervasyon kaydı oluşturur.
 *  Ayrıca kaç gece kalacağı bilgisini tutar.
 */
public class Reservation {

    // Rezervasyonu yapan müşteri nesnesi
    private Customer customer;

    // Kiralanan oda nesnesi
    private Room room;

    // Giriş tarihi
    private String checkInDate;

    // Konaklama süresi (gece sayısı)
    private int nightCount;

    // Kurucu Metot (Constructor):
    public Reservation(Customer customer, Room room, String checkInDate, int nightCount) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.nightCount = nightCount;

        // --- yeni eklenen kısım ---
        // Rezervasyon nesnesi oluşturulduğu anda,
        // ilgili odanın "makeReservation" metodunu çağırarak odayı dolu hale getiriyoruz.
        room.makeReservation();
    }

    // Toplam tutarı hesaplayan metot
    // Odanın gecelik fiyatı * gece sayısı
    public double getTotalPrice() {
        return room.calculatePrice() * nightCount;
    }

    // Rezervasyon detaylarını yazdırma
    @Override
    public String toString() {
        return "REZERVASYON DETAYI:\n" +
                "- Müşteri: " + customer.getName() + "\n" +
                "- Oda No: " + room.getRoomNumber() + "\n" +
                "- Tarih: " + checkInDate + "\n" +
                "- Gece Sayısı: " + nightCount + "\n" +
                "- TOPLAM TUTAR: " + getTotalPrice() + " TL";
    }
}