import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void testLoginSecurity() {
        // 1. HAZIRLIK: Güvenlik servisini başlat
        AuthService auth = new AuthService();

        System.out.println("🕵️‍♂️ GÜVENLİK TESTİ BAŞLIYOR...");

        // --- SENARYO 1: BAŞARILI GİRİŞ (Admin) ---
        User adminUser = auth.login("admin", "123");

        assertNotNull(adminUser, "HATA: Doğru şifreye rağmen giriş yapılamadı!");

        // trim() ve toUpperCase() ile veriyi garantiye alıyoruz
        assertEquals("ADMIN", adminUser.getRole().trim().toUpperCase(), "HATA: Admin rolü doğru gelmedi!");
        System.out.println("✅ 1. Senaryo (Doğru Giriş) Başarılı.");

        // --- SENARYO 2: YANLIŞ ŞİFRE ---
        User wrongPassUser = auth.login("admin", "999999");
        assertNull(wrongPassUser, "HATA: Yanlış şifreyle sisteme girilebildi!");
        System.out.println("✅ 2. Senaryo (Yanlış Şifre) Başarılı.");

        // --- SENARYO 3: KAYITSIZ KULLANICI ---
        User ghostUser = auth.login("hayalet_casper", "1234");
        assertNull(ghostUser, "HATA: Sistemde olmayan kullanıcı giriş yapabildi!");
        System.out.println("✅ 3. Senaryo (Kayıtsız Kullanıcı) Başarılı.");
    }
}