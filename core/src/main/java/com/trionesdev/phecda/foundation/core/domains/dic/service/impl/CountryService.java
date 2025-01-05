package com.trionesdev.phecda.foundation.core.domains.dic.service.impl;


import com.trionesdev.phecda.foundation.core.domains.dic.dao.criteria.CountryCriteria;
import com.trionesdev.phecda.foundation.core.domains.dic.dao.po.CountryPO;
import com.trionesdev.phecda.foundation.core.domains.dic.manager.impl.CountryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryManager countryManager;

    public List<CountryPO> findList(CountryCriteria criteria) {
        return countryManager.findList(criteria);
    }
}
