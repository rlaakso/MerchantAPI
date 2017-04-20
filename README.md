# MerchantAPI

REST API example for Mecrhants to provide offers for sale


Components:

MerchantAPIServer - Grizzly HTTP server to run the API

MerchantAPI - API for Merchant to add and manage offers

QueryAPI - API for other users to list and purchase offers

OfferDatastore - Dummy database to store offers in memory

Offer - Offer object

PurchaseLink - Object for payment page redirection


Merchant API

GET    /offers/{id}  -  query offers

POST   /offers       -  create new offer

PUT    /offers/{id}  -  update offer

DELETE /offers/{id}  -  delete offer


Query API

GET    /offers/{id}  -  query offer

GET    /offers       -  list offers

PUT    /offers/{id}  -  purchase offer


