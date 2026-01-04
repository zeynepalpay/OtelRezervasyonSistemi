/**
 * Müşteri bilgilerini tutan sınıf.
 * Veri güvenliğini sağlamak için (Encapsulation) değişkenleri private yaptım.
 */
public class Customer {

    // Müşteriye özel numara (ID)
    private int id;

    // Müşterinin tam adı
    private String name;

    // İletişim numarası
    private String phoneNumber;

    // Sadakat Puanı(yeni ekledim)
    private int loyaltyPoints;

    // Kurucu Metot (Constructor)
    public Customer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = 0; // Yeni müşteri her zaman 0 puanla başlar.
    }

    // --- Getter Metotları ---

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Yeni metot: Puanı Okuma
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Yeni metot: Puan Ekleme İşlemi
    // Bu metodu Rezervasyon sınıfından çağıracağım.
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        System.out.println("🌟 SİSTEM MESAJI: " + this.name + " hesabına " + points + " sadakat puanı yüklendi.");
        System.out.println("   -> Güncel Toplam Puan: " + this.loyaltyPoints);
    }

    @Override
    public String toString() {
        return "Müşteri Bilgisi -> ID: " + id + " | Ad: " + name +
                " | Tel: " + phoneNumber + " | Puan: " + loyaltyPoints;
    }
}