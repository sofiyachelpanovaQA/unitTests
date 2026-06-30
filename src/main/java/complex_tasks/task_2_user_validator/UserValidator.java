package complex_tasks.task_2_user_validator;

public class UserValidator {
    private boolean validationEnabled = true;

    public boolean isValidationEnabled() {
        return validationEnabled;
    }
    public void setValidationEnabled(boolean validationEnabled) {
        this.validationEnabled = validationEnabled;
    }

    public boolean validateName(User user) {
        if (validationEnabled) {
            if (user.getName() != null && Character.isUpperCase(user.getName().charAt(0))) {
                return true;
            } throw new InvalidUserException("Имя");
        } throw new ValidationDisabledException();
    }

    public boolean validateAge(User user) {
        if (validationEnabled) {
            if (user.getAge() <= 100 && user.getAge() >= 18) {
                return true;
            } throw new InvalidUserException("Возраст");
        } throw new ValidationDisabledException();
    }

    public boolean validateEmail(User  user) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (validationEnabled) {
            if (user.getEmail().matches(emailRegex)) {
                return true;
            } throw new InvalidUserException("Адрес электронной почты");
        } throw new ValidationDisabledException();
    }


}
