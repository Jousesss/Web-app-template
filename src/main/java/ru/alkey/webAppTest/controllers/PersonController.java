package ru.alkey.webAppTest.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alkey.webAppTest.dao.PersonDAO;
import ru.alkey.webAppTest.models.Person;

@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllPeople(Model model) {
        model.addAttribute("people",personDAO.getPeople());
        return "people/showAllPeople";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person",personDAO.getPerson(id));
        return "people/showPerson";
    }

    @GetMapping("/new")
    public String getPersonCreateForm(@ModelAttribute("person") Person person) {
        return "people/createPersonForm";
    }

    @GetMapping("/{id}/edit")
    public String getPersonUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person",personDAO.getPerson(id));
        return "people/editPersonForm";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/createPersonForm";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") Long id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/editPersonForm";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        personDAO.removePerson(id);
        return "redirect:/people";
    }
}
