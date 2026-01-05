/**
 * Sisteme giriş yapacak herkesin (Admin veya Müşteri) ortak özelliklerini tutan sınıf.
 * Bu sınıf SADECE giriş (Login) işlemleri içindir.
 */
public class User {

    private final String username; //Kullanıcı adı
    private final String password; // Şifre
    private final String role; //Rolü: admin veya customer
    private final String fullName; //Gerçek isim

    public User(String username, String password, String role, String fullName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    // --- Getter Metotları ---
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getFullName() { return fullName; }
}