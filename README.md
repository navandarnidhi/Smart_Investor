
# TRADING PLATFORM

Full stack project using **Spring Boot** (backend) and **React** (frontend)


### ER Diagram (ASCII)

```
+---------------------+           +-----------------+
|       Users         |<--------->|    Wallets      |
|---------------------|           +-----------------+
| id                  |               ^            
| fullName            |               |
| email               |               |         
| ...                 |               |
+---------------------+               |
                                      |
+--------------------+            +---------------------+
|      Assets        |<---------->| WalletTransactions  |
|--------------------|            +---------------------+
| id                 |
| quantity           |
| buy_price          |<---------->+-----------------+
| coin_id            |            |     Coins       |
| user_id            |            +-----------------+
+--------------------+            | id              |
                                  | symbol          |
+--------------------+            | ...             |
| Withdrawals        |<---------->+-----------------+
|--------------------|
| id                 |
| status             |
| amount             |
| user_id            |
| date               |
+--------------------+

+--------------------+
| Watchlists         |
|--------------------+
| id                 |
| user_id            |
+--------------------+
          |
          |
          v
+--------------------+
| Watchlist_Coins    |
|--------------------+
| watchlist_id       |
| coin_id            |
+--------------------+

+---------------------+           +---------------------+
|   VerificationCodes |<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| otp                 |
| user_id             |
| email               |
| mobile              |
| verification_type   |
+---------------------+

+---------------------+           +---------------------+
|  TradingHistories   |<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| selling_price       |
| buying_price        |
| coin_id             |
| user_id             |
+---------------------+

+---------------------+           +---------------------+
|    PaymentOrders    |<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| amount              |
| status              |
| payment_method      |
| user_id             |
+---------------------+

+---------------------+           +---------------------+
|   PaymentDetails    |<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| account_number      |
| account_holder_name |
| ifsc                |
| bank_name           |
| user_id             |
+---------------------+

+---------------------+           +---------------------+
|        Orders       |<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| user_id             |
| order_type          |
| price               |
| timestamp           |
| status              |
| order_item_id       |
+---------------------+
          |
          |
          v
+---------------------+           +---------------------+
|      OrderItems     |<--------->|        Coins        |
|---------------------|           +---------------------+
| id                  |
| quantity            |
| coin_id             |
| buy_price           |
| sell_price          |
| order_id            |
+---------------------+

+---------------------+             +---------------------+
|    Notifications    | <---------> |        Users        |
|---------------------|             +---------------------+
| id                  |
| from_user_id        |
| to_user_id          |
| amount              |
| message             |
+---------------------+

+---------------------+           
|   MarketChartData   |
|---------------------|
| id                  |
| timestamp           |
| price               |
+---------------------+

+---------------------+           +---------------------+
| ForgotPasswordTokens|<--------->|        Users        |
|---------------------|           +---------------------+
| id                  |
| user_id             |
| otp                 |
| verification_type   |
| send_to             |
+---------------------+
```

---

## Database Tables

### Users

| Field                  | Type    |
| ---------------------- | ------- |
| id                     | bigint  |
| fullName               | varchar |
| email                  | varchar |
| mobile                 | varchar |
| password               | varchar |
| status                 | varchar |
| isVerified             | boolean |
| twoFactorAuth\_enabled | boolean |
| twoFactorAuth\_sendTo  | varchar |
| picture                | varchar |
| role                   | varchar |

### Coins

| Field                                | Type     |
| ------------------------------------ | -------- |
| id                                   | varchar  |
| symbol                               | varchar  |
| name                                 | varchar  |
| image                                | varchar  |
| current\_price                       | double   |
| market\_cap                          | bigint   |
| market\_cap\_rank                    | int      |
| fully\_diluted\_valuation            | bigint   |
| total\_volume                        | bigint   |
| high\_24h                            | double   |
| low\_24h                             | double   |
| price\_change\_24h                   | double   |
| price\_change\_percentage\_24h       | double   |
| market\_cap\_change\_24h             | bigint   |
| market\_cap\_change\_percentage\_24h | double   |
| circulating\_supply                  | bigint   |
| total\_supply                        | bigint   |
| max\_supply                          | bigint   |
| ath                                  | double   |
| ath\_change\_percentage              | double   |
| ath\_date                            | datetime |
| atl                                  | double   |
| atl\_change\_percentage              | double   |
| atl\_date                            | datetime |
| roi                                  | varchar  |
| last\_updated                        | datetime |

### Assets

| Field      | Type    |
| ---------- | ------- |
| id         | bigint  |
| quantity   | double  |
| buy\_price | double  |
| coin\_id   | varchar |
| user\_id   | bigint  |

### Withdrawals

| Field    | Type     |
| -------- | -------- |
| id       | bigint   |
| status   | varchar  |
| amount   | bigint   |
| user\_id | bigint   |
| date     | datetime |

### Watchlists

| Field    | Type   |
| -------- | ------ |
| id       | bigint |
| user\_id | bigint |

### Watchlist\_Coins

| Field         | Type    |
| ------------- | ------- |
| watchlist\_id | bigint  |
| coin\_id      | varchar |

### WalletTransactions

| Field        | Type     |
| ------------ | -------- |
| id           | bigint   |
| wallet\_id   | bigint   |
| type         | varchar  |
| date         | datetime |
| transfer\_id | varchar  |
| purpose      | varchar  |
| amount       | bigint   |

### Wallets

| Field    | Type    |
| -------- | ------- |
| id       | bigint  |
| user\_id | bigint  |
| balance  | decimal |

### VerificationCodes

| Field              | Type    |
| ------------------ | ------- |
| id                 | bigint  |
| otp                | varchar |
| user\_id           | bigint  |
| email              | varchar |
| mobile             | varchar |
| verification\_type | varchar |

### TradingHistories

| Field          | Type    |
| -------------- | ------- |
| id             | bigint  |
| selling\_price | double  |
| buying\_price  | double  |
| coin\_id       | varchar |
| user\_id       | bigint  |

### PaymentOrders

| Field           | Type    |
| --------------- | ------- |
| id              | bigint  |
| amount          | bigint  |
| status          | varchar |
| payment\_method | varchar |
| user\_id        | bigint  |

### PaymentDetails

| Field                 | Type    |
| --------------------- | ------- |
| id                    | bigint  |
| account\_number       | varchar |
| account\_holder\_name | varchar |
| ifsc                  | varchar |
| bank\_name            | varchar |
| user\_id              | bigint  |

### Orders

| Field           | Type     |
| --------------- | -------- |
| id              | bigint   |
| user\_id        | bigint   |
| order\_type     | varchar  |
| price           | decimal  |
| timestamp       | datetime |
| status          | varchar  |
| order\_item\_id | bigint   |

### OrderItems

| Field       | Type    |
| ----------- | ------- |
| id          | bigint  |
| quantity    | double  |
| coin\_id    | varchar |
| buy\_price  | double  |
| sell\_price | double  |
| order\_id   | bigint  |

### Notifications

| Field          | Type    |
| -------------- | ------- |
| id             | bigint  |
| from\_user\_id | bigint  |
| to\_user\_id   | bigint  |
| amount         | bigint  |
| message        | varchar |

### MarketChartData

| Field     | Type     |
| --------- | -------- |
| id        | bigint   |
| timestamp | datetime |
| price     | double   |

### ForgotPasswordTokens

| Field              | Type    |
| ------------------ | ------- |
| id                 | varchar |
| user\_id           | bigint  |
| otp                | varchar |
| verification\_type | varchar |
| send\_to           | varchar |



