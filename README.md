# Code check Application

### Tech stack
Java - 11<br>
MongoDB<br>
Spring - latest<br>
Springboot - 2.4.3<br>

### Guides
####To build and run this application in docker just run
```
./buildAndRun.sh
```


####To deploy it in kubernetes just run 
```
kubectl apply -f deploy-pipeline/deployment.yaml
```


### Endpoints
/health - healthCheck <br>
/customers - adding customer <br>
/customers/{customerId}/accounts  - adding accounts <br>
/customers/{customerId}/{accountNo}/balance  - getting balance <br>


###Sample payloads
####Add Customer
```
sample url : http://localhost:8080/customers
{
    "dob": "2021-01-21",
    "name" : "Radha",
    "address": "34, Potley avenue"
}
```
#### Add Customer Account
```
sample url : http://localhost:8080/customers/8aabe23d-8ee7-4378-991c-584251075634/accounts
{
    "startDate": "2021-02-2190",
    "balance": 20001,
    "transactions": "Additional transaction",
    "productType": "saving"
}
```