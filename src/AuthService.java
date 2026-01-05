import java.io.*;
import java.util.ArrayList;

public class AuthService {
    private ArrayList<User> userList;
    private final String FILE_PATH = "kullanicilar.txt";

    public AuthService() {
        this.userList = new ArrayList<>();
        loadUsersFromFile();
    }

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
                    // DOSYA SIRALAMASI: admin, 123, İsim, Rol
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