# TransactionManager

Its a Java SPRING BOOT backend application for Transaction Manager APIs

Few Assumptions taken :
1. Transaction with same ID wont be triggered again via PUT request
2. In Memory Data structures used
3.  Junit used to test only controller class
4. Excpetion handling done for base cases

Data structure used  -   HashMap  parent   ,  HashMap listByIds , HashMap listByType
parent -  this stores each transaction and their parent ids
listByIds - this stores all the transactions mapped wrt their IDs as the KEY
listByType - this stores all related transactions in the map wrt TYPE as the KEY

Initially to set up created ArrayList<Transaction>  containing all the transactions


APIs and their Asymptotic Behaviour

1. create transactions -

This API  takes Constant time O(1) for creating a Transaction object , and then updating some in memory data structures
Example -



PUT   -    http://localhost:8080/transactionservice/transaction/10

with JSON request body  -

{
"amount":54.89,
"type":"food",
"parent_id":2

}

2. Get Transactions by type -


Now , whenever the request comes with certain TYPE ,
constant time to search all related transactions O(1)
then linear time to traverse all the transactions and returns their IDs O(N)
where N = no of transactions with same TYPE

example  -

GET  - localhost:8080/transactionservice/types/food



3. get SUM by ID

Now first we get the related transaction via HashMAp listByIds in O(1) time .
then we need to traverse parent map , one by one getting the parent
so in worst case we can end up with all the transactions being related which means traversing
full map O(N)

example -

GET   - localhost:8080/transactionservice/sum/10


Project Structure :

RestController  -  TransactionController  - having all the endpoints
Service  - TransactionService  -middleware for DAO and client layer
REPO - TransactionRepo -  DAO layer containing all the business logic
Model  -  TRansaction   -  bean
Exception - Central exception handling class for all the exceptions
Test -  Junit class for testing controller class


NOTE -  Many use cases can be added , this is just a basic API handler .



