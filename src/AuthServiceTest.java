import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void testLoginSecurity() {
        // 1. HAZIRLIK: Güvenlik servisini başlat (Kullanıcılar yükleniyor)
        AuthService auth = new AuthService();

        System.out.println("🕵️‍♂️ GÜVENLİK TESTİ BAŞLIYOR...");

        // --- SENARYO 1: BAŞARILI GİRİŞ (Admin) ---
        User adminUser = auth.login("admin", "123");

        // Beklenti: Kullanıcı boş gelmemeli (Giriş başarılı olmalı)
        assertNotNull(adminUser, "HATA: Doğru şifreye rağmen giriş yapılamadı!");
        // Beklenti: Rolü "ADMIN" olmalı
        assertEquals("ADMIN", adminUser.getRole(), "HATA: Admin yetkisi doğru gelmedi!");
        System.out.println("✅ 1. Senaryo (Doğru Giriş) Başarılı.");


        // --- SENARYO 2: YANLIŞ ŞİFRE ---
        User wrongPassUser = auth.login("admin", "999999");

        // Beklenti: Kullanıcı 'null' gelmeli (Giriş başarısız olmalı)
        assertNull(wrongPassUser, "HATA: Yanlış şifreyle sisteme girilebildi! (Güvenlik Açığı)");
        System.out.println("✅ 2. Senaryo (Yanlış Şifre) Başarılı.");


        // --- SENARYO 3: KAYITSIZ KULLANICI ---
        User ghostUser = auth.login("hayalet_casper", "1234");

        // Beklenti: 'null' gelmeli
        assertNull(ghostUser, "HATA: Sistemde olmayan kullanıcı giriş yapabildi!");
        System.out.println("✅ 3. Senaryo (Kayıtsız Kullanıcı) Başarılı.");
    }
}