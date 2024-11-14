package com.trionesdev.phecda.foundation.core.domains.notification.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.entity.Contact;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.entity.ContactGroup;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.impl.ContactDAO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.impl.ContactGroupDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactManager {
    private final ContactDAO contactDAO;
    private final ContactGroupDAO contactGroupDAO;

    //region contact
    public void createContact(Contact contact) {
        contactDAO.save(contact);
    }

    @Transactional
    public void deleteContactById(String id) {
        contactDAO.removeById(id);
        contactGroupDAO.removeGroupContact(id);
    }

    public void updateContactById(Contact contact) {
        contactDAO.updateById(contact);
    }

    public Contact findContactById(String id) {
        return contactDAO.getById(id);
    }

    public List<Contact> findContacts(ContactCriteria criteria) {
        return contactDAO.selectList(criteria);
    }

    public PageInfo<Contact> findContactsPage(ContactCriteria criteria) {
        return contactDAO.selectPage(criteria);
    }

    //endregion


    //region contact group
    public void createContactGroup(ContactGroup contactGroup) {
        contactGroupDAO.save(contactGroup);
    }

    public void deleteContactGroupById(String id) {
        contactGroupDAO.removeById(id);
    }

    public void updateContactGroupById(ContactGroup contactGroup) {
        contactGroupDAO.updateById(contactGroup);
    }

    public ContactGroup findContactGroupById(String id) {
        return contactGroupDAO.getById(id);
    }

    public List<ContactGroup> findContactGroups(ContactGroupCriteria criteria) {
        return contactGroupDAO.selectList(criteria);
    }

    public PageInfo<ContactGroup> findContactGroupsPage(ContactGroupCriteria criteria) {
        return contactGroupDAO.selectPage(criteria);
    }

    //endregion

}
