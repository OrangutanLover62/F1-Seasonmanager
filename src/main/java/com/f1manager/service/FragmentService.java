package com.f1manager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class FragmentService {
    private final TemplateEngine templateEngine;

    public String render(String templateSelector) {
        return render(templateSelector, null);
    }

    public String render(String templateSelector, Map<String, Object> variables) {
        Context context = new Context();
        if (variables != null && !variables.isEmpty()) {
            context.setVariables(variables);
        }
        return templateEngine.process("fragments", Set.of(templateSelector), context);
    }
}
