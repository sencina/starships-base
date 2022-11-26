package model;

import enums.EntityType;

public interface Entity extends Serializable{

    String getId();

    default String getIdNumber(){
        return getId().split("-")[1];
    }

    EntityType getEntityType();
}
