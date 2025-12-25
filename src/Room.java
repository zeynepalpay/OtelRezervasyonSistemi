/**
 * Oteldeki tüm odaların ortak özelliklerini tutan Ana (Ata) sınıf.
 * Standard ve Deluxe odalar buradan türetilecek.
 * Soyut (abstract) olduğu için tek başına nesne olarak üretilemez.
 */
public abstract class Room{
    //Odanın kapı numarası
    private int roomNumber;
    //Odanın gecelik temel fiyatı(inheritance ile alt sınıflar erişebilsin diye protected yaptım)
    protected double price;

    /**
     * Odanın fiyatını hesaplayan metot.
     * Her oda tipi (Deluxe/Standard) fiyatı farklı hesaplayacağı için
     * burada abstract bıraktım, alt sınıflar içini dolduracak.
     */
    public abstract double calculatePrice();

    //Oda numarasını okumak için getter metodu
    public int getRoomNumber() {
        return roomNumber;
    }

}