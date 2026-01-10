Investment Banking Deal Pipeline Management System
📌 Project Overview

The Investment Banking Deal Pipeline Management System is a full-stack web application designed to simulate real-world investment banking workflows such as M&A, IPO, Equity, and Debt deals.
It enables secure deal tracking across lifecycle stages, role-based access control, audit trails, analytics, and administrative user management.

This project demonstrates enterprise-grade backend design, secure authentication, RBAC, workflow management, and a modern React frontend.

🎯 Business Problem

Investment banks manage multiple deals simultaneously across stages like:

Prospect

Under Evaluation

Term Sheet Submitted

Closed

Lost

Challenges addressed:

Secure access control

Deal lifecycle visibility

Auditability of deal changes

Admin-only financial data protection

Business analytics for decision making

🧠 Solution Highlights

JWT-based authentication

Role-Based Access Control (ADMIN / USER)

Kanban-style deal pipeline

Deal activity audit trail

Notes & collaboration

Analytics dashboard

Dockerized deployment

🛠 Tech Stack
Backend

Java 17

Spring Boot

Spring Security + JWT

MongoDB

Maven

Frontend

React (TypeScript)

Material UI (MUI)

Axios

React Router

DevOps

Docker

Docker Compose

👥 User Roles
🔑 ADMIN

Create / manage users

View & update deal values

View analytics (financial metrics)

Full access to all deals

👤 USER

View and manage assigned deals

Move deals across stages

Add notes

View deal activity (audit trail)

Cannot see sensitive deal values on cards

🔐 Authentication & Security

Passwords hashed using BCrypt

JWT tokens for stateless authentication

Secure API endpoints using Spring Security

Frontend route guards (PrivateRoute & RoleRoute)

Sensitive data protected at API + UI level

🔁 Deal Lifecycle Workflow

Create Deal

Move across stages using drag-and-drop

Add notes & edit details

Admin updates deal value

All actions recorded as Deal Activities

Final stage → CLOSED or LOST

📊 Analytics Module
Available for USER & ADMIN

Won deals count

Lost deals count

Deals by stage

ADMIN-Only

Total deal value

Average deal value

Analytics data is computed dynamically from MongoDB.

📂 Project Structure
Backend
InvestmentBAnking-dealpipeline/
├── controller
├── service
├── repository
├── model
├── dto
├── security
└── config

Frontend
Investment-Banking-Dealpipeline-Frontend/
├── api
├── components
├── layout
├── pages
├── routes
└── App.tsx

🚀 Running the Application
Prerequisites

Java 17

Node.js

MongoDB (running locally)

Docker (optional)

▶️ Run Backend
cd InvestmentBAnking-dealpipeline
mvn spring-boot:run


Backend runs on:

http://localhost:8080

▶️ Run Frontend
cd Investment-Banking-Dealpipeline-Frontend
npm install
npm run dev


Frontend runs on:

http://localhost:5173

🐳 Docker Support
Run Full Stack Using Docker Compose
docker-compose up --build


Services:

Backend → Spring Boot

Frontend → React

MongoDB → Local (outside container)

🧪 Testing & Validation

APIs tested using Postman

Role-based access verified

Invalid access blocked (403)

Edge cases handled

UI validations added