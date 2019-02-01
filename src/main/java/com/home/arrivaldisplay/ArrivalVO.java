package com.home.arrivaldisplay;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Arrival info display Value Object.
 */
@XmlRootElement(name = "ArrivalVO")
public class ArrivalVO extends InfoDisplayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String paxExit;

    public ArrivalVO() {
        super();
    }

    public String getPaxExit() {
        return paxExit;
    }

    public void setPaxExit(String paxExit) {
        this.paxExit = paxExit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.paxExit);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArrivalVO other = (ArrivalVO) obj;
        if (!Objects.equals(this.paxExit, other.paxExit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArrivalVO{" + super.toString() + ", paxExit=" + paxExit + '}';
    }
}
