import java.util.ArrayList;
/**
 * Rezervasyon işlemlerini yönettiğim sınıf.
 * Değişkenler 'final' yapılarak kod daha güvenli hale getirildi.
 */
public class Reservation {

    // Değişiklik 1: Değişkenler 'final' yapıldı.
    private final Customer customer;
    private final Room room;
    private final String checkInDate;
    private final int nightCount;
    private final ArrayList<MenuItem> orders;

    // Kurucu Metot (Constructor)
    public Reservation(Customer customer, Room room, String checkInDate, int nightCount) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.nightCount = nightCount;
        this.orders = new ArrayList<>();

        // Odayı sistemde "Dolu" olarak işaretliyoruz
        room.makeReservation();

        System.out.println("✅ Rezervasyon Kaydı Oluşturuldu. (Oda: " + room.getRoomNumber() + ")");
        System.out.println("ℹ️ Bilgi: Ödeme işlemi otelden çıkış yaparken alınacaktır.");
    }

    // ---Oda Servisi Siparişi---
    public void addOrder(MenuItem item) {
        orders.add(item);
        System.out.println("🍔 ODA SERVİSİ: " + item.getName() + " sipariş edildi. (+ " + item.getPrice() + " TL)");
    }

    // ---Güncel Fiyat Hesaplama---
    public double getTotalPrice() {
        double roomPrice = room.calculatePrice() * nightCount;
        double foodPrice = 0;

        for (MenuItem item : orders) {
            foodPrice += item.getPrice();
        }
        return roomPrice + foodPrice;
    }

    // Fatura işlemleri için gerekli
    public String getCustomerName() {
        return customer.getName();
    }

    // Çıkış yap ve Ödeme al
    public void checkOut(String cardNumber) {
        double totalAmount = getTotalPrice();

        System.out.println("\n--- ÇIKIŞ İŞLEMİ (CHECK-OUT) ---");
        System.out.println(">> Toplam Borcunuz: " + totalAmount + " TL");

        Payment odeme = new Payment(totalAmount, customer.getName(), cardNumber);

        if (odeme.processPayment()) {
            System.out.println("✅ Ödeme Başarılı! E-faturanız hazırlanıyor...");

            FileService.writeInvoice(this);

            System.out.println("👋 Bizi tercih ettiğiniz için teşekkür ederiz, yine bekleriz!");

            int kazanilanPuan = nightCount * 10;
            customer.addLoyaltyPoints(kazanilanPuan);
        } else {
            System.out.println("❌ Ödeme İşlemi BAŞARISIZ! (Yetersiz Bakiye veya Hatalı Kart)");
            System.out.println("⚠️ Lütfen geçerli bir kart ile tekrar deneyiniz.");
        }
    }

    public void cancel() {
        room.cancelReservation();
        System.out.println("ℹ️ Rezervasyon iptal edildi.");
    }

    @Override
    public String toString() {
        double roomCost = room.calculatePrice() * nightCount;
        double extrasCost = getTotalPrice() - roomCost;

        // Değişiklik 2: checkInDate (Tarih) buraya eklendi, artık "kullanılmıyor" uyarısı vermeyecek.S
        return "REZERVASYON DURUMU:\n" +
                "- Giriş Tarihi: " + checkInDate + "\n" +
                "- Müşteri: " + customer.getName() + "\n" +
                "- Oda No: " + room.getRoomNumber() + "\n" +
                "- Konaklama Ücreti: " + roomCost + " TL\n" +
                "- Ekstralar (Yemek): " + extrasCost + " TL\n" +
                "- TOPLAM TUTAR: " + getTotalPrice() + " TL";
    }
}