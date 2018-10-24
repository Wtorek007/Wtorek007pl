package projekt.core.validation;

import java.util.*;

public class ValidationErrors {

    private final Map<String, List<String>> errors = new HashMap<>();

    public void addError(String field, String errorMessage) {
        if (errors.containsKey(field)) {
            errors.get(field).add(errorMessage);
        } else {
            List<String> list = new LinkedList<>();
            list.add(errorMessage);
            errors.put(field, list);
        }
    }

    public int size() {
        return errors.size();
    }

    public List<String> getErrors(String field) {
        if(!errors.containsKey(field)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(errors.get(field));
    }

    public Map<String, List<String>> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public boolean isValid() {
        return size() == 0;
    }

    public boolean isInvalid() {
        return size() > 0;
    }


}