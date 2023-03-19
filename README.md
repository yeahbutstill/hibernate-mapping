# hibernate-mapping

Sample hibernate java mapping

## Setup run container

```shell
docker run --rm \
--name orderservice \
-e POSTGRES_DB=orderservice \
-e POSTGRES_USER=orderuser \
-e POSTGRES_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v "$PWD/orderservice-data:/var/lib/postgresql/data" \
-p 5432:5432 \
postgres:15
```