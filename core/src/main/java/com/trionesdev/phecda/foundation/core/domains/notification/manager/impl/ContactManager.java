package com.trionesdev.phecda.foundation.core.domains.notification.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactGroupPO;
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
    public void createContact(ContactPO contact) {
        contactDAO.save(contact);
    }

    @Transactional
    public void deleteContactById(String id) {
        contactDAO.removeById(id);
        contactGroupDAO.removeGroupContact(id);
    }

    public void updateContactById(ContactPO contact) {
        contactDAO.updateById(contact);
    }

    public ContactPO findContactById(String id) {
        return contactDAO.getById(id);
    }

    public List<ContactPO> findContacts(ContactCriteria criteria) {
        return contactDAO.selectList(criteria);
    }

    public PageInfo<ContactPO> findContactsPage(ContactCriteria criteria) {
        return contactDAO.selectPage(criteria);
    }

    //endregion


    //region contact group
    public void createContactGroup(ContactGroupPO contactGroup) {
        contactGroupDAO.save(contactGroup);
    }

    public void deleteContactGroupById(String id) {
        contactGroupDAO.removeById(id);
    }

    public void updateContactGroupById(ContactGroupPO contactGroup) {
        contactGroupDAO.updateById(contactGroup);
    }

    public ContactGroupPO findContactGroupById(String id) {
        return contactGroupDAO.getById(id);
    }

    public List<ContactGroupPO> findContactGroups(ContactGroupCriteria criteria) {
        return contactGroupDAO.selectList(criteria);
    }

    public PageInfo<ContactGroupPO> findContactGroupsPage(ContactGroupCriteria criteria) {
        return contactGroupDAO.selectPage(criteria);
    }

    //endregion

}
