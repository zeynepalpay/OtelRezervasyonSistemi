import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Otelimizin sadakat programını ve müşteri kayıt sistemini test ettiğim sınıftır.
 * Amacım; yeni bir müşteri sisteme girdiğinde bilgilerinin doğru kaydedilip
 * kaydedilmediğini ve kazandığı puanların hesabına düzgün yansıyıp yansımadığını doğrulamaktır.
 */

class CustomerTest {
    /**
     * Müşteri oluşturma ve puan kazanma sürecini uçtan uca test eden metodumdur.
     * Bu test kapsamında;
     * 1. Yeni müşterinin başlangıç puanının 0 olduğu,
     * 2. İsim bilgisinin sisteme doğru geçtiği,
     * 3. Puan ekleme işlemi sonrası bakiyenin güncellendiği kontrol edilir.
     */
    @Test
    void testCustomerAndLoyaltyPoints() {
        System.out.println("👤 MÜŞTERİ VE PUAN SİSTEMİ TESTİ BAŞLIYOR...");

        // 1. Yeni bir müşteri oluşturalım (ID: 1, İsim: Zeynep, Tel: 5559238677)
        Customer customer = new Customer(1, "Zeynep", "5559238677");

        // 2. İlk kayıt kontrolü: Puan 0 mı?
        assertEquals(0, customer.getLoyaltyPoints(), "HATA: Yeni müşteri 0 puanla başlamalı!");
        assertEquals("Zeynep", customer.getName(), "HATA: Müşteri adı yanlış kaydedildi!");
        System.out.println("✅ Senaryo 1: Yeni müşteri kaydı ve başlangıç puanı (0) doğru.");

        // 3. Puan ekleme kontrolü: 50 puan ekleyelim
        customer.addLoyaltyPoints(50);
        assertEquals(50, customer.getLoyaltyPoints(), "HATA: Puan ekleme işlemi başarısız!");
        System.out.println("✅ Senaryo 2: Sadakat puanı ekleme işlemi başarıyla doğrulandı.");
    }
}