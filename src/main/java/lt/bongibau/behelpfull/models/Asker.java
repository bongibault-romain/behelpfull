package lt.bongibau.behelpfull.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Asker extends User {

    private LocalDate dateDeNaissance;
    private int validatorId;

    public Asker(int id, String username, String password, Date date, int validatorId) {
        super(id, username, password);
        this.dateDeNaissance = date.toLocalDate();
        this.validatorId = validatorId;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        if ((dateDeNaissance != null) && (dateDeNaissance.isBefore(today))) {
            return Period.between(dateDeNaissance, today).getYears();
        } else {
            return 0; // si la date de naissance est incorrecte
        }
    }

    public int getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(int validatorId) {
        this.validatorId = validatorId;
    }

    //    public Request postRequest(){}
    //    public Request deleteRequest(){}
}
