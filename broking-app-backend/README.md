# Smart Investor Broking App Backend

A comprehensive Spring Boot backend for a broking application with Zerodha API integration for market data and DigiLocker integration for KYC verification.

## Features

### Core Features
- **User Authentication & Authorization** with JWT tokens
- **Email Verification** with OTP
- **Portfolio Management**
- **Trading Operations** (Place, modify, cancel orders)
- **Watchlist Management**
- **Funds Management**
- **Research & Reports**
- **Admin Panel**

### New Integrations

#### 1. Zerodha API Integration
- **Real-time Market Data** from Zerodha Kite API
- **Live Stock Quotes**
- **Historical Data** with customizable intervals
- **Options Chain Data**
- **Instrument Lists**

#### 2. DigiLocker KYC Integration
- **Aadhaar Verification** via DigiLocker
- **PAN Card Verification**
- **Driving License Verification**
- **Passport Verification**
- **Automated KYC Processing**

## API Endpoints

### Market Data (Zerodha Integration)
```
GET /api/marketdata/indices                    # Get Nifty and BankNifty data
GET /api/marketdata/stocks/{symbol}            # Get live stock quote
GET /api/marketdata/historical/{symbol}        # Get historical data
GET /api/marketdata/options/{symbol}           # Get options chain
GET /api/marketdata/instruments/{exchange}     # Get instrument list
```

### KYC (DigiLocker Integration)
```
GET /api/kyc/digilocker/auth                   # Get DigiLocker auth URL
GET /api/kyc/digilocker/callback               # Process DigiLocker callback
POST /api/kyc/digilocker/update/{userId}      # Update KYC with DigiLocker data
GET /api/kyc/digilocker/aadhaar               # Get Aadhaar details
GET /api/kyc/digilocker/pan                   # Get PAN details
GET /api/kyc/digilocker/driving-license       # Get driving license
GET /api/kyc/digilocker/passport              # Get passport details
```

### Authentication
```
POST /api/auth/register                        # User registration
POST /api/auth/login                           # Login with password
POST /api/auth/verify-otp                     # Verify email OTP
POST /api/auth/login-otp-request              # Request login OTP
POST /api/auth/login-otp                      # Login with OTP
```

## Configuration

### Application Properties
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/brokingdb
spring.datasource.username=root
spring.datasource.password=1307

# JWT
app.jwt.secret=brokingapp@123

# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# Zerodha API
zerodha.api.key=YOUR_API_KEY
zerodha.api.secret=YOUR_API_SECRET
zerodha.user.id=YOUR_USER_ID

# DigiLocker API
digilocker.client.id=YOUR_DIGILOCKER_CLIENT_ID
digilocker.client.secret=YOUR_DIGILOCKER_CLIENT_SECRET
digilocker.redirect.uri=http://localhost:8080/api/kyc/digilocker/callback
```

## Setup Instructions

### 1. Database Setup
```sql
CREATE DATABASE brokingdb;
```

### 2. Zerodha API Setup
1. Register on Zerodha Kite Connect
2. Get API credentials from Zerodha
3. Update `application.properties` with your credentials

### 3. DigiLocker API Setup
1. Register as a DigiLocker partner
2. Get client credentials from DigiLocker
3. Update `application.properties` with your credentials

### 4. Email Setup
1. Configure Gmail SMTP settings
2. Update email credentials in `application.properties`

### 5. Run the Application
```bash
mvn spring-boot:run
```

## API Usage Examples

### Get Live Market Data
```bash
curl -X GET "http://localhost:8080/api/marketdata/stocks/RELIANCE" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Initiate DigiLocker KYC
```bash
# Step 1: Get authorization URL
curl -X GET "http://localhost:8080/api/kyc/digilocker/auth?state=user123"

# Step 2: User completes DigiLocker flow
# Step 3: Process callback
curl -X GET "http://localhost:8080/api/kyc/digilocker/callback?code=AUTH_CODE&state=user123"
```

## Security Features

- **JWT Authentication** for all protected endpoints
- **CORS Configuration** for frontend integration
- **Password Encryption** using BCrypt
- **Email Verification** for user registration
- **Role-based Access Control**

## Technology Stack

- **Spring Boot 3.5.3**
- **Spring Security** with JWT
- **Spring Data JPA**
- **MySQL Database**
- **WebFlux** for reactive API calls
- **Jackson** for JSON processing
- **Lombok** for boilerplate reduction

## Mock Data

For development and testing purposes, the application includes mock data generators:
- **Zerodha API**: Returns realistic mock market data
- **DigiLocker API**: Returns mock KYC documents

This allows development without real API credentials while maintaining realistic data structures.

## Error Handling

The application includes comprehensive error handling:
- **API Timeout Handling**
- **Fallback to Mock Data**
- **Proper HTTP Status Codes**
- **Detailed Error Messages**

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License. 