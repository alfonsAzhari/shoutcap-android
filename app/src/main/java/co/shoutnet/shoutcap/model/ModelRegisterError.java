package co.shoutnet.shoutcap.model;

/**
 * Created by Adam MB on 1/22/2016.
 */
public class ModelRegisterError {
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
        private String shoutid_error;
        private String email_error;

        public String getShoutid_error() {
            return shoutid_error;
        }

        public void setShoutid_error(String shoutid_error) {
            this.shoutid_error = shoutid_error;
        }

        public String getEmail_error() {
            return email_error;
        }

        public void setEmail_error(String email_error) {
            this.email_error = email_error;
        }
    }
}
