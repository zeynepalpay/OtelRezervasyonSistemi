public class Main {
    public static void main(String[] args) {

        System.out.println("=== OTEL REZERVASYON SİSTEMİ TEST BAŞLANGICI ===");
        System.out.println();

        // Standart oda nesnesi oluşturulup fiyatın doğru gelip gelmediği test edilir.
        StandardRoom stdOda = new StandardRoom(101, 1000.0);

        System.out.println("Oda No: " + stdOda.getRoomNumber());
        System.out.println("Oda Tipi: Standart");
        System.out.println("Fiyat: " + stdOda.calculatePrice() + " TL");

        System.out.println("-----------------------------");

        // Deluxe oda nesnesi oluşturulur.
        // Fiyat hesaplama metodunun (Override edilen) %20 fark ekleyip eklemediği kontrol edilir.
        DeluxeRoom luksOda = new DeluxeRoom(201, 1000.0);

        System.out.println("Oda No: " + luksOda.getRoomNumber());
        System.out.println("Oda Tipi: Deluxe");
        System.out.println("Fiyat: " + luksOda.calculatePrice() + " TL");

        System.out.println();
        System.out.println("=== TEST SONU ===");
    }
}