# Payload Java Library

A Java library for integrating [Payload](https://payload.co).

## Installation

### Maven Dependency

```xml
<dependency>
  <groupId>co.payload</groupId>
  <artifactId>payload</artifactId>
  <version>0.1.3</version>
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
import co.payload.pl;

pl.api_key = "secret_key_3bW9JMZtPVDOfFNzwRdfE";
```

### Creating an Object

Interfacing with the Payload API is done primarily through Payload Objects. Below is an example of
creating a customer using the `pl.Customer` object.

```java
// Create a Customer
pl.Customer customer = new pl.Customer(){{
    set("email", "matt.perez@example.com");
    set("full_name", "Matt Perez");
    create();
}};
```

```java
// Create a Payment
pl.Payment payment = new pl.Payment(){{
    set("amount", 100.0);
    set("payment_method, new pl.Card(){{
        set("card_number", "4242 4242 4242 4242");
    }});
    create();
}};
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

### Selecting Objects

Objects can be selected using any of their attributes.

```java
// Select a customer by email
pl.Customer customers = pl.Customer.filter_by(
    "email", "matt.perez@example.com"
);
```

Use the `pl.attr` attribute helper
interface to write powerful queries with a little extra syntax sugar.

```java
pl.Payment payments = pl.Payments.filter_by(
    pl.attr("amount").gt(100),
    pl.attr("amount").lt(200),
    pl.attr("description").contains("Test"),
    pl.attr("created_at").gt(LocalDate.of(2019, 02, 20))
).all();
```

## Documentation

To get further information on Payload's Java library and API capabilities,
visit the unabridged [Payload Documentation](https://docs.payload.co/?java).
