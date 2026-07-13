package com.pat.s1ac.domain.third_party;

import com.pat.s1ac.domain.model.util.Response;

public interface ICompanyService {
    Response<Boolean> exists(String companyId);
}
