import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private String generatedFileName = "fatura_MustafaAlkan.txt";

    @Test
    void testInvoiceFileCreation() {
        // Hazırlık
        Customer customer = new Customer(301, "Mustafa Alkan", "5321234567");
        Room room = new StandardRoom(303, 1000.0);
        Reservation rez = new Reservation(customer, room, "01.01.2026", 1);

        // İşlem: Fatura oluştur
        FileService.writeInvoice(rez);

        // Kontrol
        File file = new File(generatedFileName);
        assertTrue(file.exists(), "HATA: Fatura dosyası oluşturulamadı!");

        // İçerik Kontrolü
        assertTrue(file.length() > 0, "HATA: Fatura dosyası boş!");
    }

    @AfterEach
    void tearDown() {
        // Test bittikten sonra oluşan sahte faturayı sil ki klasör kirlenmesin
        File file = new File(generatedFileName);
         if (file.exists()) {
            file.delete();
        }
    }
}