package ms.phecda.backend.rest.backend.domains.notification.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.notification.dao.criteria.ContactCriteria;
import ms.phecda.backend.core.domains.notification.dao.criteria.ContactGroupCriteria;
import ms.phecda.backend.core.domains.notification.dao.entity.Contact;
import ms.phecda.backend.core.domains.notification.dao.entity.ContactGroup;
import ms.phecda.backend.core.domains.notification.service.impl.ContactService;
import ms.phecda.backend.rest.backend.domains.notification.controller.query.ContactGroupQuery;
import ms.phecda.backend.rest.backend.domains.notification.controller.query.ContactQuery;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactCreateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactGroupCreateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactGroupUpdateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactUpdateRO;
import ms.phecda.backend.rest.backend.domains.notification.support.NotificationBeRestConvert;
import ms.phecda.backend.rest.backend.domains.notification.support.NotificationConstants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "联系人")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = NotificationConstants.NOTIFICATION_URI)
public class ContactController {
    private final NotificationBeRestConvert notificationBeRestConvert;
    private final ContactService contactService;

    //region contact
    @Operation(summary = "新建联系人")
    @PostMapping(value = "contacts")
    public void createContact(@RequestBody ContactCreateRO args) {
        Contact contact = notificationBeRestConvert.from(args);
        contactService.createContact(contact);
    }

    @Operation(summary = "根据ID删除联系人")
    @DeleteMapping(value = "contacts/{id}")
    public void removeContactById(@PathVariable String id) {
        contactService.deleteContactById(id);
    }

    @Operation(summary = "根据ID修改联系人")
    @PutMapping(value = "contacts/{id}")
    public void updateContactById(@PathVariable String id, @RequestBody ContactUpdateRO args) {
        Contact contact = notificationBeRestConvert.from(args);
        contact.setId(id);
        contactService.updateContactById(contact);
    }

    @Operation(summary = "根据ID获取联系人")
    @GetMapping(value = "contacts/{id}")
    public Contact findContactById(@PathVariable String id) {
        return contactService.findContactById(id);
    }

    @Operation(summary = "查询联系人列表")
    @GetMapping(value = "contacts/list")
    public List<Contact> findContacts(ContactQuery query) {
        ContactCriteria criteria = notificationBeRestConvert.from(query);
        return contactService.findContacts(criteria);
    }

    @Operation(summary = "分页查询联系人")
    @GetMapping(value = "contacts/page")
    public PageInfo<Contact> findContactsPage(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNum") Integer pageNum,
            ContactQuery query) {
        ContactCriteria criteria = notificationBeRestConvert.from(query);
        criteria.setPageSize(pageSize);
        criteria.setPageNum(pageNum);
        return contactService.findContactsPage(criteria);
    }

    //endregion

    //region contact group
    @Operation(summary = "新建联系人组")
    @PostMapping(value = "contact-groups")
    public void createContactGroup(@RequestBody ContactGroupCreateRO args) {
        ContactGroup contactGroup = notificationBeRestConvert.from(args);
        contactService.createContactGroup(contactGroup);
    }

    @Operation(summary = "根据ID删除联系人组")
    @DeleteMapping(value = "contact-groups/{id}")
    public void removeContactGroupById(@PathVariable String id) {
        contactService.deleteContactGroupById(id);
    }

    @Operation(summary = "根据ID获取联系人组")
    @GetMapping(value = "contact-groups/{id}")
    public ContactGroup findContactGroupById(@PathVariable String id) {
        return contactService.findContactGroupById(id);
    }

    @Operation(summary = "根据ID修改联系人组")
    @PutMapping(value = "contact-groups/{id}")
    public void updateContactGroupById(@PathVariable String id, @RequestBody ContactGroupUpdateRO args) {
        ContactGroup contactGroup = notificationBeRestConvert.from(args);
        contactGroup.setId(id);
        contactService.updateContactGroupById(contactGroup);
    }

    @Operation(summary = "查询联系人组列表")
    @GetMapping(value = "contact-groups/list")
    public List<ContactGroup> findContactGroups(ContactGroupQuery query) {
        return contactService.findContactGroups(notificationBeRestConvert.from(query));
    }

    @Operation(summary = "分页查询联系人组")
    @GetMapping(value = "contact-groups/page")
    public PageInfo<ContactGroup> findContactGroupsPage(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNum") Integer pageNum,
            ContactGroupQuery query) {
        ContactGroupCriteria criteria = notificationBeRestConvert.from(query);
        criteria.setPageSize(pageSize);
        criteria.setPageNum(pageNum);
        return contactService.findContactGroupsPage(criteria);
    }


    //endregion

}
