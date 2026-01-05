/**
 * Oteldeki odaların temelini oluşturan soyut (abstract) sınıf.
 */
public abstract class Room implements Reservable {

    private final int roomNumber;
    protected double price;
    private boolean isOccupied;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isOccupied = false;
    }

    public abstract double calculatePrice();

    // --- Reservable Arayüzünden Gelen Metotlar ---

    @Override
    public void makeReservation() {
        if (!isOccupied) {
            isOccupied = true;
            System.out.println("Oda " + roomNumber + " için rezervasyon yapıldı.");
        } else {
            System.out.println("HATA: Oda " + roomNumber + " zaten dolu!");
        }
    }

    @Override
    public void cancelReservation() {
        if (isOccupied) {
            isOccupied = false;
            System.out.println("Oda " + roomNumber + " rezervasyonu iptal edildi.");
        } else {
            System.out.println("HATA: Oda " + roomNumber + " zaten boş!");
        }
    }

    @Override
    public boolean isAvailable() {
        return !isOccupied;
    }

    /**
     * Odanın durumunu dışarıdan değiştirmek için kullanılır.
     * @param available true ise oda boşaltılır, false ise oda doldurulur.
     */
    public void setAvailable(boolean available) {
        // available true (müsait) gelirse -> isOccupied false olmalı
        // available false (dolu) gelirse -> isOccupied true olmalı
        this.isOccupied = !available;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}