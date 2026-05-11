# e-Hotels Management System

**Author:** Mohammadjavad Aghaeipour Kalyani  

## 📖 Project Overview
e-Hotels is a full-stack database management application developed as part of the Software Engineering curriculum at the University of Ottawa. The platform facilitates real-time room booking and administration across five major hotel chains operating in over 14 North American locations. 

Built to handle complex relational data, this project demonstrates advanced database architecture, robust backend integration, and a seamless user experience for both customers and hotel staff.

## 💻 Tech Stack
* **Database:** PostgreSQL / MySQL
* **Backend:** Java / PHP
* **Server:** Apache Tomcat
* **Frontend:** HTML / CSS

## ✨ Core Features

### User & Employee Portals
* **Customer Interface:** Allows guests to search for room availability and book rooms for specific dates.
* **Advanced Search Capabilities:** Dynamic filtering by start/end dates, capacity, area, hotel chain, star category, total hotel rooms, and price limits.
* **Employee Management:** Enables hotel staff to manage check-ins, automatically transforming reservations into active rentings.
* **Walk-in Processing:** Staff can process direct rentings for customers who arrive without prior booking.
* **Payment Integration:** Secure tracking and insertion of customer payments for room rentings.

### Database Architecture
* **Relational Schema:** Fully normalized database schema secured with primary keys, referential integrity, and custom domain constraints.
* **Automated Triggers:** Custom SQL triggers autonomously manage data integrity during insert, delete, and update operations.
* **Performance Optimization:** Strategic indexing on high-traffic relations to significantly accelerate complex query performance.
* **Dynamic Data Views:** SQL views provide instant aggregation of available rooms per region and total capacity per hotel.
* **Historical Archiving:** System safely maintains a permanent, immutable archive of past bookings and rentings, even if parent records (rooms/customers) are deleted.
* **Strict Hierarchies:** Enforces absolute data dependencies—rooms must belong to valid hotels, and hotels to valid chains.

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 11+
* PostgreSQL 14+ or MySQL
* Apache Tomcat 9+
* Eclipse IDE (or equivalent)

### Installation
1. Clone this repository to your local machine:
   ```bash
   git clone [https://github.com/YOUR_USERNAME/eHotel-Management-System.git](https://github.com/mjak1385/eHotel-System.git)
