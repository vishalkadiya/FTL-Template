package com.ftlTemplate.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ftlTemplate.model.Employee;
import com.ftlTemplate.repository.EmployeeRepository;
import com.ftlTemplate.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public ResponseEntity<String> findByName(String name) {
        Optional<Employee> employee = employeeRepository.findByFirstName(name);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        try {
            cfg.setDirectoryForTemplateLoading(new File("C:/Assignment/path"));
            Template template = cfg.getTemplate("demo.ftl");
            Map<String, Object> input = new HashMap<>();

            employee.ifPresent(e -> {
                        input.put("user", "employee-data");
                        input.put("firstName", e.getFirstName());
                        input.put("lastName", e.getLastName());
                        input.put("employeeNumber", e.getEmp_no());
                        input.put("logoUrlFromDatabase", "C:/Users/NTS-Vishal Kadiya/OneDrive/Pictures/img3.jpg");
                    }
            );
            System.out.println(input.keySet()+ "=" +input.values());
            StringWriter output = new StringWriter();
            template.process(input, output);
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom("vishalkadiya5989@gmail.com");
                mimeMessageHelper.setTo("vishal.kadiya@neutrinotechlabs.com");
                mimeMessageHelper.setText(output.toString(), true);
                mimeMessageHelper.setSubject("Template testing");
                mailSender.send(mimeMessage);
                System.out.println("Attachment mail sent successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

//	            return output.toString();
            return ResponseEntity.ok("Template has been sent in mail please check mail");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while processing in template");
        }
    }
}
