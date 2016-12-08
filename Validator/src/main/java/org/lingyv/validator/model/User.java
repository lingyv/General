package org.lingyv.validator.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.lingyv.validator.annotation.Between;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class User implements Serializable {

    @NotEmpty(message = "{NotEmpty.user.userName}")
    private String userName;

    @Pattern(regexp = "[0-9a-zA-Z]{6,30}", message = "{Pattern.user.password}")
    private String password;

    @Length(min = 2, max = 100, message = "{Length.user.realName}")
    private String realName;

    @Email(message = "{Email.user.email}")
    private String email;

    @NotNull(message = "{NotNull.user.age}")
    @Between(min = 18, max = 45)
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
