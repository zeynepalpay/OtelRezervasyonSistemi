/**
 * Oteldeki odaların temelini oluşturan soyut (abstract) sınıf.
 * Room sınıfı artık 'Reservable' arayüzünü uyguluyor (implements).
 * Yani rezervasyon kurallarına uymak zorunda.
 */
public abstract class Room implements Reservable {

    // Odanın numarası (Değiştirilemez)
    private final int roomNumber;

    // Odanın gecelik temel fiyatı (Alt sınıflar erişebilsin diye protected)
    protected double price;

    // Odanın dolu olup olmadığını tutan değişken
    // True = Dolu, False = Boş
    private boolean isOccupied;

    // Kurucu Metot
    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isOccupied = false; // Oda ilk oluşturulduğunda boştur.
    }

    // Soyut Metot (Alt sınıflar dolduracak)
    public abstract double calculatePrice();

    // --- Reservable Arayüzünden Gelen Zorunlu Metotlar ---

    // 1. Rezervasyon Yap
    @Override
    public void makeReservation() {
        if (!isOccupied) {
            isOccupied = true;
            System.out.println("Oda " + roomNumber + " için rezervasyon yapıldı.");
        } else {
            System.out.println("HATA: Oda " + roomNumber + " zaten dolu!");
        }
    }

    // 2. Rezervasyonu İptal Et
    @Override
    public void cancelReservation() {
        if (isOccupied) {
            isOccupied = false;
            System.out.println("Oda " + roomNumber + " rezervasyonu iptal edildi.");
        } else {
            System.out.println("HATA: Oda " + roomNumber + " zaten boş!");
        }
    }

    // 3. Müsait mi?
    @Override
    public boolean isAvailable() {
        return !isOccupied; // Dolu değilse müsaittir.
    }

    // Getter Metodu
    public int getRoomNumber() {
        return roomNumber;
    }
}