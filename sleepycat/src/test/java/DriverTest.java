import com.google.gson.Gson;
import factor.re.model.Reimbursement;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverTest {
    public static void main(String[] args) {
//        Object o = null;
//        System.out.println(new Gson().toJson(o));

//        List<User> s = new ArrayList<>();
//        s.add(new User("Bob", 22, 666111));
//        s.add(new User("Sal", 55, 61261));
//        s.add(new User("Foo", 32, 5345));
//        s.add(new User("Der", 14, 89000));
//        System.out.println(new Gson().toJson(s));
//        for (User u:s) {
//            System.out.println(new Gson().toJson(u));
//        }
//        String x = "pob/fleb/foo/user";
//        String regex = "\\/[^\\/]+$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(x);
//        System.out.println(m.group(1));

        String incomingJSON = "{\"amount\":123.0,\"description\":\"yoyoyoyoyo\",\"author\":7,\"status_id\":1,\"type_id\":14}";

        Gson gson = new Gson();
        Reimbursement rs = new Reimbursement(1, 123f, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), "yoyoyoyoyo", 7,
                13, 0, 14);
        System.out.println(gson.toJson(rs));

        Reimbursement r2 = gson.fromJson(incomingJSON, Reimbursement.class);
        System.out.println(r2);
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