# ModulerElektrikliAracBataryaApp

Bu proje, üniversite mühendislik projesi kapsamında geliştirilmiş olan **MODÜLER ELEKTRİKLİ ARAÇ VE ENERJİ DEPOLAMA SİSTEMİ** (Modüler Batarya Yönetim ve İzleme Paneli) mobil uygulamasıdır. Proje tamamen **Java** dili ve **XML** arayüz şablonları kullanılarak, Android Studio standartlarına uygun olarak tasarlanmıştır.

## Teknolojiler ve Yapı

-   **Dil:** Java (Android SDK 11+)
-   **Arayüz:** Klasik XML Tasarımları (Jetpack Compose kullanılmamıştır)
-   **Tasarım Yaklaşımı:** Material Design 3, yuvarlatılmış köşeli modern bilgi kartları (Card-Based UI), dinamik durum renklendirmeleri ve akıcı bir yerleşim düzeni (ScrollView tabanlı).
-   **Durum Yönetimi:** Çevrimdışı kararlı simülasyon, zamanlayıcılar (Handler & Runnable) ve güvenlik eşikleriyle bütünleşik karar ağacı mekanizması.

---

## Proje Bileşenleri ve Ekranlar

### 1. Giriş Ekranı (Giriş Arayüzü)
Uygulama açılışında kullanıcıyı karşılayan profesyonel bir kimlik doğrulama ekranıdır.
-   **Yetkili Kimlik Bilgileri:**
    -   **Kullanıcı Adı:** `admin`
    -   **Şifre:** `1453`
-   **Çalışma Mantığı:** Bilgiler doğru girildiğinde simüle edilmiş bir JWT Token (`JWT_TOKEN_ADMIN_1453`) ve oturum bilgisi `SharedPreferences` üzerine kaydedilir ve kullanıcı doğrudan gösterge paneline yönlendirilir. Bilgiler yanlış girilirse alanlar temizlenerek kırmızı renkli **"Hatalı Giriş! Yetkisiz Erişim."** uyarısı gösterilir.

### 2. Veri Simülatörü ve Ana Gösterge (Batarya İzleme Paneli)
Batarya hücrelerinin ve modüllerinin anlık teknik durumlarını izleyen kontrol panelidir.
Ekranın en üstünde hücre disiplinleri tarafından onaylanmış şu teknik sabitler yer alır:
-   **Batarya Kimyası:** LFP (Lityum Demir Fosfat)
-   **Nominal Kapasite:** 29 kWh (6 adet 5 kWh'lik modüler yapı)
-   **İdeal Çalışma Sıcaklığı:** 25°C
-   **Kulombik Verimlilik:** %99.9
-   **Enerji Verimliliği:** %92 - %95

Panelde izlenen anlık metrikler:
-   **Şarj Durumu (SOC):** %0 ile %100 arasında yatay ilerleme çubuğu (ProgressBar) ile gösterilir.
-   **Gerilim (V), Akım (A), Sıcaklık (°C)** bilgileri anlık olarak kartlar üzerinde güncellenir.
-   **Sistem Durumu ve Uyarı Mesajı:** Sistem durumuna bağlı olarak dinamik arka plan rengi ve mesaj içeriği değişir.

### 3. Karar Ağacı ve Güvenlik Kilidi (DecisionEngine)
Giriş yapan sensör verilerini işleyerek üç farklı durumda güvenlik kilidini devreye alan otonom akıllı karar mekanizmasıdır:

-   **FAULT (HATA) Durumu:**
    -   *Koşullar:* Sıcaklık > 60°C VEYA Sıcaklık < -10°C VEYA Gerilim > 400V VEYA Gerilim < 300V VEYA SOC < %10.
    -   *Eylem:* Güvenlik kilidi **AKTİF** hale gelir. Akım anında **0.0 A** değerine sabitlenir. Arayüz kırmızı renge bürünür. Simülasyon otomatik olarak durdurulur.
    -   *Mesaj:* "Kritik hata! Güvenlik kilidi aktif."
-   **WARNING (UYARI) Durumu:**
    -   *Koşullar:* FAULT yoksa ve (50°C <= Sıcaklık <= 60°C VEYA 5°C <= Sıcaklık <= 10°C VEYA 300V <= Gerilim <= 350V VEYA %10 <= SOC < %20).
    -   *Eylem:* Güvenlik kilidi PASİF'tir. Arayüz turuncu/sarı renge bürünür.
    -   *Mesaj:* "Değerler sınıra yaklaşıyor. Dikkatli olun."
-   **NORMAL Durumu:**
    -   *Koşullar:* FAULT ve WARNING durumları tetiklenmediyse.
    -   *Eylem:* Güvenlik kilidi PASİF'tir. Arayüz yeşil renkte stabil kalır.
    -   *Mesaj:* "Sistem stabil. Tüm değerler güvenli aralıkta."

---

## Manüel Durum Testleri
Kullanıcıların ve jürinin kararlılık testi yapabilmesi için panelin altına üç adet manüel test butonu eklenmiştir. Bu butonlara tıklandığında anında şu zorunlu değerler yüklenerek otonom karar mekanizmasının tepkisi canlı olarak gözlemlenebilir:

1.  **NORMAL Test:** SOC: %76, Gerilim: 360.0 V, Akım: 12.5 A, Sıcaklık: 25.0 °C
2.  **WARNING Test:** SOC: %18, Gerilim: 330.0 V, Akım: 14.2 A, Sıcaklık: 52.0 °C
3.  **FAULT Test:** SOC: %8, Gerilim: 290.0 V, Akım: 20.5 A, Sıcaklık: 61.0 °C

---

## Çalıştırma Talimatları (Nasıl Çalıştırılır?)

1.  Uygulama klasörünü bilgisayarınıza indirin veya doğrudan GitHub'dan çekin.
2.  **Android Studio** programını açın ve **"Open"** diyerek projenin ana klasörünü seçin.
3.  Android Studio'nun Gradle yapılandırmasını eşitlemesini (Gradle Sync) bekleyin.
4.  Eşitleme tamamlandıktan sonra üst menüdeki yeşil **"Run" (Oynat)** butonuna basarak uygulamayı emülatörde veya gerçek cihazınızda test edebilirsiniz.
5.  Giriş bilgilerini yazın ve simülasyon adımlarını gözlemleyin.
