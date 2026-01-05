import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    @Test
    void testInvoiceFileCreation() {

        Customer customer = new Customer(301, "Mustafa Alkan", "5321234567");
        Room room = new StandardRoom(303, 1000.0);

        Reservation rez = new Reservation(customer, room, "01.01.2026", 1);

        // Fatura oluştur
        FileService.writeInvoice(rez);
        String expectedFileName = "fatura_MustafaAlkan.txt";

        File file = new File(expectedFileName);

        // Kontrol
        assertTrue(file.exists(), "HATA: Fatura dosyası bulunamadı! (İsimde boşluk hatası olabilir)");

        // 4. BİLGİLENDİRME:
        System.out.println("✅ Test Başarılı! Dosya şurada: " + file.getAbsolutePath());
    }
}