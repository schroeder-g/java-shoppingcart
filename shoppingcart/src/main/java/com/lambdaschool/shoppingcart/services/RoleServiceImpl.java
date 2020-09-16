package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.exceptions.ResourceNotFoundException;
import com.lambdaschool.shoppingcart.models.Role;
import com.lambdaschool.shoppingcart.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl
    implements RoleService
{

    @Autowired
    RoleRepository rolerepos;

    @Override
    public Role findRoleById(long id) {
        Role role = rolerepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + id + " not found."));
        return role;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Role findByName(String name) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Role update(long id, Role role) {
        return null;
    }
}
