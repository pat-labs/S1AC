package com.pat.s1ac.infrastructure.mygrpc.service;

import com.pat.s1ac.domain.third_party.ICompanyService;

public class CompanyService implements ICompanyService {
    public boolean exists(String companyId) {
        return true;
    }
}
