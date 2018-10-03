# Using the new decimal serializers in freestyle-rpc

This repository shows an example about how to upgrade the decimal serializers available from version `0.15.1`.

Its structured in 5 folders:

* `protocol-v1`, `server-v1` and `client-v1`: current system version at the beginning of the process.
* `protocol-v2` and `server-v2`: intermediate system version that will give support for clients in `v1` and the future clients.
* `protocol-v3`, `server-v3` and `client-v3`: final system version after the process.

The following schema shows the different stages:

![schema](img/migration-guide-decimals.png?raw=true)

## How to execute it

1 Publish the three protocols

```bash
$ cd protocol-v1
$ sbt publishLocal
$ cd ../protocol-v2
$ sbt publishLocal
$ cd ../protocol-v3
$ sbt publishLocal
```

2 Execute `server-v1` 

```bash
$ cd ../server-v1
$ sbt run
...
[info] Running freestyle.rpc.RPCServer
```

3 In a different terminal, execute `client-v1`

```bash
$ cd client-v1
$ sbt run
...
[info] Running freestyle.rpc.RPCClient
Calling server with request StockInfoRequest(stockId)
Response StockInfoResponse(stockId,30578.86,4.342)
```

4 Stop `server-v1` (Crtl-C) and execute `server-v2`

```bash
$ cd ../server-v2
$ sbt run
...
[info] Running freestyle.rpc.RPCServer
```

5 Execute `client-v1` again

```bash
$ cd client-v1
$ sbt run
...
[info] Running freestyle.rpc.RPCClient
Calling server with request StockInfoRequest(stockId)
Response StockInfoResponse(stockId,30578.86,4.342)
```

6 Execute `client-v3`

```bash
$ cd ../client-v3
$ sbt run
...
[info] Running freestyle.rpc.RPCClient
Calling server with request StockInfoRequest(stockId)
Response StockInfoResponse(stockId,30578.86,4.342)
```

7 Stop `server-v2` (Crtl-C) and execute `server-v3`

```bash
$ cd ../server-v3
$ sbt run
...
[info] Running freestyle.rpc.RPCServer
```

8 Execute `client-v3`

```bash
$ cd ../client-v3
$ sbt run
...
[info] Running freestyle.rpc.RPCClient
Calling server with request StockInfoRequest(stockId)
Response StockInfoResponse(stockId,30578.86,4.342)
```
