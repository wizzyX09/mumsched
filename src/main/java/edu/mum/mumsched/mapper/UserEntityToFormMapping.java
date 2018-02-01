package edu.mum.mumsched.mapper;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.model.UserForm;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToFormMapping extends ConverterConfigurerSupport<User, UserForm> {

    @Override
    protected Converter<User, UserForm> converter() {
        return new AbstractConverter<User, UserForm>() {
            protected UserForm convert(User user) {
                UserForm userForm = new UserForm();
                userForm.setId(user.getId());
                userForm.setName(user.getName());
                userForm.setLastName(user.getLastName());
                userForm.setEmail(user.getEmail());
                userForm.setPassword(user.getPassword());
                userForm.setRoles(user.getRoles());

                return userForm;
            }
        };
    }
}