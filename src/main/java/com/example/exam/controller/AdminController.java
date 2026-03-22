package com.example.exam.controller;

import com.example.exam.model.Exam;
import com.example.exam.model.ExamResult;
import com.example.exam.model.Question;
import com.example.exam.model.User;
import com.example.exam.repository.ExamRepository;
import com.example.exam.repository.ExamResultRepository;
import com.example.exam.repository.QuestionRepository;
import com.example.exam.repository.UserRepository;
import com.example.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamResultRepository examResultRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        // 1. Get KPI Card Stats
        long totalStudents = userRepository.countByRole("ROLE_STUDENT");
        long totalExams = examRepository.count();
        long totalQuestions = questionRepository.count();
        long totalSubmissions = examResultRepository.count();

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalExams", totalExams);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("totalSubmissions", totalSubmissions);

        // 2. Get Data for Submissions Chart
        List<Exam> allExams = examRepository.findAll();
        List<String> chartLabels = allExams.stream()
                .map(Exam::getTitle)
                .collect(Collectors.toList());
        List<Integer> chartData = allExams.stream()
                .map(exam -> exam.getResults().size())
                .collect(Collectors.toList());

        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartData", chartData);

        List<ExamResult> recentResults = examResultRepository.findTop5ByOrderBySubmissionTimeDesc();
        model.addAttribute("recentResults", recentResults);

        return "admin/dashboard";
    }

    @GetMapping("/manage-exams")
    public String getManageExamsPage(Model model) {
        model.addAttribute("exams", examRepository.findAll());
        return "admin/manage_exams";
    }

    @GetMapping("/exam/add")
    public String showAddExamForm(Model model) {
        model.addAttribute("exam", new Exam());
        return "admin/add_exam";
    }

    @PostMapping("/exam/add")
    public String addExam(@ModelAttribute Exam exam) {
        examRepository.save(exam);
        return "redirect:/admin/manage-exams";
    }

    @GetMapping("/exam/{examId}/question/add")
    public String showAddQuestionForm(@PathVariable Long examId, Model model) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));
        Question question = new Question();
        question.setExam(exam);
        model.addAttribute("question", question);
        model.addAttribute("examId", examId);
        return "admin/add_question";
    }

    @PostMapping("/exam/question/add")
    public String addQuestion(@ModelAttribute Question question,
            @RequestParam Long examId,
            RedirectAttributes redirectAttributes) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));

        question.setExam(exam);
        questionRepository.save(question);

        redirectAttributes.addFlashAttribute("successMessage", "Question added successfully!");

        return "redirect:/admin/exam/" + examId + "/question/add";
    }

    @GetMapping("/results/{examId}")
    public String viewResults(@PathVariable Long examId, Model model) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));

        List<ExamResult> results = examResultRepository.findByExam(exam);

        model.addAttribute("exam", exam);
        model.addAttribute("results", results);

        return "admin/view_results";
    }

    @GetMapping("/exam/delete/{examId}")
    public String deleteExam(@PathVariable Long examId) {
        // CascadeType.ALL, deleting the exam will also delete
        // all associated questions and results.
        examRepository.deleteById(examId);
        return "redirect:/admin/manage-exams";
    }

    @GetMapping("/exam/edit/{examId}")
    public String showEditExamForm(@PathVariable Long examId, Model model) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));
        model.addAttribute("exam", exam);
        return "admin/edit_exam";
    }
@PostMapping("/exam/update/{examId}")
    public String updateExam(@PathVariable Long examId, @ModelAttribute Exam updatedExam) {
        Exam existingExam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));

        existingExam.setTitle(updatedExam.getTitle());
        existingExam.setDurationInMinutes(updatedExam.getDurationInMinutes());

        examRepository.save(existingExam);
        return "redirect:/admin/manage-exams";
    }

    @GetMapping("/exam/manage-questions/{examId}")
    public String showManageQuestionsPage(@PathVariable Long examId, Model model) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));
        model.addAttribute("exam", exam);
        model.addAttribute("questions", exam.getQuestions());
        return "admin/manage_questions";
    }

    @GetMapping("/question/edit/{questionId}")
    public String showEditQuestionForm(@PathVariable Long questionId, Model model) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + questionId));
        model.addAttribute("question", question);
        return "admin/edit_question.html";
    }

    @PostMapping("/question/update/{questionId}")
    public String updateQuestion(@PathVariable Long questionId, @ModelAttribute Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + questionId));

        existingQuestion.setText(updatedQuestion.getText());
        existingQuestion.setOption1(updatedQuestion.getOption1());
        existingQuestion.setOption2(updatedQuestion.getOption2());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption4(updatedQuestion.getOption4());
        existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        existingQuestion.setMarks(updatedQuestion.getMarks());

        questionRepository.save(existingQuestion);

        return "redirect:/admin/exam/manage-questions/" + existingQuestion.getExam().getId();
    }

    @GetMapping("/exam/{examId}/question/delete/{questionId}")
    public String deleteQuestion(@PathVariable Long examId,
            @PathVariable Long questionId,
            RedirectAttributes redirectAttributes) { // <-- Added RedirectAttributes
        try {
            questionRepository.deleteById(questionId);
            redirectAttributes.addFlashAttribute("successMessage", "Question deleted successfully.");

        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete this question. It has already been answered by one or more students and is part of their exam results.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An unexpected error occurred while trying to delete the question.");
        }

        return "redirect:/admin/exam/manage-questions/" + examId;
    }

    @GetMapping("/students")
    public String getManageStudentsPage(Model model) {
        // Find all users who have the "STUDENT" role
        List<User> students = userRepository.findByRole("ROLE_STUDENT");
        model.addAttribute("students", students);
        return "admin/manage_students";
    }

    @GetMapping("/student/delete/{id}")
    @Transactional
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // 1. Find the student
            Optional<User> studentOpt = userRepository.findById(id);
            if (studentOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid student Id:" + id);
            }
            User student = studentOpt.get();

            // 2. MANUALLY delete the "children" (ExamResults)
            examResultRepository.deleteByStudent(student);

            // 3. Force Hibernate to execute the pending DELETE statements NOW.
            examResultRepository.flush();

            // 4. NOW it is safe to delete the "parent" (User)
            userRepository.delete(student);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Student account and all associated results have been deleted.");

        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Could not delete student. A database integrity error occurred.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while trying to delete the student account: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    @PostMapping("/student/reset-password")
    public String resetStudentPassword(@RequestParam("userId") Long userId,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes) {

        if (newPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty.");
            return "redirect:/admin/students";
        }

        try {
            User student = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + userId));

            // Encode the new password before saving
            student.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(student);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Password for " + student.getUsername() + " has been reset.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while trying to reset the password.");
        }
        return "redirect:/admin/students";
    }

    // ==================== Admin/Teacher Management ====================

    @GetMapping("/manage-admins")
    public String getManageAdminsPage(Model model) {
        List<User> admins = userRepository.findByRole("ROLE_ADMIN");
        model.addAttribute("admins", admins);
        return "admin/manage_admins";
    }

    @GetMapping("/admin/add")
    public String showAddAdminForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null || (!"admin".equals(principal.getName()) && !"admin@exam.com".equals(principal.getName()))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only the main Administrator can add new admins/teachers.");
            return "redirect:/admin/manage-admins";
        }
        model.addAttribute("user", new User());
        return "admin/add_admin";
    }

    @PostMapping("/admin/add")
    public String addAdmin(@ModelAttribute("user") User user,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        if (principal == null || (!"admin".equals(principal.getName()) && !"admin@exam.com".equals(principal.getName()))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only the main Administrator can add new admins/teachers.");
            return "redirect:/admin/manage-admins";
        }

        // Check if username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An account already exists with the email: " + user.getUsername());
            return "redirect:/admin/admin/add";
        }

        userService.saveAdmin(user);
        redirectAttributes.addFlashAttribute("successMessage",
                "Admin account for '" + user.getUsername() + "' created successfully!");
        return "redirect:/admin/manage-admins";
    }

    @GetMapping("/admin/delete/{id}")
    @Transactional
    public String deleteAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> adminOpt = userRepository.findById(id);
            if (adminOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid admin Id:" + id);
            }

            User adminToDelete = adminOpt.get();

            // Prevent deleting the main Administrator account (both 'admin' and 'admin@exam.com')
            if ("admin@exam.com".equals(adminToDelete.getUsername()) || "admin".equals(adminToDelete.getUsername())) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "The main Administrator account cannot be deleted.");
                return "redirect:/admin/manage-admins";
            }

            // Prevent deleting yourself (the currently logged-in admin)
            org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder
                    .getContext().getAuthentication();
            if (auth != null && adminToDelete.getUsername().equals(auth.getName())) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "You cannot delete your own admin account.");
                return "redirect:/admin/manage-admins";
            }

            userRepository.delete(adminToDelete);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Admin account has been deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while trying to delete the admin account: " + e.getMessage());
        }
        return "redirect:/admin/manage-admins";
    }

    @PostMapping("/admin/reset-password")
    public String resetAdminPassword(@RequestParam("userId") Long userId,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes) {
        if (newPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty.");
            return "redirect:/admin/manage-admins";
        }

        try {
            User admin = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid admin Id:" + userId));
            admin.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(admin);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Password for " + admin.getUsername() + " has been reset.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while trying to reset the password.");
        }
        return "redirect:/admin/manage-admins";
    }
}
