/**
 * Müşteri bilgilerini tutan sınıf.
 * Veri güvenliğini sağlamak için (Encapsulation) değişkenleri private yaptım.
 *  Ayrıca sadakat puanı sistemiyle müşterilerimize her konaklamada puan kazandırıyoruz.
 */
public class Customer {

    // Müşteriye özel numara (ID)
    private int id;

    // Müşterinin tam adı
    private String name;

    // İletişim numarası
    private String phoneNumber;

    // Sadakat Puanı
    private int loyaltyPoints;

    // Kurucu Metot (Constructor)
    public Customer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = 0; // Yeni müşteri her zaman 0 puanla başlar.
    }

    // --- Getter Metotları: Private değişkenlere güvenli erişim sağlar---

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Müşterinin şu ana kadar kaç puan biriktirdiğini öğrenmemizi sağlar.
     * @return Mevcut sadakat puanı
     */
    public int getLoyaltyPoints() {

        return loyaltyPoints;
    }

    /**
     * Müşteri otelden çıkış yaptığında (Check-out), kazandığı puanı hesabına ekleyen metot.
     * Bu metot Rezervasyon sınıfı üzerinden otomatik çağrılır.
     * @param points Eklenmek istenen puan miktarı
     */
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        System.out.println("🌟 SİSTEM MESAJI: " + this.name + " hesabına " + points + " sadakat puanı yüklendi.");
        System.out.println("   -> Güncel Toplam Puan: " + this.loyaltyPoints);
    }

    /**
     * Müşteri bilgilerini tek bir satırda, okunabilir bir özet halinde döndürür.
     * @return Müşteri bilgilerinin metin hali
     */
    @Override
    public String toString() {
        return "Müşteri Bilgisi -> ID: " + id + " | Ad: " + name +
                " | Tel: " + phoneNumber + " | Puan: " + loyaltyPoints;
    }
}