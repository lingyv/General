package org.lingyv.validator.web.controller;

import org.lingyv.validator.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ValidatorController {

    @RequestMapping("/register")
    public String toLoginPage(User user) {
        return "/jsp/register.jsp";
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doLogin(@Valid User user, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError error : errorList) {
                System.out.println(error.getField() + "*" + error.getDefaultMessage());
                map.put("ERR_" + error.getField(), error.getDefaultMessage());
            }
            return "/jsp/register.jsp";
        }

        return "/jsp/registersuccess.jsp";
    }

}
