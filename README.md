# Akka HTTP with Couchbase NoSQL

High performance HTTP/no-blocking/distributed service

## Testing

Execute tests using `test` command:

```
$ sbt
> test
```

API call and result example

```bash
curl http://localhost:9000/list/all
```

```json
[
  {
    "jsonClass":"SimpleShipment",
    "description":"Soya",
    "price":100.0,
    "size":100
  },
  {
    "jsonClass":"SimpleShipment",
    "description":"Soya",
    "price":400.0,
    "size":200
  },
  {
    "jsonClass":"ComplexShipment",
    "description":"Premmmium Soya",
    "fromCountry":"Brazil",
    "connectionCountry":"India",
    "destinationCountry":"China",
    "price":500.012,
    "size":500
  },
  {
    "jsonClass":"ComplexShipment",
    "description":"Camaro SS",
    "fromCountry":"EUA",
    "connectionCountry":"Panama",
    "destinationCountry":"Brazil",
    "price":1500.012,
    "size":2000
  }
]
```