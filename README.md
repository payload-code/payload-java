<p align="center">
  <a href="https://payload.com">
    <img src="https://payload.com/images/horizontal-logo.svg" alt="Payload Logo"/>
  </a>
</p>

<p align="center">
  <strong>Powerfully simple payments</strong>
</p>

![Payload Documentation](https://img.shields.io/badge/Payload-Documentation-blue?style=plastic&logo=https%3A%2F%2Fpayload.com%2Fimages%2Fhorizontal-logo.svg&logoColor=%230000FF&logoSize=auto&link=https%3A%2F%2Fdocs.payload.com%2F)
![Payload Support](https://img.shields.io/badge/Payload-Support-blue?style=plastic&link=mailto:contact@payload.com)
![Java CI](https://github.com/payload-code/payload-java/actions/workflows/test-workflow.yml/badge.svg)

# Payload Java Library

A Java library for integrating [Payload](https://payload.co).

## Installation

### Maven Dependency

```xml
<dependency>
  <groupId>co.payload</groupId>
  <artifactId>payload</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Manual Installation

### 1) Download

Download the [latest](https://github.com/payload-code/payload-java/archive/master.zip)
version from GitHub.

### 2) Include in Project

Include the **Payload** folder in your Android Studio or Eclipse project.

## Get Started

Once you've included the Payload Java library in your project,
include the `co.payload.pl` namespace to get started.

All Payload objects and methods are accessible using the `pl` static class.

### API Authentication

To authenticate with the Payload API, you'll need a live or test API key. API
keys are accessible from within the Payload dashboard.

```java
import co.payload.Session;

Session sess = Session("secret_key_3bW9JMZtPVDOfFNzwRdfE");
```

### Creating an Object

Interfacing with the Payload API is done primarily through Payload Objects. Below is an example of
creating a customer using the `pl.Customer` object.

```java
// Create a Customer
pl.Customer customer = sess.create(new pl.Customer(){{
    set("email", "matt.perez@example.com");
    set("full_name", "Matt Perez");
}});
```

```java
// Create a Payment
pl.Payment payment = sess.create(new pl.Payment(){{
    set("amount", 100.0);
    set("payment_method", new pl.Card(){{
        set("card_number", "4242 4242 4242 4242");
    }});
}});
```

### Accessing Object Attributes

Object attributes are accessible through `getStr`, `getInt`, and `getFloat`.

```java
System.out.print(customer.getStr("email"));

```

### Updating an Object

Updating an object is a simple call to the `update` object method.

```java
// Updating a customer's email
customer.update(pl.attr("email", "matt.perez@newwork.com"));
```

### Get an Object by ID

Grab an existing object by its ID using the `get` method.

```java
// Get a customer by id
pl.Customer customer = sess.select(pl.Customer.class).get("cust_id");
```

### Selecting Objects

Objects can be selected using any of their attributes.

```java
// Select a customer by email
List<pl.Customer> customers = sess.select(pl.Customer.class).filter_by(
    "email", "matt.perez@example.com"
);
```

Use the `pl.attr` attribute helper
interface to write powerful queries with a little extra syntax sugar.

```java
List<pl.Payment> payments = sess.select(pl.Payment.class).filter_by(
    pl.attr("amount").gt(100),
    pl.attr("amount").lt(200),
    pl.attr("description").contains("Test"),
    pl.attr("created_at").gt(LocalDate.of(2019, 02, 20))
).all();
```

### Testing the Payload Java Library

Tests are contained within the `src/tests` directory. To run tests use the following command in terminal:

```bash
API_KEY=your_test_secret_key mvn test
```

## Documentation

To get further information on Payload's Java library and API capabilities,
visit the unabridged [Payload Documentation](https://docs.payload.co/?java).
