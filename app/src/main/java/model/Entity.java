package model;

public interface Entity extends Serializable{

    String getId();

    default String getIdNumber(){
        return getId().split("-")[1];
    }

    default String getIdType(){
        return getId().split("-")[0];
    }
}
