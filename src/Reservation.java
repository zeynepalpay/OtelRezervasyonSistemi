import java.util.ArrayList; // Listeleri kullanabilmek için bu kütüphaneyi ekledik.

/**
 * Rezervasyon işlemlerini yönettiğim sınıf.
 * Bir Müşteri (Customer) ile bir Odayı (Room) ilişkilendirerek rezervasyon kaydı oluşturur.
 * Ayrıca kaç gece kalacağı bilgisini ve Oda Servisi siparişlerini tutar.
 */
public class Reservation {

    private Customer customer;
    private Room room;
    private String checkInDate;
    private int nightCount;

    //  Müşterinin yediği yemekleri/siparişleri tutan liste
    private ArrayList<MenuItem> orders;

    // Kurucu Metot (Constructor)
    // Not! : Kart numarasını burada istemiyoruz. Ödemeyi çıkışta (Check-Out) alacağız.
    public Reservation(Customer customer, Room room, String checkInDate, int nightCount) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.nightCount = nightCount;

        // Sipariş listesini içi boş olarak başlatıyoruz
        this.orders = new ArrayList<>();

        // Odayı sistemde "Dolu" olarak işaretliyoruz
        room.makeReservation();

        System.out.println("✅ Rezervasyon Kaydı Oluşturuldu. (Oda: " + room.getRoomNumber() + ")");
        System.out.println("ℹ️ Bilgi: Ödeme işlemi otelden çıkış yaparken alınacaktır.");
    }

    // ---Oda Servisi Siparişi---
    // Müşteri hamburger, kola vb. istediğinde bu metodu kullanacağız.
    public void addOrder(MenuItem item) {
        orders.add(item); // Siparişi listeye ekle
        System.out.println("🍔 ODA SERVİSİ: " + item.getName() + " sipariş edildi. (+ " + item.getPrice() + " TL)");
    }

    // ---Güncel Fiyat Hesaplama---
    // Sadece oda fiyatını değil, yenilen yemeklerin parasını da topluyoruz.
    public double getTotalPrice() {
        double roomPrice = room.calculatePrice() * nightCount; // Oda Ücreti

        double foodPrice = 0;
        // Döngü ile sipariş listesindeki (orders) tüm yemeklerin fiyatını topluyoruz
        for (MenuItem item : orders) {
            foodPrice += item.getPrice();
        }

        return roomPrice + foodPrice; // Oda + Yemek Toplamı
    }

    // Çıkış yap ve Ödeme al.
    // Kart numarasını  müşteriden çıkarken istiyoruz.
    public void checkOut(String cardNumber) {
        double totalAmount = getTotalPrice();

        System.out.println("\n--- ÇIKIŞ İŞLEMİ (CHECK-OUT) ---");
        System.out.println(">> Toplam Borcunuz: " + totalAmount + " TL");

        // Ödeme sistemini çağırıyoruz
        Payment odeme = new Payment(totalAmount, customer.getName(), cardNumber);

        if (odeme.processPayment()) {
            // Ödeme Başarılıysa
            System.out.println("✅ Ödeme Başarılı! Faturanız e-posta adresinize gönderildi.");
            System.out.println("👋 Bizi tercih ettiğiniz için teşekkür ederiz, yine bekleriz!");

            // Sadakat puanı dağıtımı
            int kazanilanPuan = nightCount * 10;
            customer.addLoyaltyPoints(kazanilanPuan);
        } else {
            // Ödeme Başarısızsa (Kibar Uyarı)
            System.out.println("❌ Ödeme İşlemi BAŞARISIZ! (Yetersiz Bakiye veya Hatalı Kart)");
            System.out.println("⚠️ Lütfen geçerli bir kart ile tekrar deneyiniz veya resepsiyonla görüşünüz.");
        }
    }

    // Rezervasyon İptali
    // İptal durumunda odayı tekrar boşa çıkarıyoruz.
    public void cancel() {
        room.cancelReservation();
        System.out.println("ℹ️ Rezervasyon iptal edildi ve oda tekrar satışa açıldı.");
    }

    @Override
    public String toString() {
        double roomCost = room.calculatePrice() * nightCount;
        double extrasCost = getTotalPrice() - roomCost;

        return "REZERVASYON DURUMU:\n" +
                "- Müşteri: " + customer.getName() + "\n" +
                "- Oda No: " + room.getRoomNumber() + "\n" +
                "- Konaklama Ücreti: " + roomCost + " TL\n" +
                "- Ekstralar (Yemek): " + extrasCost + " TL\n" +
                "- TOPLAM TUTAR: " + getTotalPrice() + " TL";
    }
}