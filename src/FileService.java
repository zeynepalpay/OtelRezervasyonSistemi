import java.io.FileWriter;
import java.io.IOException;

/**
 * Otelimizin dosya işlemlerini (File I/O) yönettiğim yardımcı sınıftır.
 * Temel görevi; rezervasyon işlemi bittiğinde fatura detaylarını bir .txt dosyasına kaydetmektir.
 * Kodun karmaşıklaşmaması için "Single Responsibility" prensibine uygun olarak bu işi ayrı bir sınıfta topladım.
 */
public class FileService {

    /**
     * Tamamlanmış bir rezervasyonun tüm detaylarını alarak kişiye özel bir fatura dosyası oluşturur.
     * Bu metot 'static' olduğu için sınıfı 'new' yapmadan, direkt dosya ismiyle çağrılabilir.
     * * @param reservation Faturası kesilecek olan rezervasyon nesnesi (Müşteri, oda ve yemek bilgileri buradadır).
     */
    public static void writeInvoice(Reservation reservation) {

        // 1. Dosya ismini oluşturuyorum.
        // Müşterinin adını alıp boşlukları siliyorum (Örn: "Zeynep Kaya" -> "fatura_ZeynepKaya.txt" oluyor).
        String fileName = "fatura_" + reservation.getCustomerName().replaceAll(" ", "") + ".txt";

        try {
            // 2. Dosya yazma nesnesini (FileWriter) başlatıyorum.
            FileWriter writer = new FileWriter(fileName);

            // 3. Faturanın başlık kısmını yazıyorum.
            writer.write("=====================================\n");
            writer.write("       SKYLINE HOTEL - FATURA        \n");
            writer.write("=====================================\n");

            // 4. Rezervasyon detaylarını (Oda, Fiyat, Yemekler) yazdırıyorum.
            // Reservation sınıfındaki 'toString' metodu tüm özeti zaten veriyor, onu kullandım.
            writer.write(reservation.toString());

            // 5. Alt bilgi ve kapanış.
            writer.write("\n=====================================\n");
            writer.write("   Bizi tercih ettiğiniz için teşekkürler! \n");
            writer.write("   İşlem Tarihi: 05.01.2026 \n");

            // 6. İşlem bitince dosyayı kapatıyorum (Kaynakları tüketmemesi için önemli).
            writer.close();

            System.out.println("📄 BİLGİ: Fatura başarıyla '" + fileName + "' dosyasına kaydedildi.");

        } catch (IOException e) {
            // Dosya oluşturulurken bir hata çıkarsa (izin yoksa vs.) burada yakalıyorum.
            System.out.println("⚠️ HATA: Fatura dosyası oluşturulamadı!");
            e.printStackTrace();
        }
    }
}