package com.trionesdev.phecda.backend.core.domains.notification.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.backend.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.backend.core.domains.notification.dao.entity.Contact;
import com.trionesdev.phecda.backend.core.domains.notification.dao.entity.ContactGroup;
import com.trionesdev.phecda.backend.core.domains.notification.manager.impl.ContactManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactService {
    private final ContactManager contactManager;

    //region contact
    public void createContact(Contact contact) {
        contactManager.createContact(contact);
    }

    public void deleteContactById(String id) {
        contactManager.deleteContactById(id);
    }

    public void updateContactById(Contact contact) {
        contactManager.updateContactById(contact);
    }

    public Contact findContactById(String id) {
        return contactManager.findContactById(id);
    }

    public List<Contact> findContacts(ContactCriteria criteria) {
        return contactManager.findContacts(criteria);
    }

    public PageInfo<Contact> findContactsPage(ContactCriteria criteria) {
        return contactManager.findContactsPage(criteria);
    }


    //endregion

    //region contact group
    public void createContactGroup(ContactGroup contactGroup) {
        contactManager.createContactGroup(contactGroup);
    }

    public void deleteContactGroupById(String id) {
        contactManager.deleteContactGroupById(id);
    }

    public void updateContactGroupById(ContactGroup contactGroup) {
        contactManager.updateContactGroupById(contactGroup);
    }

    public ContactGroup findContactGroupById(String id) {
        return contactManager.findContactGroupById(id);
    }

    public List<ContactGroup> findContactGroups(ContactGroupCriteria criteria) {
        return contactManager.findContactGroups(criteria);
    }

    public PageInfo<ContactGroup> findContactGroupsPage(ContactGroupCriteria criteria) {
        return contactManager.findContactGroupsPage(criteria);
    }

    //endregion

}
