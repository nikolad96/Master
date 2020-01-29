package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.FormFieldsDto;
import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.Recenzent;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.utils.Utils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {
        System.out.println("usao u metodu registration/get");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Proces_registracije_korisnika");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        //System.out.println("size:" + taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size());

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();


        return new FormFieldsDto(task.getId(), properties, processInstance.getId());
    }

    @PostMapping(path = "/postRegistrationData/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postRegistrationData(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("usao u metodu registration/postRegistrationData");

        HashMap<String, Object> map = Utils.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);

        Korisnik korisnik;
        String postojiMail = "true";
        String postojiUsername = "true";

        for (FormSubmissionDto formField : dto) {
            if(formField.getFieldId().equals("username")) {
                korisnik = korisnikService.findOneByUsername(formField.getFieldValue());
                try{
                    korisnik.getId();
                }catch(NullPointerException e){
                    postojiUsername = "false";
                    System.out.println("Ne postoji korisnik sa ovim username-om.");
                }
            }
            if(formField.getFieldId().equals("email")) {
//                korisnik = korisnikService.findOneByEmail(formField.getFieldValue());
//                try{
//                    korisnik.getId();
//                }catch(NullPointerException e){
//                    postojiMail = "false";
//                    System.out.println("Ne postoji korisnik sa ovim email-om.");
//                }
                postojiMail = "false";
            }
        }

        if(postojiMail.equals("true") || postojiUsername.equals("true")){
            List<FormSubmissionDto> result = new ArrayList<FormSubmissionDto>();
            result.add(new FormSubmissionDto("postojiMail", postojiMail));
            result.add(new FormSubmissionDto("postojiUsername", postojiUsername));

            return new ResponseEntity<List<FormSubmissionDto>>(result, HttpStatus.OK);
        }else{

            formService.submitTaskForm(taskId, map);

            List<FormSubmissionDto> result = new ArrayList<FormSubmissionDto>();
            result.add(new FormSubmissionDto("ok", "true"));
            return new ResponseEntity<List<FormSubmissionDto>>(result, HttpStatus.OK);
        }


        //System.out.println("size:" + taskService.createTaskQuery().processInstanceId(processInstanceId).list().size());

//        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
//        TaskFormData taskFormData = formService.getTaskFormData(nextTask.getId());
//        List<FormField> properties = taskFormData.getFormFields();
//
//        return new FormFieldsDto(nextTask.getId(), properties, processInstanceId);
    }

    @PostMapping(path = "/activateUser/{id_process}", consumes = "application/json")
    public @ResponseBody ResponseEntity activateUser(@RequestBody List<FormSubmissionDto> dto, @PathVariable String id_process) {
        System.out.println("usao u metodu registration/activateUser");

        System.out.println("field value: " + dto.get(0).getFieldValue());
        Integer id_user = Integer.parseInt(dto.get(0).getFieldValue());

        System.out.println("id_user: " + id_user + "; id_process: " + id_process);
        runtimeService.setVariable(id_process, "id_user", id_user);

        String hashedValue = (String) runtimeService.getVariable(id_process, "hashVrednost");
        boolean isValid = BCrypt.checkpw(korisnikService.findOneById(id_user).getUsername(), hashedValue);

        if(!isValid){
            System.out.println("Nije validan hash prilikom verifikacije mejla");
        }else{

            System.out.println("Validan je hash prilikom verifikacije mejla");
            runtimeService.setVariable(id_process, "potvrdjeno", true);
        }


        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(path = "/getTasks", produces = "application/json")
    @PreAuthorize("hasAuthority('POTVRDA_RECENZENTA')")
    public @ResponseBody ResponseEntity<List<FormFieldsDto>> getTasks() {

        System.out.println("usao u metodu registration/getTasks");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("admin").list();

        List<FormFieldsDto> formFieldsDtos = new ArrayList<>();
        for(Task task: tasks){
            if(task.getName().equals("Potvrda recenzenta")) {
                FormFieldsDto formFieldsDto = new FormFieldsDto();
                formFieldsDto.setTaskId(task.getId());

                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormField> properties = taskFormData.getFormFields();

                formFieldsDto.setFormFields(properties);
                formFieldsDtos.add(formFieldsDto);
            }
        }

        return new ResponseEntity(formFieldsDtos,  HttpStatus.OK);

    }

    @PostMapping(path = "/recenzentPotvrda", consumes = "text/plain")
    public @ResponseBody ResponseEntity recenzentPotvrda(@RequestBody String taskId) {
        System.out.println("usao u metodu registration/recenzentPotvrda");

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "potvrda", "da");
        taskService.complete(taskId);


        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/recenzentOdbijanje", consumes = "text/plain")
    public @ResponseBody ResponseEntity recenzentOdbijanje(@RequestBody String taskId) {
        System.out.println("usao u metodu registration/recenzentOdbijanje");

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        String username = (String) runtimeService.getVariable(processInstanceId, "username");
        Korisnik pronadjen = korisnikService.findOneByUsername(username);
        pronadjen.setRecenzent(Recenzent.ODBIJEN);
        korisnikService.save(pronadjen);

        runtimeService.setVariable(processInstanceId, "potvrda", "ne");
        taskService.complete(taskId);


        return new ResponseEntity<>(HttpStatus.OK);

    }

}
