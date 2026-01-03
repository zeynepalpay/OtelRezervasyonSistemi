public class Payment {

    private double amount;          // Çekilecek tutar
    private String cardHolderName;  // Kart sahibinin adı
    private String cardNumber;      // Kart numarası

    // Kurucu Metot
    public Payment(double amount, String cardHolderName, String cardNumber) {
        this.amount = amount;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
    }

    // Ödeme işlemini gerçekleştiren metot
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