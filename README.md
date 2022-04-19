# Training_session_5

 **REST documentation:**
 
* `GET` `/api/latest` 

with parameters `base`(base currency code), `target`(target currency code)

example request: `/api/latest?base=EUR&target=PLN`

example response: `{"rate":4.618717}`

* `GET` `/api/historical`

with parameters `base`(base currency code), `target`(target currency code),
`date`(date in format yyyy-mm-dd)

example request: `/api/historical?base=EUR&target=PLN&date=2019-04-10`

example response: `{"rate":4.2835}`

* `GET` `/api/gold`

with parameter `date`(date in format yyyy-mm-dd)

example request: `/api/gold?date=2022-04-15`

example response: `{"price":268.77}`