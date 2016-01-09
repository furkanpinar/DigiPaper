package com.digipaper.framework.generics;

import com.digipaper.framework.interfaces.IGenericModel;

import java.io.Serializable;

public abstract class GenericModel implements IGenericModel, Cloneable, Serializable {

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object object) {
        if (!getClass().isInstance(object)) {
            return false;
        }

        IGenericModel model = (IGenericModel) object;
        return this.getId().intValue() == model.getId().intValue();
    }
}
