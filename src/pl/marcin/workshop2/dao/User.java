package pl.marcin.workshop2.dao;

public class User {

    private int id;
    private String userName;
    private String email;
    private String hashPassword;
    private Integer group_id;

    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        hashPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return hashPassword;
    }

    public void setPassword(String password, boolean hashPassword) {

        if (hashPassword) hashPassword(password);
        else this.hashPassword = password;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    private void hashPassword(String password){
        this.hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    @Override

    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", group_id='" + group_id + '\'' +
                '}';
    }

//    public static void main(String[] args) {
//        User user = new User("a", "b", "CR7");
//        System.out.println(user.getPassword());
//    }
}
