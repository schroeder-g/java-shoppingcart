package com.lambdaschool.shoppingcart.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions
{
    @Override
    public boolean isAuthorizedToMakeChange(String username)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (username.equalsIgnoreCase(authentication.getName()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
        {
            return true;
        } else
        {
            throw new EntityNotFoundException(authentication.getName() + " is not allowed to make changes in this domain.");
        }
    }
}
