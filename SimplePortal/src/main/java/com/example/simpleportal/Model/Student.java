package com.example.simpleportal.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "students")
public class Student {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String address;

    @Column(nullable = false)
    private String password;

    private String faculty;
    private float  currentGpa;
    private long   courseCount;
    @Column(nullable = false)
    private String role = "STUDENT";
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleSlot> scheduleSlots = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityLog> activityLogs = new ArrayList<>();

    public Student() {}

    public Student(long id, String name, String email, String phone, String address,
                   String password, String faculty, float currentGpa, long courseCount, String role) {
        this.id          = id;
        this.name        = name;
        this.email       = email;
        this.phone       = phone;
        this.address     = address;
        this.password    = password;
        this.faculty     = faculty;
        this.currentGpa  = currentGpa;
        this.courseCount = courseCount;
        this.role        = role == null ? "STUDENT" : role;
    }

    public boolean isAdmin()   { return "ADMIN".equalsIgnoreCase(role); }
    public boolean isStudent() { return "STUDENT".equalsIgnoreCase(role); }

    public long getId()               { return id; }
    public void setId(long id)        { this.id = id; }

    public String getName()           { return name; }
    public void setName(String n)     { this.name = n; }

    public String getEmail()          { return email; }
    public void setEmail(String e)    { this.email = e; }

    public String getPhone()          { return phone; }
    public void setPhone(String p)    { this.phone = p; }

    public String getAddress()        { return address; }
    public void setAddress(String a)  { this.address = a; }

    public String getPassword()       { return password; }
    public void setPassword(String p) { this.password = p; }

    public String getFaculty()        { return faculty; }
    public void setFaculty(String f)  { this.faculty = f; }

    public float getCurrentGpa()      { return currentGpa; }
    public void setCurrentGpa(float g){ this.currentGpa = g; }

    public long getCourseCount()      { return courseCount; }
    public void setCourseCount(long c){ this.courseCount = c; }

    public String getRole()           { return role; }
    public void setRole(String r)     { this.role = r; }

    public List<Enrollment>   getEnrollments()   { return enrollments; }
    public List<ScheduleSlot> getScheduleSlots() { return scheduleSlots; }
    public List<Order>        getOrders()        { return orders; }
    public List<ActivityLog>  getActivityLogs()  { return activityLogs; }}