# MODÜLER ELEKTRİKLİ ARAÇ VE ENERJİ DEPOLAMA ÜNİTESİ SİSTEMİ

Bu proje, Artan enerji talebi, fosil yakıtların çevresel etkileri ve sürdürülebilirlik gereksinimleri, ulaşım sektöründe elektrikli araçlara yönelik dönüşümü hızlandırmaktadır. Elektrikli araçlar düşük emisyon ve yüksek enerji verimliliği avantajlarına sahip olmakla birlikte, menzil kaygısı, uzun şarj süreleri ve yetersiz şarj altyapısı gibi sorunlar yaygın kullanımın önünde önemli engeller oluşturmaktadır. Özellikle gelişmekte olan ülkelerde bu altyapı eksiklikleri daha belirgin hâle gelmekte ve kullanıcı deneyimini olumsuz etkilemektedir. Bu durum, mevcut şarj paradigmasına alternatif çözümlerin geliştirilmesini gerekli kılmaktadır.

Bu çalışma, söz konusu problemlere çözüm olarak modüler ve değiştirilebilir batarya sistemlerini ele almaktadır. Önerilen yaklaşımda batarya, araçtan bağımsız bir bileşen olarak değerlendirilmekte ve enerji dolumu, batarya değişimi yoluyla kısa sürede gerçekleştirilmektedir. Bu doğrultuda araştırmanın temel amacı; modüler batarya mimarisine sahip bir elektrikli araç konsepti ile bu sistemi destekleyen batarya değişim istasyonu tasarımını bütüncül bir yaklaşımla geliştirmektir. Çalışma kapsamında araç şasisi ve batarya kasasının mekanik entegrasyonu, Batarya Yönetim Sistemi (BMS), araç-istasyon iletişim altyapısı, istasyon tasarımı ve kullanıcı etkileşimi gibi temel bileşenler ele alınmaktadır. Ayrıca sistemin uygulanabilirliğini değerlendirmek amacıyla ekonomik fizibilite, yaşam döngüsü maliyeti ve güvenlik kriterleri de analiz edilmektedir.

Türkiye özelinde elektrikli araç kullanımının artırılmasına yönelik politikalar ve yerli üretim girişimleri, şarj altyapısının geliştirilmesini kritik bir gereklilik hâline getirmiştir. Bu bağlamda batarya değişim sistemleri, özellikle ticari araçlar ve yoğun kullanım senaryolarında hızlı enerji erişimi sağlayarak önemli avantajlar sunmaktadır. Bu çalışma, mühendislik, yazılım ve tasarım disiplinlerini bir araya getiren bütünleşik bir sistem önerisi sunarak literatüre katkı sağlamayı ve elektrikli araç ekosisteminde karşılaşılan temel sorunlara uygulanabilir bir çözüm geliştirmeyi amaçlamaktadır.


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
