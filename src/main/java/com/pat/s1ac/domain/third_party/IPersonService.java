package com.pat.s1ac.domain.third_party;

import com.pat.s1ac.domain.model.util.Response;

public interface IPersonService {
    Response<Boolean> exists(String personId);
}
