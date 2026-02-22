# EduGlobal Layihəsi - Backend və Frontend Əlaqəsi

## Tələblər

1. **Java 17** - Backend üçün
2. **Maven** - Dependency management üçün
3. **MySQL** - Verilənlər bazası üçün
4. **Web Browser** - Frontend-i görmək üçün

## Quraşdırma və İşə Salınması

### 1. MySQL Verilənlər Bazasını Hazırlayın

MySQL-də `eduglobal` adlı verilənlər bazası yaradılmalıdır (və ya backend avtomatik yaradacaq).

Backend konfiqurasiyası (`backend/src/main/resources/application.yml`):
- Host: `localhost:3306`
- Database: `eduglobal`
- Username: `root`
- Password: `1234`

Əgər fərqli parametrlər istifadə edirsinizsə, `application.yml` faylını yeniləyin.

### 2. Backend-i İşə Salın

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend `http://localhost:8081` ünvanında işləyəcək.

### 3. Frontend-i Açın

**Seçim 1: Spring Boot vasitəsilə (Tövsiyə olunur)**
- Backend işlədikdən sonra brauzerdə `http://localhost:8081/index.html` ünvanına daxil olun
- Və ya `http://localhost:8081/` - Spring Boot avtomatik `index.html`-i tapacaq

**Seçim 2: Ayrı HTTP Server**
- `fronted` qovluğunda ayrı bir HTTP server işə salın:
  ```bash
  cd fronted
  # Python 3 istifadə edərək:
  python -m http.server 8080
  # Və ya Node.js http-server:
  npx http-server -p 8080
  ```
- Sonra brauzerdə `http://localhost:8080` ünvanına daxil olun
- **Qeyd:** Bu halda frontend-də API URL-i `http://localhost:8081` olmalıdır (bu artıq konfiqurasiya edilib)

## Test Etmək

1. Backend-in işlədiyini yoxlayın:
   - `http://localhost:8081/api/reviews` - GET sorğusu göndərin (rəyləri görmək üçün)
   - Swagger UI: `http://localhost:8081/swagger-ui.html`

2. Frontend-də rəy sistemi:
   - Ana səhifədə (`index.html`) rəy bölməsinə ad və rəy yazın
   - "Rəyi Göndər" düyməsinə basın
   - Rəy backend-ə göndərilməlidir və siyahıda görünməlidir

## Xətalar və Həllər

### Backend başlamır
- MySQL-in işlədiyini yoxlayın
- `application.yml`-də verilənlər bazası parametrlərini yoxlayın
- Port 8081-in boş olduğunu yoxlayın

### Frontend backend-ə qoşula bilmir (CORS xətası)
- Backend-in işlədiyini yoxlayın (`http://localhost:8081/api/reviews`)
- Browser console-da xətaları yoxlayın (F12)
- `WebConfig.java`-da CORS konfiqurasiyasını yoxlayın

### Static fayllar yüklənmir
- `WebConfig.java`-da resource handler konfiqurasiyasını yoxlayın
- `fronted` qovluğunun `backend` qovluğu ilə eyni səviyyədə olduğunu yoxlayın

## API Endpoint-ləri

### Reviews (Rəylər)
- `GET /api/reviews` - Bütün rəyləri əldə et
- `POST /api/reviews` - Yeni rəy əlavə et
  ```json
  {
    "name": "İstifadəçi adı",
    "text": "Rəy mətni"
  }
  ```

### Questions (Suallar)
- `GET /api/questions/exam` - İmtahan üçün təsadüfi suallar
- `GET /api/questions/{level}` - Müəyyən səviyyə üçün suallar (A1, B1, C1)

### Authentication (İstifadəçi)
- `POST /apis/login` - Giriş
- `POST /apis/refresh-token` - Token yenilə

## Struktur

```
eduglobal/
├── backend/          # Spring Boot backend
│   └── src/main/java/com/eduglobal/backend/
│       ├── config/   # Konfiqurasiya (CORS, Security, Web)
│       ├── controller/ # REST API endpoint-ləri
│       ├── service/   # Business logic
│       └── entity/    # Database entity-ləri
└── fronted/          # Frontend HTML/CSS/JS faylları
    ├── index.html
    ├── test.html
    └── ...
```

## Növbəti Addımlar

1. ✅ Backend və frontend arasında əlaqə quruldu
2. ✅ CORS konfiqurasiyası edildi
3. ✅ Review API frontend-də inteqrasiya edildi
4. ⏭️ Digər API endpoint-lərinin frontend-də istifadəsi
5. ⏭️ Authentication sisteminin frontend-də tətbiqi
