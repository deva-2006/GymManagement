

# Gym Management System (Java + JDBC + MySQL)

A small console project I built to practice Java, JDBC, and MySQL.
It handles basic gym operations like members, plans, attendance, and login.

## Features

Login

Admin login
Member login

### Admin

* Add / view / update / delete members
* Add / view plans
* Update member plan (admin manually updates plans; no automatic payment processing)
* Check attendance

### Member

* View profile
* View plan
* View attendance
* View remaining days (shown after login)
* Mark attendance

## Tech Used

* Java
* JDBC
* MySQL

## How to Run

1. Create the database by running `gymdb.sql` (included).
2. Put your DB URL, username, and password in `DBConnection.java`.

   * Example: `jdbc:mysql://localhost:3306/gymDB`, user `root`, password `Deva@2006`
3. Create the tables (run `gymdb.sql`) — it creates plans, members, attendance, users, and a sample admin.
4. Compile and run `Main.java`.

## Default Admin

```
username: admin
password: admin123
```

## What I Learned

* Connecting Java with MySQL
* Writing CRUD operations
* Using PreparedStatement
* Structuring code in multiple files (DAO pattern)
* Basic Git/GitHub workflow

## Future Ideas

* Password hashing
* Better error messages and validation
* Spring Boot version of this project
* Web UI (React) and deployment
* Optional ML features later (attendance prediction, plan recommendation)

