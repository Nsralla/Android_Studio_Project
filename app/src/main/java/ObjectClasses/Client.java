package ObjectClasses;

public class Client extends User{
    public Client() {
    }
    public Client(String firstName, String lastName, String email, String phone, String hashedPassword, String gender) {
        super(firstName,  lastName, email, phone, hashedPassword, gender);
    }
}
