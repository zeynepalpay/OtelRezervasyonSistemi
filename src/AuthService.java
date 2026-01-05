import java.util.ArrayList;
/**
 * Kimlik Doğrulama Servisi.
 * Kullanıcı giriş işlemlerini ve kayıtlı kullanıcı listesini yönetir.
 */
public class AuthService {

    // Kayıtlı kullanıcıları tutan "Sanal Veritabanı" listemiz
    private ArrayList<User> userList;

    public AuthService() {
        this.userList = new ArrayList<>();

        // --- ÖRNEK KULLANICILAR OLUŞTURUYORUZ ---

        // 1. Yönetici (Admin)
        // Kullanıcı Adı: admin, Şifre: 123
        userList.add(new User("admin", "123", "ADMIN", "Sistem Yöneticisi"));

        // 2. Müşteri (Customer)
        // Kullanıcı Adı: zeynep, Şifre: 1234
        userList.add(new User("zeynep", "1234", "CUSTOMER", "Zeynep Yildiz"));
        userList.add(new User("tuğberk", "1234","CUSTOMER","Tuğberk Kocatekin"));
    }

    // --- GİRİŞ KONTROLÜ (LOGIN) ---
    // Eğer kullanıcı adı ve şifre doğruysa, o User nesnesini geri döndürür.
    // Yanlışsa 'null' (boş) döndürür.
    public User login(String username, String password) {
        for (User user : userList) {
            // Hem kullanıcı adı hem şifre eşleşiyor mu?
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("✅ GİRİŞ BAŞARILI! Hoşgeldin, " + user.getFullName());
                return user; // Giriş yapan kullanıcıyı sisteme bildiriyoruz
            }
        }

        // Döngü bitti ve kimse bulunamadıysa:
        System.out.println("❌ HATA: Kullanıcı adı veya şifre hatalı!");
        return null;
    }
}