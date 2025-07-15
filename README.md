# spring-Batch
Spring Batch Implementation where we can process millions of records in seconds

# ⚡ Spring Batch High-Performance CSV Importer

A high-performance, multi-threaded **Spring Batch** application built to efficiently process and import **millions of records** from a CSV file into a relational database in **seconds**.

🚀 In our benchmark, we successfully processed **1,000 student records in just 153 milliseconds** using **asynchronous multi-threaded chunk processing** with **JDBC batch writing**.

---

## 📌 Key Features

- ✅ Spring Batch powered ETL pipeline
- ✅ Reads CSV file using `FlatFileItemReader`
- ✅ Processes each record using `ItemProcessor`
- ✅ Inserts records into DB using **JdbcBatchItemWriter** for blazing-fast performance
- ✅ Asynchronous processing with `SimpleAsyncTaskExecutor`
- ✅ Chunk-oriented processing (100 records per chunk)
- ✅ Scalable to millions of records

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Batch
- Spring Data JPA (optional)
- JDBC Template (used for fast inserts)
- MySQL (or any JDBC-compatible database)
- Lombok

---

## 📁 Project Structure

```bash
├── config
│   └── StudentBatchConfig.java
├── controller
│   └── StudentBatchController.java
├── entity
│   └── Student.java
├── repository
│   └── StudentRepository.java
├── service
│   └── StudentBatchService.java
├── processor
│   └── StudentItemProcessor.java
├── resources
│   └── students_5000_records.csv
