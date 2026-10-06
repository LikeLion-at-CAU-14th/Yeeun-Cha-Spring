# 주문 API 명세서

## 노션 데이터베이스 속성

노션에서 `/표` 또는 `/데이터베이스`를 만든 뒤 다음 속성을 추가한다.

| 속성 | 노션 타입 | 설명 |
|---|---|---|
| API 이름 | 제목 | API 기능 이름 |
| Method | 선택 | POST, GET, PUT, DELETE |
| URL | 텍스트 | 요청 주소 |
| 설명 | 텍스트 | API가 하는 일 |
| 성공 상태 | 선택 | 현재 구현 기준 200 OK |
| 인증 | 선택 | 현재 실습에서는 없음 |

## 데이터베이스에 입력할 목록

| API 이름 | Method | URL | 설명 | 성공 상태 | 인증 |
|---|---|---|---|---|---|
| 주문 생성 | POST | `/orders` | 배송정보와 상품을 포함한 주문 생성 | 200 OK | 없음 |
| 구매자별 주문 목록 조회 | GET | `/orders/buyer/{buyerId}` | 구매자의 삭제되지 않은 주문 목록 조회 | 200 OK | 없음 |
| 주문 상세 조회 | GET | `/orders/{orderId}` | 주문 ID로 주문 상세 조회 | 200 OK | 없음 |
| 주문 배송정보 수정 | PUT | `/orders/{orderId}` | 배송 준비 상태인 주문의 배송정보 수정 | 200 OK | 없음 |
| 주문 삭제 | DELETE | `/orders/{orderId}` | 배송 완료 주문을 Soft Delete 처리 | 200 OK | 없음 |

---

# 1. 주문 생성

## 기본 정보

| 구분 | 내용 |
|---|---|
| Method | `POST` |
| URL | `/orders` |
| 설명 | 구매자, 상품, 배송정보를 이용해 주문을 생성한다. |

## Request Body

| 필드 | 타입 | 필수 | 설명 |
|---|---|---|---|
| `buyerId` | Long | O | 구매자 ID |
| `recipient` | String | O | 수령인 |
| `phoneNumber` | String | O | 수령인 전화번호 |
| `roadAddress` | String | O | 도로명주소 |
| `detailAddress` | String | O | 상세주소 |
| `zipCode` | String | O | 우편번호 |
| `products` | Array | O | 주문 상품 목록 |
| `products[].productId` | Long | O | 상품 ID |
| `products[].quantity` | Integer | O | 주문 수량 |

```json
{
  "buyerId": 961,
  "recipient": "예은",
  "phoneNumber": "010-1234-5678",
  "roadAddress": "서울특별시 동작구 상도로 1",
  "detailAddress": "101동 202호",
  "zipCode": "06234",
  "products": [
    {
      "productId": 2,
      "quantity": 2
    }
  ]
}
```

## 성공 응답

```json
{
  "orderId": 1,
  "buyerId": 961,
  "deliverStatus": "PREPARATION",
  "shippingAddress": {
    "recipient": "예은",
    "phoneNumber": "010-1234-5678",
    "roadAddress": "서울특별시 동작구 상도로 1",
    "detailAddress": "101동 202호",
    "zipCode": "06234"
  },
  "products": [
    {
      "productId": 2,
      "productName": "멋사 티셔츠",
      "price": 15000,
      "quantity": 2
    }
  ]
}
```

## 오류 상황

- 존재하지 않는 `buyerId`이면 `구매자를 찾을 수 없습니다.` 예외가 발생한다.
- 존재하지 않는 `productId`이면 `상품을 찾을 수 없습니다.` 예외가 발생한다.

---

# 2. 구매자별 주문 목록 조회

## 기본 정보

| 구분 | 내용 |
|---|---|
| Method | `GET` |
| URL | `/orders/buyer/{buyerId}` |
| 설명 | 특정 구매자의 삭제되지 않은 주문 목록을 조회한다. |

## Path Variable

| 이름 | 타입 | 설명 |
|---|---|---|
| `buyerId` | Long | 조회할 구매자의 ID |

## 요청 예시

```http
GET /orders/buyer/961
```

GET 요청이므로 Request Body는 사용하지 않는다.

## 성공 응답

```json
[
  {
    "orderId": 1,
    "buyerId": 961,
    "deliverStatus": "PREPARATION",
    "shippingAddress": {
      "recipient": "예은",
      "phoneNumber": "010-1234-5678",
      "roadAddress": "서울특별시 동작구 상도로 1",
      "detailAddress": "101동 202호",
      "zipCode": "06234"
    },
    "products": [
      {
        "productId": 2,
        "productName": "멋사 티셔츠",
        "price": 15000,
        "quantity": 2
      }
    ]
  }
]
```

주문이 없으면 빈 배열을 반환한다.

```json
[]
```

---

# 3. 주문 상세 조회

## 기본 정보

| 구분 | 내용 |
|---|---|
| Method | `GET` |
| URL | `/orders/{orderId}` |
| 설명 | 주문 ID를 이용해 삭제되지 않은 주문 한 건을 조회한다. |

## Path Variable

| 이름 | 타입 | 설명 |
|---|---|---|
| `orderId` | Long | 조회할 주문 ID |

## 요청 예시

```http
GET /orders/1
```

## 성공 응답

```json
{
  "orderId": 1,
  "buyerId": 961,
  "deliverStatus": "PREPARATION",
  "shippingAddress": {
    "recipient": "예은",
    "phoneNumber": "010-1234-5678",
    "roadAddress": "서울특별시 동작구 상도로 1",
    "detailAddress": "101동 202호",
    "zipCode": "06234"
  },
  "products": [
    {
      "productId": 2,
      "productName": "멋사 티셔츠",
      "price": 15000,
      "quantity": 2
    }
  ]
}
```

## 오류 상황

- 주문이 없거나 Soft Delete된 주문이면 `주문을 찾을 수 없습니다.` 예외가 발생한다.

---

# 4. 주문 배송정보 수정

## 기본 정보

| 구분 | 내용 |
|---|---|
| Method | `PUT` |
| URL | `/orders/{orderId}` |
| 설명 | 배송 준비 상태인 주문의 배송정보를 수정한다. |

## Path Variable

| 이름 | 타입 | 설명 |
|---|---|---|
| `orderId` | Long | 수정할 주문 ID |

## Request Body

| 필드 | 타입 | 필수 | 설명 |
|---|---|---|---|
| `recipient` | String | O | 변경할 수령인 |
| `phoneNumber` | String | O | 변경할 전화번호 |
| `roadAddress` | String | O | 변경할 도로명주소 |
| `detailAddress` | String | O | 변경할 상세주소 |
| `zipCode` | String | O | 변경할 우편번호 |

```json
{
  "recipient": "김예은",
  "phoneNumber": "010-9999-8888",
  "roadAddress": "서울특별시 동작구 상도로 100",
  "detailAddress": "202동 303호",
  "zipCode": "06999"
}
```

## 성공 조건

- 주문의 배송 상태가 `PREPARATION`이어야 한다.
- 성공하면 변경된 주문 정보를 반환한다.

## 오류 상황

- 주문을 찾지 못하면 `주문을 찾을 수 없습니다.` 예외가 발생한다.
- 배송 상태가 `PREPARATION`이 아니면 `배송 준비 중인 주문만 배송정보를 수정할 수 있습니다.` 예외가 발생한다.

---

# 5. 주문 삭제

## 기본 정보

| 구분 | 내용 |
|---|---|
| Method | `DELETE` |
| URL | `/orders/{orderId}` |
| 설명 | 배송 완료 상태의 주문을 Soft Delete 처리한다. |

## Path Variable

| 이름 | 타입 | 설명 |
|---|---|---|
| `orderId` | Long | 삭제할 주문 ID |

## 요청 예시

```http
DELETE /orders/1
```

DELETE 요청의 Request Body는 사용하지 않는다.

## 성공 조건

- 주문의 배송 상태가 `COMPLETED`여야 한다.

## 성공 응답

```text
주문이 성공적으로 삭제되었습니다.
```

실제 DB 행은 삭제하지 않고 다음 값만 변경한다.

```text
deleted = true
```

## 오류 상황

- 주문을 찾지 못하거나 이미 Soft Delete된 주문이면 `주문을 찾을 수 없습니다.` 예외가 발생한다.
- 배송 상태가 `COMPLETED`가 아니면 `배송 완료된 주문만 삭제할 수 있습니다.` 예외가 발생한다.

---

## 참고

- 현재 실습에서는 인증 기능을 적용하지 않아 요청에 포함된 ID를 사용한다.
- 별도의 전역 예외 처리기를 구현하지 않아 `IllegalArgumentException`이 현재 `500 Internal Server Error`로 반환될 수 있다.
- 추후 `@RestControllerAdvice` 등을 적용하면 상황에 맞게 `400`, `404`, `409` 등의 상태 코드로 나눌 수 있다.
