/**
 * Otel ödeme işlemlerini simüle eden sınıftır.
 * Müşterinin kredi kartı bilgilerini doğrular ve ödemenin
 * başarıyla alınıp alınmadığını kontrol eder.
 */
public class Payment {

    private double amount;          // Çekilecek tutar
    private String cardHolderName;  // Kart sahibinin adı
    private String cardNumber;      // Kart numarası

    /**
     * Ödeme nesnesini gerekli bilgilerle hazırlar.
     * @param amount Tahsil edilecek toplam tutar.
     * @param cardHolderName Kartın üzerindeki isim.
     * @param cardNumber 16 haneli kredi kartı numarası.
     */
    public Payment(double amount, String cardHolderName, String cardNumber) {
        this.amount = amount;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
    }
    /**
     * Banka entegrasyonunu simüle ederek ödeme işlemini gerçekleştirir.
     * Kart numarasının uzunluğunu kontrol ederek basit bir güvenlik doğrulaması yapar.
     * @return Ödeme onaylandıysa true, kart geçersizse false döner.
     */
    public boolean processPayment() {
        System.out.println(">> ÖDEME SİSTEMİ: Banka ile iletişim kuruluyor...");

        // Basit bir doğrulama kuralı: Kart numarası 16 haneli olmalı
        if (cardNumber.length() != 16) {
            System.out.println("HATA: Geçersiz kart numarası! İşlem iptal edildi.");
            return false; // Ödeme başarısız
        }

        System.out.println("BAŞARILI: " + amount + " TL tutarında ödeme " + cardHolderName + " kartından tahsil edildi.");
        return true; // Ödeme başarılı
    }
}