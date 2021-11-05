# Tribal coding challenge

This project is made as Tribal coding challenge

## How to run

Make sure you have installed and configured Java 11

Download [JAR file](https://github.com/Aliaksei-Karaliou/tribal-demo/releases/latest/download/tribal-demo.jar) and run

```batch
java -jar tribal-demo-app.jar
```

The default url is `http://localhost:8080 (POST http://localhost:8080/credit)`

## API

<table>
  <thead>
    <tr>
      <th>API</th>
      <th>Request type</th>
      <th>Description</th>
      <th>Example body</th>
      <th>Example result</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <code>/credit</code>
      </td>
      <td>POST</td>
      <td>Determine if credit can be approved</td>
      <td>
        <pre>
					<code class="lang-json">
{
	"foundingType":"SME",
	"cashBalance":435.30,
	"monthlyRevenue":4235.45,
	"requestedCreditLine":100,
	"requestedDate":"2021-07-19T16:32:59.860Z"
}
					</code>
				</pre>
		</td>
		      <td>
        <pre>
					<code class="lang-json">
{
  "isApproved": true,
  "requestedCreditLine": 100.0
}
					</code>
				</pre>
		</td>
    </tr>
  </tbody>
</table>

## Project structure

The project is written in Kotlin with framework Spring Boot

### Functional requirements

`com.aliakseikaraliou.github.tribaldemo.credit` package contains business models, line services, controllers related to
functional requirements

Application receive credit line model, determine its type, and determine maximum credit line sum at different
strategies:

* SME - `SMERecommendedCreditLineStrategy`
* Startup - `StartupRecommendedCreditLineStrategy`

If application is approved, application returns corresponding result with response code 200

```json
{
  "isApproved": true,
  "requestedCreditLine": 100.0
}
```

Otherwise application returns corresponding result with response code 403

```json
{
  "isApproved": false,
  "requestedCreditLine": 100.0
}
```

### Non-functional requirements

`com.aliakseikaraliou.github.tribaldemo.ratelimit` package contains filters, servers, etc. related to rate limiting

As it is demo application and there is no need to implement authentication and authorization and connect external
database decided to used IP address as person identifier and [caffeine cache](https://github.com/ben-manes/caffeine) as
temporal [Bucket4j](https://github.com/vladimir-bukhtoyarov/bucket4j) storage

For approved and declined requests there are 2 different caches, and both of them has default after read expiration
time *10 minutes*. This time is enough for passing rate limit checks

*Approved requests* use `ApprovedRateLimitStrategy` and *Denied requests* use `DeniedRateLimitStrategy`

## Configured properties

| Name                                         | Type    | Description                                                                             | Default value      |
| -------------------------------------------- | ------- | --------------------------------------------------------------------------------------- | ------------------ |
| app.credit.sme.monthly_ratio                 | Double  | Monthly ratio for SME credit line                                                       | 0.2                |
| app.credit.startup.cash_balance_ratio        | Double  | Cash balance ratio for startup credit line                                              | 0.3333333333333333 |
| app.credit.startup.monthly_ratio             | Double  | Monthly ratio for startup credit line                                                   | 0.2                |
| app.rate_limit.approved.capacity             | Integer | Rate limit request capacity for approved requests                                       | 2                  |
| app.rate_limit.approved.duration             | Integer | Rate limit time duration (in seconds) for approved requests                             | 120                |
| app.rate_limit.approved.cache_expiration     | Integer | Cache expiration (in seconds) for approved requests                                     | 600                |
| app.rate_limit.denied.capacity               | Integer | Rate limit request capacity for denied requests                                         | 1                  |
| app.rate_limit.denied.duration               | Integer | Rate limit time duration (in seconds) for denied requests                               | 30                 |
| app.rate_limit.denied.cache_expiration       | Integer | Cache expiration (in seconds) for denied requests                                       | 600                |
| app.rate_limit.denied.request_to_sales_count | Integer | Necessary request count for starting receiving 'A sales agent will contact you' message | 3                  |
