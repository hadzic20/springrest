package com.example.login;

import java.util.List;

public class UserUpdater {
    public boolean updateUser(String name, String surname, String email, int userGroupId, int countryId, String phone, String corporate, String title, String usageReason, UserRepository repository) {
        List<myUser> liste = repository.findAll();
        for (com.example.login.myUser myuser : liste) {
            if (myuser.getEmail().equals(email)) {
                myUser chosenone = repository.getReferenceById(myuser.getId());
                chosenone.setName(name);
                chosenone.setSurname(surname);
                chosenone.setUserGroupId(userGroupId);
                chosenone.setCountryId(countryId);
                chosenone.setPhone(phone);
                chosenone.setCorporate(corporate);
                chosenone.setTitle(title);
                chosenone.setUsageReason(usageReason);
                repository.save(chosenone);
                return true;
            }
        }
        return false;
    }
}
