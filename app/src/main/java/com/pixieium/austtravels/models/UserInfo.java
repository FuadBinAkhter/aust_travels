package com.pixieium.austtravels.models;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.database.Exclude;
import com.pixieium.austtravels.databinding.ActivitySignupBinding;

public class UserInfo {
    private String email;
    private String name;
    private String semester;
    private String department;
    private String universityId;
    private String userImage;
    @Exclude
    private String password;

    public UserInfo() {
    }

    public UserInfo(String email, String name, String semester, String department, String universityId, String userImage) {
        this.email = email;
        this.name = name;
        this.semester = semester;
        this.department = department;
        this.universityId = universityId;
        this.userImage = userImage;
    }

    public UserInfo(String email, String password, String name, String semester, String department, String universityId, String userImage) {
        this.email = email;
        this.name = name;
        this.semester = semester;
        this.department = department;
        this.universityId = universityId;
        this.userImage = userImage;
        this.password = password;
    }

    public UserInfo(String email, String userImage) {
        this.email = email;
        this.userImage = userImage;

    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }


    private Boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public Boolean validateInput(ActivitySignupBinding binding) {
        boolean isValid = true;
        if (!isValidEmail(this.email)) {
            binding.eduMail.setError("Please enter a valid email");
            isValid = false;
        } else {
            if (!email.split("@")[1].equals("aust.edu")) {
                //println(email.split('@')[1])
                binding.eduMail.setError("You must enter your institutional mail");
                isValid = false;
            }
        }

        if (TextUtils.isEmpty(email)) {
            binding.eduMail.setError("Field is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(universityId)) {
            binding.universityId.setError("Field is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            binding.password.setError("Field is required");
            isValid = false;
        }
        if (TextUtils.isEmpty(name)) {
            binding.name.setError("Field is required");
            isValid = false;
        }
        if (name.length() > 20) {
            binding.name.setError("Please enter a name of 20 characters");
            isValid = false;
        }
        if (TextUtils.isEmpty(semester)) {
            binding.semester.setError("Field is required");
            isValid = false;
        }
        if (TextUtils.isEmpty(department)) {
            binding.department.setError("Field is required");
            isValid = false;
        }

        return isValid;
    }

}