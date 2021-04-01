import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DriverTest {
    public static void main(String[] args) {
        Object o = null;
        System.out.println(new Gson().toJson(o));

//        List<User> s = new ArrayList<>();
//        s.add(new User("Bob", 22, 666111));
//        s.add(new User("Sal", 55, 61261));
//        s.add(new User("Foo", 32, 5345));
//        s.add(new User("Der", 14, 89000));
//        System.out.println(new Gson().toJson(s));
//        for (User u:s) {
//            System.out.println(new Gson().toJson(u));
//        }
    }
}

class User{
    public String name;
    public int age;
    public int id;

    public User(String name, int age, int id){
        this.name = name;
        this.age = age;
        this.id = id;
    }
}