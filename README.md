## 시나리오
### 1. 재입고 알림 신청
- 유저는 상품별 재입고 알림을 신청한다
### 2. 상품 재입고 알림
- 상품의 재고상태와 재입고 회차를 변경한다
- 해당 상품의 재입고 신청한 유저를 조회한다
- 재입고 회차와는 상관없이 모두 알림을 보낸다
  - 재입고 회차는 몇 번 재입고를 했는지 확인한다고 생각했다
  - 재입고 알림을 받았더라도 그 때 안샀을 수도 있고, 마음에 들어서 여러 개 살 수 있다고 가정한다
- 재입고 발송 상태를 변경한다 (메모리)
### 3. 재입고 발송 중 품절
- 알림을 보내기 전에 재고여부를 확인한다
- 만약 재고가 있다고 그대로 알림을 보낸다
- 재고가 없다면 알림 히스토리에 저장하지 않는다
- 재입고 알림 히스토리에는 재고 부족으로 인한 알림 취소를 저장하고 마지막 발송 유저 아이디도 저장한다
### 4. 재입고 발송 중 오류
- 재입고 알림 히스토리에 오류로 인한 알림 취소를 저장하고 마지막 발송 유저 아이디도 저장한다
### 5. 재입고 알림 히스토리
- 상품별 재입고 알림 히스토리에 정상적으로 저장한다

