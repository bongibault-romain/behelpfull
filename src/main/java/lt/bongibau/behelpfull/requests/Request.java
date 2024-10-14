package lt.bongibau.behelpfull.requests;

import org.jetbrains.annotations.Nullable;

public class Request {

    private int id;
    private int time;
    private String description;
    private String status;
    private int askerId;
    @Nullable
    private Integer volunteerId;
    @Nullable
    private Integer validatorId;


    public Request(int time, String description) {
        this.time = time;
        this.description = description;
    }


}
