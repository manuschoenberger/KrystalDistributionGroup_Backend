# Krystal Distribution Group

- 'fakeDelivery.http' to create fake delivery events and create warehouses. Start with this.
- 'requests.http' to create delivery appointments, weigh trucks, and check license plates, etc...

## cURL Statements

### Request 0a: Fake Delivery Event (Gypsum)
```bash
curl -X POST "http://localhost:15672/api/exchanges/%2F/warehouse_events/publish" \
  -H "Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=" \
  -H "Content-Type: application/json" \
  -d '{
        "properties": {},
        "routing_key": "payload.submitted",
        "payload": "{\"sellerUUID\":{\"uuid\":\"ef01c728-ce36-46b5-a110-84f53fdd9668\"},\"rawMaterialData\":\"GYPSUM\",\"amount\":10000}",
        "payload_encoding": "string"
      }'
```

### Request 0b: Fake Delivery Event (Iron Ore)
```bash
curl -X POST "http://localhost:15672/api/exchanges/%2F/warehouse_events/publish" \
  -H "Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=" \
  -H "Content-Type: application/json" \
  -d '{
        "properties": {},
        "routing_key": "payload.submitted",
        "payload": "{\"sellerUUID\":{\"uuid\":\"ef01c728-ce36-46b5-a110-84f53fdd9668\"},\"rawMaterialData\":\"IRON_ORE\",\"amount\":10000}",
        "payload_encoding": "string"
      }'
```

### Request 0c: Fake Delivery Event (Cement)
```bash
curl -X POST "http://localhost:15672/api/exchanges/%2F/warehouse_events/publish" \
  -H "Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=" \
  -H "Content-Type: application/json" \
  -d '{
        "properties": {},
        "routing_key": "payload.submitted",
        "payload": "{\"sellerUUID\":{\"uuid\":\"fe01c728-ce36-46b5-a110-84f53fdd9668\"},\"rawMaterialData\":\"CEMENT\",\"amount\":300000}",
        "payload_encoding": "string"
      }'
```

### Request 0d: Fake Delivery Event (Petcoke)
```bash
curl -X POST "http://localhost:15672/api/exchanges/%2F/warehouse_events/publish" \
  -H "Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=" \
  -H "Content-Type: application/json" \
  -d '{
        "properties": {},
        "routing_key": "payload.submitted",
        "payload": "{\"sellerUUID\":{\"uuid\":\"fe01c728-ce36-46b5-a110-84f53fdd9668\"},\"rawMaterialData\":\"PETCOKE\",\"amount\":450000}",
        "payload_encoding": "string"
      }'
```

### Request 0e: Fake Delivery Event (Slag)
```bash
curl -X POST "http://localhost:15672/api/exchanges/%2F/warehouse_events/publish" \
  -H "Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=" \
  -H "Content-Type: application/json" \
  -d '{
        "properties": {},
        "routing_key": "payload.submitted",
        "payload": "{\"sellerUUID\":{\"uuid\":\"ee01c728-ce36-46b5-a110-84f53fdd9668\"},\"rawMaterialData\":\"SLAG\",\"amount\":10000}",
        "payload_encoding": "string"
      }'
```

### Auth.: Get access token with user (seller)
```bash
curl -X POST http://localhost:8180/realms/krystal-distribution-group/protocol/openid-connect/token \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "client_id=landside" \
--data-urlencode "client_secret=1Si6Me3twRUZM1TEidxSJnSNWUk6hncZ" \
--data-urlencode "username=seller" \
--data-urlencode "password=password" \
--data-urlencode "grant_type=password" \
--data-urlencode "scope=openid"
```

### Request 1: Make Delivery Appointment
```bash
curl -X POST http://localhost:8090/api/delivery-appointments \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "sellerUUID": "ef01c728-ce36-46b5-a110-84f53fdd9668",
  "licensePlate": "1-ABC-123",
  "payload": "GYPSUM",
  "arrivalWindowStart": "2024-10-30T13:00:00"
}'
```

### Auth.: Get access token with user (truckdriver)
```bash
curl -X POST http://localhost:8180/realms/krystal-distribution-group/protocol/openid-connect/token \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "client_id=landside" \
--data-urlencode "client_secret=1Si6Me3twRUZM1TEidxSJnSNWUk6hncZ" \
--data-urlencode "username=truckdriver" \
--data-urlencode "password=password" \
--data-urlencode "grant_type=password" \
--data-urlencode "scope=openid"
```

### Request 2: Check License Plate
```bash
curl -X GET http://localhost:8090/api/gate/1-ABC-123 \
-H "Authorization: Bearer {{access_token}}"
```

### Request 3: Weigh Truck on Arrival
```bash
curl -X POST http://localhost:8090/api/weighing-bridge \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "licensePlate": "1-ABC-123",
  "positionStatus": "weighing_bridge_entry",
  "weight": 25000.0
}'
```

### Request 4a: Make Delivery [Deprecated]
```bash
curl -X POST http://localhost:8090/api/delivery \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "sellerUUID": "ef01c728-ce36-46b5-a110-84f53fdd9668",
  "material": "GYPSUM",
  "amount": "200000"
}'
```

### Request 4b: Dock Conveyor Belt
```bash
curl -X POST http://localhost:8090/api/conveyor-belt \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "sellerUUID": "ef01c728-ce36-46b5-a110-84f53fdd9668",
  "payloadData": "Gypsum",
  "licensePlate": "1-ABC-123"
}'
```

### Request 5: Get Trucks on Site
```bash
curl -X GET http://localhost:8090/api/site/trucks \
-H "Authorization: Bearer {{access_token}}"
```

### Request 6: Weigh Truck on Departure
```bash
curl -X POST http://localhost:8090/api/weighing-bridge \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "licensePlate": "1-ABC-123",
  "positionStatus": "weighing_bridge_exit",
  "weight": 20000.0
}'
```

### Request 7: Get Delivery Appointments
```bash
curl -X GET http://localhost:8090/api/delivery-appointment \
-H "Authorization: Bearer {{access_token}}"
```

### Auth.: Get access token with user (buyer)
```bash
curl -X POST http://localhost:8180/realms/krystal-distribution-group/protocol/openid-connect/token \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "client_id=warehouse" \
--data-urlencode "client_secret=t1Ax7pB57LqpM8dLS5y3w0gyew9A2BEx" \
--data-urlencode "username=buyer" \
--data-urlencode "password=password" \
--data-urlencode "grant_type=password" \
--data-urlencode "scope=openid"
```

### Request 8: Make Purchase Order
```bash
curl -X POST http://localhost:8090/api/purchase-order \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "poNumber": "PO-12345",
  "referenceUUID": "111e4567-e89b-12d3-a456-426614174000",
  "customerParty": {
    "partyUUID": "d4f6e8b9-45a1-4b99-93de-348f1d6f7410",
    "name": "Customer Corp",
    "address": "123 Customer St, Customer City"
  },
  "sellerParty": {
    "partyUUID": "ef01c728-ce36-46b5-a110-84f53fdd9668",
    "name": "Seller Corp",
    "address": "456 Seller Ave, Seller City"
  },
  "vesselNumber": "Vessel-789",
  "orderLines": [
    {
      "lineNumber": 1,
      "materialType": "GYPSUM",
      "description": "Gypsum",
      "quantity": 7000,
      "uom": "tons"
    },
    {
      "lineNumber": 2,
      "materialType": "IRON_ORE",
      "description": "Iron Ore",
      "quantity": 8500,
      "uom": "tons"
    }
  ]
}'
```

### Auth.: Get access token with user (captain)
```bash
curl -X POST http://localhost:8180/realms/krystal-distribution-group/protocol/openid-connect/token \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "client_id=landside" \
--data-urlencode "client_secret=1Si6Me3twRUZM1TEidxSJnSNWUk6hncZ" \
--data-urlencode "username=captain" \
--data-urlencode "password=password" \
--data-urlencode "grant_type=password" \
--data-urlencode "scope=openid"
```

### Request 9: Carry out Shipment
```bash
curl -X POST http://localhost:8090/api/shipment \
-H "Content-Type: application/json" \
-H "Authorization: Bearer {{access_token}}" \
-d '{
  "sellerUUID": "ef01c728-ce36-46b5-a110-84f53fdd9668",
  "referenceUUID": "111e4567-e89b-12d3-a456-426614174000",
  "vesselNumber": "Vessel-789"
}'
```
