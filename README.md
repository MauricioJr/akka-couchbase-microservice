# Akka HTTP with Couchbase NoSQL

High performance HTTP/no-blocking/distributed service

## Testing

Execute tests using `test` command:

```
$ sbt
> test
```

API call and result example

## List all

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

## insert new item

Post data
```bash
curl -H "Content-Type: application/json" -X POST -d '{"description":"incredible itian motorbike","price":400000.50, "size": 1000}' http://localhost:9000/shipment/new
```

Checking first list element
```bash
curl http://localhost:9000/shipment/0/details
```

Returns:
```json
{
  "jsonClass":"SimpleShipment",
  "description":"incredible italian motorbike",
  "price":400000.5,
  "size":1000
}
```