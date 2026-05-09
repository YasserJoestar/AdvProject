package com.example.simpleportal.Service;

import com.example.simpleportal.Model.ActivityLog;
import com.example.simpleportal.Model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository repo;
    private final StudentRepository     studentRepo;

    public ActivityLogService(ActivityLogRepository repo, StudentRepository studentRepo) {
        this.repo        = repo;
        this.studentRepo = studentRepo;
    }

    /** Log using a resolved Student object. */
    public void log(Student student, String action, String detail) {
        if (student == null) return;
        repo.save(new ActivityLog(student, action, detail));
    }

    /** Log using a raw student-id string from a cookie (falls back gracefully). */
    public void log(String cookieStudentId, String action, String detail) {
        long sid = parseLong(cookieStudentId, -1L);
        if (sid < 0) return;
        studentRepo.findById(sid).ifPresent(s -> log(s, action, detail));
    }

    /** All logs for one student (newest first). */
    public List<ActivityLog> getForStudent(Long studentId) {
        return repo.findByStudent_IdOrderByTimestampDesc(studentId);
    }

    /** All logs across every user (admin view, newest first). */
    public List<ActivityLog> getAll() {
        return repo.findAllByOrderByTimestampDesc();
    }

    private long parseLong(String raw, long fallback) {
        if (raw == null || raw.isBlank()) return fallback;
        try { return Long.parseLong(raw.trim()); } catch (NumberFormatException e) { return fallback; }
    }
}
