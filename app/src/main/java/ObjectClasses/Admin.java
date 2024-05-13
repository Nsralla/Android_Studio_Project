package ObjectClasses;

public class Admin extends User{
    public Admin() {
    }

    public Admin(String firstName, String lastName, String email, String phone, String hashedPassword, String gender) {
        super(firstName, lastName, email, phone, hashedPassword, gender);
    }


}
