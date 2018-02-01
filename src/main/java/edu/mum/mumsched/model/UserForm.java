package edu.mum.mumsched.model;

import org.apache.tomcat.util.buf.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserForm {

    private int id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String gender;
    private boolean active;
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        for (Role role : roles) {
            if(UserType.ADMIN.toString().equalsIgnoreCase(role.getRole())) {
                return true;
            }
        }
        return false;
    }

    public boolean isFaculty() {
        for (Role role : roles) {
            if(UserType.FACULTY.toString().equalsIgnoreCase(role.getRole())) {
                return true;
            }
        }
        return false;
    }

    public boolean isStudent() {
        for (Role role : roles) {
            if(UserType.STUDENT.toString().equalsIgnoreCase(role.getRole())) {
                return true;
            }
        }
        return false;
    }

    // to display the list of roles as a comma separated strings
    public String getRolesString() {
        List<String> listOfRoles = new ArrayList<>();
        for(Role role : roles) {
            listOfRoles.add(role.getRole());
        }

        return StringUtils.join(listOfRoles);
    }

    public User getEntity() {
        User user = new User();
        if (this.isFaculty()) {
            user = new Faculty();
        } else if (this.isStudent()) {
            user = new Student();
        } else if (this.isAdmin()) {
            user = new Admin();
        }

        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRoles(roles);
        user.setActive(true);

        Gender genderParam = Gender.MALE;
        if (Gender.FEMALE.toString().equalsIgnoreCase(gender)) {
            genderParam = Gender.FEMALE;
        }
        user.setGender(genderParam);

        return user;
    }
}
