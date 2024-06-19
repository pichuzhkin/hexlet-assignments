package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping(path = "")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContactDTO newContact(@RequestBody ContactCreateDTO incomingDto) {
        var outDto = new ContactDTO();
        var entity = new Contact();
        entity.setFirstName(incomingDto.getFirstName());
        entity.setLastName(incomingDto.getLastName());
        entity.setPhone(incomingDto.getPhone());

        contactRepository.save(entity);

        outDto.setId(entity.getId());
        outDto.setPhone(entity.getPhone());
        outDto.setLastName(entity.getLastName());
        outDto.setFirstName(entity.getFirstName());
        outDto.setCreatedAt(entity.getCreatedAt());
        outDto.setUpdatedAt(entity.getUpdatedAt());

        return outDto;
    }
    // END
}
