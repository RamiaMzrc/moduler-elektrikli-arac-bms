# EKRAN 1: Login Activity Project

Bu proje, referans gorseldeki elektrikli arac/batarya kokpiti temasini Android Login Activity olarak uygular.

## Ekranlar

- `LoginActivity`: Gorseldeki ana login karti, e-posta/sifre alanlari, sifre goster/gizle ve sayfa gecisleri.
- `RegisterActivity`: Yeni kullanici kaydi yapar ve bilgileri `SharedPreferences` ile saklar.
- `ForgotPasswordActivity`: Kayitli kullanici sifresini gunceller.
- `DashboardActivity`: Giris sonrasi arac kontrol paneli, menzil, SOC, sunucu durumu, performans, pil dizisi ve harita durum kartlari.
- `BatteryMonitoringActivity`: Arac kontrol panelindeki `BATARYA IZLEME PANELI` butonu ile acilan bos teslim ekrani. Bu ekranin icerigi sonraki ekip tarafindan kodlanacaktir.

## Demo giris

- E-posta: `demo@moduler.ev`
- Sifre: `123456`

Kayit ekranindan yeni kullanici olusturdugunuzda uygulama bu bilgilerle giris yapar.

## Android Studio'da acma

1. Android Studio'yu acin.
2. `Open` secenegi ile bu klasoru secin: `EVLoginActivityProject`.
3. Gradle sync tamamlaninca uygulamayi emulator veya cihaza calistirin.

## Derleme

Bu proje `gradlew.bat assembleDebug` ile derlenmistir. Android Studio icinde `Run` veya `Build > Make Project` kullanabilirsiniz.
