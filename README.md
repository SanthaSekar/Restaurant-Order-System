# Restaurant Order & Menu Simulation System

## Overview

A Java-based OOP project that simulates a real-world restaurant system with multiple order types and billing strategies.

## Features

* Dine-In, Takeaway, Delivery orders
* Polymorphic billing system (Strategy pattern)
* Encapsulation, Inheritance, Abstraction
* CSV data generation for Power BI analysis

## Tech Stack

* Java (OOP)
* File Handling (TXT, CSV)
* Power BI (Analytics)

## Project Structure

* model/ → Menu items (Appetizer, MainCourse, Beverage)
* order/ → Order types (DineIn, Delivery, Takeaway)
* billing/ → Billing strategies (Normal, Discount, HappyHour)
* util/ → File handling & menu manager
* main/ → Entry point (RestaurantApp)

## How to Run

1. Compile:
   javac -d . model/*.java order/*.java billing/*.java util/*.java main/*.java

2. Run:
   java main.RestaurantApp

## Output

* orders.txt → formatted bill records
* orders.csv → structured data for Power BI

## Screenshots

### Power BI Dashboard
![Dashboard](screenshots/Power Bi Dashboard.png)

### CSV Data Output
![CSV](screenshots/Excel data.png)

### Console Output
![Console](screenshots/console-output.png)

## Author
Santhakumari S
