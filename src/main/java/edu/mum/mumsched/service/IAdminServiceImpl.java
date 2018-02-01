package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Admin;
import edu.mum.mumsched.model.UserForm;
import org.springframework.stereotype.Service;

@Service("adminService")
public class IAdminServiceImpl extends ConcreteServiceImpl<Admin, UserForm> {
}
