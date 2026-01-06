import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Otelimizin giriş kapısındaki güvenliği, yani giriş sistemini test ettiğim sınıf.
 * Burada hem doğru bilgileri hem de kötü niyetli veya hatalı giriş denemelerini
 * simüle ederek sistemin açık vermediğinden emin oluyorum.
 */
class AuthServiceTest {
    /**
     * Sistemin giriş güvenliğini 3 farklı senaryoda test eden ana metodum.
     * Amacım; doğru şifreyle giriliyor mu, yanlış şifre engelleniyor mu ve
     * sistemde olmayan biri sızabiliyor mu kontrol etmek.
     */
    @Test
    void testLoginSecurity() {
        // 1. HAZIRLIK: Güvenlik servisini başlat
        AuthService auth = new AuthService();

        System.out.println("🕵️‍♂️ GÜVENLİK TESTİ BAŞLIYOR...");

        // --- SENARYO 1: BAŞARILI GİRİŞ (Admin) ---
        // Burada her şeyin doğru olduğu durumu test ediyoruz.
        User adminUser = auth.login("admin", "123");

        // Kontrol: Eğer bilgiler doğruysa sistem bize boş (null) olmayan bir kullanıcı döndürmeli
        assertNotNull(adminUser, "HATA: Doğru şifreye rağmen giriş yapılamadı!");

        // Ekstra Kontrol: Giriş yapanın gerçekten ADMIN yetkisinde olup olmadığına bakıyoruz.
        // trim() ve toUpperCase() kullanarak küçük-büyük harf veya boşluk hatalarını eliyoruz.
        assertEquals("ADMIN", adminUser.getRole().trim().toUpperCase(), "HATA: Admin rolü doğru gelmedi!");
        System.out.println("✅ 1. Senaryo (Doğru Giriş) Başarılı.");

        // --- SENARYO 2: YANLIŞ ŞİFRE ---
        // Burada "Kullanıcı adı doğru ama şifre yanlışsa ne olur?" sorusunu soruyoruz.
        User wrongPassUser = auth.login("admin", "999999");

        // Beklentimiz: Sistemin 'null' dönerek girişe izin vermemesi.
        assertNull(wrongPassUser, "HATA: Yanlış şifreyle sisteme girilebildi!");
        System.out.println("✅ 2. Senaryo (Yanlış Şifre) Başarılı.");

        // --- SENARYO 3: KAYITSIZ KULLANICI ---
        // Sistemde hiç olmayan bir kullanıcı giriş yapmaya çalışırsa ne olacak?
        User ghostUser = auth.login("hayalet_casper", "1234");

        // Beklentimiz: Tanınmayan kullanıcı için de 'null' dönmesi ve kapının açılmaması.
        assertNull(ghostUser, "HATA: Sistemde olmayan kullanıcı giriş yapabildi!");
        System.out.println("✅ 3. Senaryo (Kayıtsız Kullanıcı) Başarılı.");
    }
}