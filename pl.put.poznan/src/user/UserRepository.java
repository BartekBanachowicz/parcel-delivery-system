package user;

import java.util.ArrayList;
import java.util.List;

class UserRepository {

     private final List<User> users = new ArrayList<>();

     User getUser() {
         return users.get(0);
     }

     void addUser(User user) {
         users.add(user);
     }

     void removeUser(User user) {
         users.remove(user);
     }

}
