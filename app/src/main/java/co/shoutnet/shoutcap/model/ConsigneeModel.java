package co.shoutnet.shoutcap.model;

/**
 * Created by codelabs on 12/17/15.
 */
public class ConsigneeModel {
    private String Name;
    private String phone;
    private String email;
    private String address;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
