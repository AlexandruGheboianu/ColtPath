package com.ghb.coltpath.core;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Ghebo on 1/17/2016.
 */
public class ModelAuditor implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getName(); //get logged in username
        } else {
            return "Anonymous";
        }
    }
}
