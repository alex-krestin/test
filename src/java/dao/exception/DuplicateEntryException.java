package dao.exception;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DuplicateEntryException extends Exception {
    private String entry;
    private String key;

    public DuplicateEntryException(Exception e) {
        ArrayList<String> values = new ArrayList<>();

        Pattern r = Pattern.compile("\'(.*?)\'");
        Matcher m = r.matcher(e.getMessage());

        while (m.find()) {
            values.add(m.group(1));
        }

        if (values.size() == 2) {
            entry = values.get(0);
            key = values.get(1);
        }
    }

    public String getEntry() {
        return entry;
    }

    public String getKey() {
        return key;
    }

    public String getCustomMessage() {
        switch (key) {
            case "username":
                return "Il nome utente scelto è già utilizzato da un altro utente.";
            case "tel":
                return "Il numero di telefono è associato ad un altra agenzia.";
            case "fax":
                return "Il numero di fax è associato ad un altra agenzia.";
            case "email":
                return "L'email è associato ad un altra agenzia.";
            case "agencyCode":
                return "Codice Agenzia è associato ad un altra agenzia.";
            case "unique_title":
                return "Titolo è associato ad un altro servizio"; //TODO rename
            default:
                System.out.println("debug > " + key);
                return "Undefined duplicate entry";
        }
    }
}
