package com.trionesdev.phecda.foundation.core.domains.notification.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactGroupPO;
import com.trionesdev.phecda.foundation.core.domains.notification.manager.impl.ContactManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactService {
    private final ContactManager contactManager;

    //region contact
    public void createContact(ContactPO contact) {
        contactManager.createContact(contact);
    }

    public void deleteContactById(String id) {
        contactManager.deleteContactById(id);
    }

    public void updateContactById(ContactPO contact) {
        contactManager.updateContactById(contact);
    }

    public ContactPO findContactById(String id) {
        return contactManager.findContactById(id);
    }

    public List<ContactPO> findContacts(ContactCriteria criteria) {
        return contactManager.findContacts(criteria);
    }

    public PageInfo<ContactPO> findContactsPage(ContactCriteria criteria) {
        return contactManager.findContactsPage(criteria);
    }


    //endregion

    //region contact group
    public void createContactGroup(ContactGroupPO contactGroup) {
        contactManager.createContactGroup(contactGroup);
    }

    public void deleteContactGroupById(String id) {
        contactManager.deleteContactGroupById(id);
    }

    public void updateContactGroupById(ContactGroupPO contactGroup) {
        contactManager.updateContactGroupById(contactGroup);
    }

    public ContactGroupPO findContactGroupById(String id) {
        return contactManager.findContactGroupById(id);
    }

    public List<ContactGroupPO> findContactGroups(ContactGroupCriteria criteria) {
        return contactManager.findContactGroups(criteria);
    }

    public PageInfo<ContactGroupPO> findContactGroupsPage(ContactGroupCriteria criteria) {
        return contactManager.findContactGroupsPage(criteria);
    }

    //endregion

}
