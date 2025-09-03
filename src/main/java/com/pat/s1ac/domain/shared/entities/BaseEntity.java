package com.pat.s1ac.domain.shared.entities;

import com.pat.inventory.domain.shared.exceptions.DomainException;

public interface BaseEntity {
    void validate() throws DomainException;
}
