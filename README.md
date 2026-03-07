# 🚀 Online Exam System – Spring Boot

<p align="left">
<img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java&logoColor=white" alt="Java 17">
<img src="https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.2">
<img src="https://img.shields.io/badge/Thymeleaf-green?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf">
<img src="https://img.shields.io/badge/Bootstrap%205-purple?style=for-the-badge&logo=bootstrap&logoColor=white" alt="Bootstrap 5">
<img src="https://img.shields.io/badge/H2%20Database-lightgrey?style=for-the-badge" alt="H2 Database">
</p>

A comprehensive, responsive, and modern **Online Examination System** built with **Spring Boot, Spring Security, Thymeleaf**, and **Bootstrap 5**.

This platform provides a secure, intuitive, and beautifully designed SaaS-style environment for **Admins** to manage exams and **Students** to take tests and track their performance.

---

## ✨ Key Features & UI Updates

This project features a fully modernized **Glassmorphism Dark-Mode UI** across all key pages:

### 🌟 Global UI/UX
- **SaaS-Style Landing Page:** Animated hero section, feature cards, scrolling statistics, and a sleek dark gradient design.
- **Modern Authentication:** Glassmorphic Login & Register cards with ambient glow, icon inputs, and fade-in animations.
- **Redesigned Dashboards:** Advanced Chart.js analytics for both students and admins, wrapped in a frosted-glass aesthetic.
- **Responsive Navigation:** Sticky, blurred navbar with customized user profile dropdowns.

### 👨‍💻 Admin Features
- **Secure Dashboard:** Overview of total students, exams, questions, and submission analytics.
- **Exam Management (CRUD):** Create, edit, and delete exams (title, duration, description).
- **Question Bank:** Add and manage multiple-choice questions for each exam.
- **Admin Management:** Super-admins can add/remove secondary admins to help manage the platform.
- **Student Management:** View registered students, reset passwords, or delete accounts securely (with cascaded results deletion).
- **Results Oversight:** View all student submissions and performance metrics.

### 🧑‍🎓 Student Features
- **Secure Registration & Login:** Email/Password based authentication with profile picture uploads.
- **Personalized Dashboard:** Visualize past performance, average scores, and available exams.
- **Live Exam Interface:** Interactive UI with a live countdown timer and paginated questions.
- **Instant Grading:** Exam results are calculated immediately upon submission.
- **Detailed Reviews:** Students can review their past exams to see correct answers vs. their selected options.
- **Profile Management:** Update personal details, mobile number, and profile picture securely.

---

## 🛠️ Tech Stack

| Component | Technology |
|---|---|
| **Backend Framework** | Spring Boot 3.2 (Java 17) |
| **Security** | Spring Security 6 |
| **Frontend Templates** | Thymeleaf |
| **Styling & UI** | Vanilla CSS, Bootstrap 5.3, Bootstrap Icons |
| **Charts & Analytics** | Chart.js |
| **Database** | H2 (File-based, highly portable) |
| **ORM** | Hibernate / Spring Data JPA |
| **File Storage** | Local File System (Images mapped to `/uploads/`) |

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher installed.
- **Maven** (optional, repo includes Maven wrapper).

### 1. Clone the Repository
```bash
git clone https://github.com/abhijeetkumar51/Online_Exam_System.git
cd Online_Exam_System
```

### 2. Run the Application
You can run the application directly using the included Maven wrapper:

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```
**Mac/Linux:**
```bash
./mvnw spring-boot:run
```

The server will start on: **[http://localhost:8000](http://localhost:8000)**

---

## 🗄️ Database Access

The application uses an embedded H2 file-based database. To view the database tables:
1. Navigate to: **[http://localhost:8000/h2-console](http://localhost:8000/h2-console)**
2. Connect using the JDBC URL: `jdbc:h2:file:./data/examdb`
*(Default credentials are intentionally blank/configured in `application.properties`)*

---

## 🛡️ Default Authentication

On the **very first run**, a default Super Admin is generated if no users exist. 
Check your terminal output for the generated admin credentials, or review your `application.properties` / `DataInitializer.java` configuration.

---

## 📜 License
This project is open-source and available under the **MIT License**.
