package lv.rtu.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginUtil {

    public static volatile Map<String , LoginInformation> userMap = new HashMap<String, LoginInformation>();

    public static void addUser(String address , LoginInformation user){
        userMap.put(address , user);
    }

    public static void removeOldUsers(){
       Date currentDate = new Date();
       for(LoginInformation user : userMap.values()){
           if(Math.abs(currentDate.getTime()-user.getDate().getTime()) > 300000){
              userMap.remove(user);
           }
       }
    }

}
