<div align="center">
  
# 🚀 ExamPro: Smart Online Examination System
  
*A modern, responsive, and secure examination platform built with Spring Boot and a premium glassmorphic UI.*

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.2">
  <img src="https://img.shields.io/badge/Thymeleaf-green?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/Bootstrap%205-purple?style=for-the-badge&logo=bootstrap&logoColor=white" alt="Bootstrap 5">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
</p>

[Features](#-key-features) • [Tech Stack](#-tech-stack) • [Installation](#-getting-started) • [Deployment](#-deployment) • [Database](#-database-access)

</div>

---

## 🎯 Overview
**ExamPro** provides a secure, intuitive, and beautifully designed SaaS-style environment. It empowers **Educators and Admins** to create and manage examinations, while giving **Students** a seamless test-taking experience with instant performance analytics. 

---

## ✨ Key Features & UI Updates

Our platform features a fully modernized **Glassmorphism Dark-Mode UI** ensuring a premium user experience across all devices.

### 🌟 Global UI/UX
- **SaaS-Style Landing Page:** Animated hero section, feature cards, scrolling statistics, and a sleek dark gradient design.
- **Modern Authentication:** Glassmorphic Login & Register cards with ambient glow, icon inputs, and fade-in animations.
- **Advanced Dashboards:** Interactive Chart.js analytics for students and admins, wrapped in a frosted-glass aesthetic.
- **Responsive Navigation:** Sticky, blurred navbar with customized user profile dropdowns.

### 👨‍💻 Admin Capabilities
- **Command Center:** Centralized dashboard tracking total students, exams, questions, and submission analytics.
- **Exam Management (CRUD):** Create, edit, and delete comprehensive exams with custom duration and descriptions.
- **Question Bank:** Easily add and manage multiple-choice questions for each assessment.
- **Role Management:** Super-admins can add/remove secondary admins to distribute platform management.
- **Student Oversight:** View registered students, reset passwords, or suspend accounts (safely cascades results deletion).
- **Results Tracking:** Access all student submissions, scores, and historical performance metrics.

### 🧑‍🎓 Student Experience
- **Secure Onboarding:** Email/Password based authentication with profile picture uploads.
- **Personalized Hub:** Visualize past performance, calculate average scores, and discover newly available exams.
- **Live Exam Interface:** Distraction-free interactive UI with a live countdown timer and paginated questions. Auto-submits when time expires.
- **Instant Grading:** Exam results are calculated and delivered immediately upon submission.
- **Detailed Reviews:** Students can review completed exams to compare correct answers against their selected options.

---

## 🛠️ Tech Stack

| Component | Technology |
|---|---|
| **Backend Framework** | Spring Boot 3.2 (Java 17) |
| **Security** | Spring Security 6 |
| **Frontend Templates** | Thymeleaf, HTML5 |
| **Styling & UI** | Vanilla CSS, Bootstrap 5.3, Bootstrap Icons |
| **Data Visualization** | Chart.js |
| **Database** | H2 Database (File-based, highly portable) |
| **ORM** | Hibernate / Spring Data JPA |
| **Containerization** | Docker |

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher 
- **Maven** (optional, repo includes Maven wrapper)
- **Docker** (optional, for containerized run)

### 1. Clone the Repository
```bash
git clone https://github.com/abhijeetkumar51/Online_Exam_System.git
cd Online_Exam_System
```

### 2. Run Locally (Spring Boot)
You can run the application directly using the included Maven wrapper.

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```
**Mac/Linux:**
```bash
./mvnw spring-boot:run
```
The server will start on: **[http://localhost:8080](http://localhost:8080)**

---

## 🐳 Docker Support

You can easily run the application using Docker, bypassing the need for a local Java installation.

1. **Build the image**:
   ```bash
   docker build -t online-exam-system .
   ```
2. **Run the container**:
   ```bash
   docker run -p 8080:8080 --name exampro online-exam-system
   ```

---

## ☁️ Deployment (Cloud)

This project is fully prepared for modern cloud deployments (e.g., Render, Railway, Heroku).
- It natively reads the `$PORT` environment variable via `server.port=${PORT:8080}`.
- Simply connect your GitHub repository to a service like **Render** as a Web Service, choose the Docker runtime environment, and deploy in one click!

---

## 🗄️ Database Access

The application uses an embedded H2 file-based database for ultimate portability.
1. Navigate to: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
2. Connect using the JDBC URL: `jdbc:h2:file:./data/examdb`
*(Default credentials are configured in `application.properties`)*

---

## 🛡️ Default Authentication

On the **very first run**, a default Super Admin is generated if no users exist. 
Check your terminal output for the generated admin credentials, or review your `DataInitializer.java` configuration to authenticate immediately.

---

## 📜 License
Copyright &copy; 2026 Abhijeet Kumar. All rights reserved. 

No part of this project may be modified, copied, or redistributed without explicit permission from the author.
