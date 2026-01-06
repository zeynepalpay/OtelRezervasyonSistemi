import java.io.*;
import java.util.ArrayList;
/**
 * Sisteme giriş yapacak herkesin (Admin veya Müşteri) kullanıcı adı ve şifresini
 * kontrol eden, giriş güvenliğinden sorumlu sınıf.
 * Kullanıcı bilgilerini 'kullanicilar.txt' dosyasından çekerek kimlik doğrulaması yapar.
 */
public class AuthService {
    /** Kayıtlı tüm kullanıcıların tutulduğu liste */
    private ArrayList<User> userList;
    /** Kullanıcı verilerinin saklandığı dosya adı */
    private final String FILE_PATH = "kullanicilar.txt";

    /**
     * AuthService kurucu metodu.
     * Nesne oluşturulduğu anda listeyi hazırlar ve dosyadaki verileri yükler.
     */
    public AuthService() {
        this.userList = new ArrayList<>();
        loadUsersFromFile();
    }

    /**
     * Kullanıcı verilerini dosyadan (kullanicilar.txt) okuyan metot.
     * Eğer dosya bulunamazsa sistemin kilitlenmemesi için varsayılan bir admin hesabı oluşturur.
     * Her satırı virgülle parçalara ayırarak User nesnelerini listeye ekler.
     */
    private void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("⚠️ UYARI: kullanicilar.txt bulunamadı!");
            userList.add(new User("admin", "123", "ADMIN", "Sistem Yoneticisi"));
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length == 4) {
                    // Verileri parçalayarak değişkenlere atıyoruz.
                    String uName = data[0].trim();
                    String pass  = data[1].trim();
                    String name  = data[2].trim();
                    String role  = data[3].trim();

                    // User Constructor (username, password, role, fullName)
                    userList.add(new User(uName, pass, role, name));
                }
            }
            System.out.println("✅ SİSTEM: " + userList.size() + " kullanıcı başarıyla çekildi.");
        } catch (IOException e) {
            System.out.println("❌ HATA: Dosya okuma hatası!");
        }
    }

    /**
     * Kullanıcının girdiği bilgileri listedeki verilerle karşılaştıran giriş metodu.
     * * @param username Kullanıcının ekrandan girdiği kullanıcı adı
     * @param password Kullanıcının ekrandan girdiği şifre
     * @return Eğer bilgiler doğruysa User nesnesini döner, hatalıysa null döner.
     */
    public User login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("✅ GİRİŞ BAŞARILI! Hoşgeldin, " + user.getFullName());
                return user;
            }
        }
        System.out.println("❌ HATA: Kullanıcı adı veya şifre hatalı!");
        return null;
    }
}