import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testCardValidation() {
        System.out.println("💳 ÖDEME SİSTEMİ DOĞRULAMA TESTİ BAŞLIYOR...");

        // 1. DURUM: Geçerli Kart (16 Hane)
        // Beklenti: true dönmeli
        Payment validPayment = new Payment(1500.0, "Zeynep Yildiz", "1234567812345678");
        assertTrue(validPayment.processPayment(), "HATA: 16 haneli geçerli kart reddedildi!");
        System.out.println("✅ Senaryo 1: 16 haneli kart başarıyla onaylandı.");

        // 2. DURUM: Eksik Hane (10 Hane)
        // Beklenti: false dönmeli
        Payment shortCard = new Payment(500.0, "Ahmet Yilmaz", "1234567890");
        assertFalse(shortCard.processPayment(), "HATA: 10 haneli (eksik) kart kabul edildi!");
        System.out.println("✅ Senaryo 2: Eksik numaralı kart güvenlik gereği reddedildi.");

        // 3. DURUM: Fazla Hane (18 Hane)
        // Beklenti: false dönmeli
        Payment longCard = new Payment(2000.0, "Mehmet Can", "123456789012345678");
        assertFalse(longCard.processPayment(), "HATA: 16 haneden fazla olan kart kabul edildi!");
        System.out.println("✅ Senaryo 3: Hatalı uzunluktaki kart reddedildi.");
    }
}