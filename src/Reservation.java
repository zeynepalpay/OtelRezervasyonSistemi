/**
 * Rezervasyon işlemlerini yönettiğim sınıf.
 * Bir Müşteri (Customer) ile bir Odayı (Room) ilişkilendirerek rezervasyon kaydı oluşturur.
 * Ayrıca kaç gece kalacağı bilgisini tutar.
 */
public class Reservation {

    private Customer customer;
    private Room room;
    private String checkInDate;
    private int nightCount;

    // Kurucu Metot (Constructor)
    public Reservation(Customer customer, Room room, String checkInDate, int nightCount, String cardNumber) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.nightCount = nightCount;

        // 1. Ödenecek tutarı hesapla
        double totalAmount = getTotalPrice();

        // 2. Ödeme nesnesi oluştur (Tutar, Müşteri Adı, Kart No)
        Payment odeme = new Payment(totalAmount, customer.getName(), cardNumber);

        // 3. Ödemeyi işlemeye çalış
        if (odeme.processPayment()) {
            // EĞER ÖDEME BAŞARILIYSA: Odayı kilitle (Rezervasyon yapılır)
            room.makeReservation();
            System.out.println("✅ Rezervasyon başarıyla onaylandı!");
        } else {
            // EĞER ÖDEME BAŞARISIZSA: Odayı kilitleme, hata mesajı ver.
            System.out.println("❌ Rezervasyon BAŞARISIZ! Ödeme alınamadı.");
        }
    }

    // Toplam tutar hesaplama
    public double getTotalPrice() {
        return room.calculatePrice() * nightCount;
    }

    // Rezervasyon İptali --
    // Bu metot çağrıldığında, Room sınıfındaki 'cancelReservation' çalışır ve oda boşa çıkar.
    public void cancel() {
        room.cancelReservation();
        System.out.println("ℹ️ Bilgi: Rezervasyon kaydı silindi ve oda tekrar satışa açıldı.");
    }

    @Override
    public String toString() {
        return "REZERVASYON FİŞİ:\n" +
                "- Müşteri: " + customer.getName() + "\n" +
                "- Oda No: " + room.getRoomNumber() + "\n" +
                "- Giriş Tarihi: " + checkInDate + "\n" +
                "- Gece Sayısı: " + nightCount + "\n" +
                "- ÖDENEN TUTAR: " + getTotalPrice() + " TL";
    }
}