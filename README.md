See https://stackoverflow.com/questions/76887311/duplicate-row-was-found-and-assert-was-specified

1. Start postgres

```
docker-compose up
```

2. Run test `com.demo.integration.TranslationsAPITest`

Throws
```
org.springframework.orm.jpa.JpaSystemException: Duplicate row was found and `ASSERT` was specified
```

