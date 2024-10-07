package lt.bongibau.behelpfull.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Volunteer extends User {
    private boolean hasDriverLicense;
    private boolean hasPSC1;
    private LocalDate dateDeNaissance;

    public Volunteer(int id, String username, String password, Date date, boolean hasDriverLicense, boolean hasPSC1) {
        super(id, username, password);
        this.dateDeNaissance = date.toLocalDate();
        this.hasDriverLicense = hasDriverLicense;
        this.hasPSC1 = hasPSC1;
    }

    public int getAge(){
        LocalDate today = LocalDate.now() ;
        if ((dateDeNaissance != null) && (dateDeNaissance.isBefore(today))) {
            return Period.between(dateDeNaissance,today).getYears();
        } else {
            return 0; // si la date de naissance est incorrecte
        }
    }

//    public void acceptRequest(){}
//    public void refuseRequest(){}

}
