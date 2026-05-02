소비자 (User)

1. 상품 조회

- Request

    HTTP Method : GET

    URI : /products

    Input(Query Parameter) : name

- Response

   Output(JSON) :

   {

   "id": 1,

   "name": "사과",

   "price": 1000,

   "stockQuantity": 10

   }
   

2. 상품 구매
- Request

   HTTP Method : POST
   
   URL : /orders
   
   Input(JSON) :
   
   {

   "items": [

   {

   "productId": 1,

   "quantity": 2

   },

   {

   "productId": 2,

   "quantity": 1

   }

   ]

   } 
- Response

   Output (JSON) :

   {
      "totalPrice" : 4000,

    "purchasedItems":[
      
   {
   "name" : "사과",

   "quantity" : 2,

   "itemTotalPrice" : 2000
   },

   {"name" : "배",

   "quantity" : 1,

   "itemTotalPrice" : 2000
   }
   ]
   }

관리자 (Admin)

1. 상품 등록
- Request

   HTTP Method : POST
   
   URL : /products

   Input(JSON) :

   { "name" : "귤", 
   
   "price": 1000,

   "stockQuantity" : 100
   }

- Response

   Output(JSON) :

   성공 :

   { "id" : 3,
   
   "name" : "귤",
   
   "price" : 1000,
   
   "stockQuantity" : 100}
   
   실패 :

   {
   
   "message" : "동일한 이름의 상품 존재"   
   }

      
2. 재고 추가 
- Request
  
   HTTP Method : PATCH 
   
   URL : /products/{id}

   Input(JSON) : 
   
   {
   "addQuantity" : 50
   }
 - Response  
   
   Output(JSON) :
   
   {
   
   "name": "사과",
   
   "stockQuantity" : 60
   }
   
3. 상품 삭제
- Request
   
   HTTP Method : DELETE

   URL : /products

   Input(JSON) : 
   
   {
      "productIds" : [1,2] }
- Response
  
   Output(JSON) :
   
   {
   
   "remainingProducts": [
   
   {"name": "귤",
      "stockQuantity": 80
      },
   
   {
      "name": "수박",
      "stockQuantity": 20
      }
      ]
}