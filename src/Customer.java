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

    // Kurucu Metot (Constructor): 
    // Yeni bir müşteri oluştururken id, isim ve telefon bilgisini zorunlu tutuyorum.
    public Customer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // --- Getter Metotları ---
    // Değişkenler private olduğu için, bu verileri dışarıdan okuyabilmek adına getter metotlarını ekledim.

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Müşteri nesnesini ekrana yazdırdığımda hafıza adresi yerine 
    // anlamlı bilgiler görmek için toString metodunu yeniden tanımladım (Override).
    @Override
    public String toString() {
        return "Müşteri Bilgisi -> ID: " + id + " | Ad: " + name + " | Tel: " + phoneNumber;
    }
}