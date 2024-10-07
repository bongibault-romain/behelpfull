package lt.bongibau.behelpfull.models;

import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Asker extends User {

    private LocalDate dateDeNaissance;

    @Nullable
    private Integer validatorId;

    public Asker(int id, String username, String password, Date date, @Nullable Integer validatorId) {
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

    @Nullable
    public Integer getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(@Nullable Integer validatorId) {
        this.validatorId = validatorId;
    }

    //    public Request postRequest(){}
    //    public Request deleteRequest(){}
}
