package privilege;

import java.util.ArrayList;
import java.util.List;

class PrivilegeRepository {

    private final List<Privilege> privilegeList = new ArrayList<>();

    void addNewPrivilege(Privilege privilege) {
        privilegeList.add(privilege);
    }

    List<Privilege> getPrivileges() {
        return privilegeList;
    }

    void dropPrivilege(Privilege privilege) {
        privilegeList.remove(privilege);
    }
}
