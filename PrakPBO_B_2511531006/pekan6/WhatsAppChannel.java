package shop.notification;

public class WhatsAppChannel extends AbstractChannel {

    public WhatsAppChannel() {
    }

    @Override
    public String channelName() {
        return "WA";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();

        if (phone == null) {
            return null;
        }

        boolean validLocal =
                phone.startsWith("08") && phone.length() >= 10;

        boolean validInternational =
                phone.startsWith("+62") && phone.length() >= 10;

        if (validLocal || validInternational) {
            return phone;
        }

        return null;
    }

    @Override
    protected String prepare(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[WA to " + recipient + "] " + message);
        return true;
    }
}

