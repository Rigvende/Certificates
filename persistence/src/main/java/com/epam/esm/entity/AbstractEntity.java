package com.epam.esm.entity;

import java.io.Serializable;

/**
 * Class as abstract parent for entities
 * @author Marianna Patrusova
 * @version 1.0
 */
public abstract class AbstractEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1468398121933623850L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
