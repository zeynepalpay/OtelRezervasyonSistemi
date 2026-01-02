/**
 * Rezerve Edilebilir (Reservable) Arayüzü.
 * Bu arayüzü uygulayan (implements) her sınıf, aşağıdaki metotları içermek ZORUNDADIR.
 * Yani bu bir "Sözleşme"dir.
 */
public interface Reservable {

    // Rezervasyon yapma kuralı
    void makeReservation();

    // Rezervasyonu iptal etme kuralı
    void cancelReservation();

    // Odanın müsaitlik durumunu sorma kuralı
    boolean isAvailable();
}