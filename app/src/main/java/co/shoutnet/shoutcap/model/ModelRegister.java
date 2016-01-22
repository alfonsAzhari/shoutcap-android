package co.shoutnet.shoutcap.model;

/**
 * Created by Adam MB on 1/22/2016.
 */
public class ModelRegister {
    private String result;
    private Item item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public class Item{
        private String shoutid;
        private String password;
        private String email;

        public String getShoutid() {
            return shoutid;
        }

        public void setShoutid(String shoutid) {
            this.shoutid = shoutid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
