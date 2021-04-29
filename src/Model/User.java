/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Dean
 */
public class User {
    public int UserID;
    public String fName;
    public String lName;
    public String userType;
    public String userName;
    public String password;
    public String date;
    public String time;

    public User(int UserID, String fName, String lName, String userType, String userName, String password, String date, String time) {
        this.UserID = UserID;
        this.fName = fName;
        this.lName = lName;
        this.userType = userType;
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

   

    public int getUserID() {
        return UserID;
    }

    

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
