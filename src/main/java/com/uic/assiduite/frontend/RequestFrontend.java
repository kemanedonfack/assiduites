package com.uic.assiduite.frontend;

import com.uic.assiduite.model.Request;
import com.uic.assiduite.service.RequestService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author engome
 */
@Controller
public class RequestFrontend {

    @Autowired
    private RequestService requestService;

    @GetMapping("/request")
    public String showResquest(Model model) {
        List<Request> requests = requestService.getAllRequest();
        model.addAttribute("request", requests);
        return "request";
    }

    @PostMapping("/request/save")
    public String saveRequest(HttpServletRequest request, HttpServletResponse response, @Valid @ModelAttribute("request") Request justif, BindingResult result, Model model) {
        requestService.createRequest(justif);
        return "redirect:/request?success";
    }
}
