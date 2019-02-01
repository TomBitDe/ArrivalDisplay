package com.home.arrivaldisplay;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Info dislay Value Object.
 */
@XmlRootElement(name = "InfoDisplayVO")
public class InfoDisplayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flgtNo;
    private String schedFlgtDt;
    private String arpo;
    private String expected;
    private String comments;
    private String updated;

    public InfoDisplayVO() {
    }

    public String getFlgtNo() {
        return flgtNo;
    }

    public void setFlgtNo(String flgtNo) {
        this.flgtNo = flgtNo;
    }

    public String getSchedFlgtDt() {
        return schedFlgtDt;
    }

    public void setSchedFlgtDt(String schedFlgtDt) {
        this.schedFlgtDt = schedFlgtDt;
    }

    public String getArpo() {
        return arpo;
    }

    public void setArpo(String arpo) {
        this.arpo = arpo;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.flgtNo);
        hash = 59 * hash + Objects.hashCode(this.schedFlgtDt);
        hash = 59 * hash + Objects.hashCode(this.arpo);
        hash = 59 * hash + Objects.hashCode(this.expected);
        hash = 59 * hash + Objects.hashCode(this.comments);
        hash = 59 * hash + Objects.hashCode(this.updated);
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
        final InfoDisplayVO other = (InfoDisplayVO) obj;
        if (!Objects.equals(this.flgtNo, other.flgtNo)) {
            return false;
        }
        if (!Objects.equals(this.arpo, other.arpo)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.schedFlgtDt, other.schedFlgtDt)) {
            return false;
        }
        if (!Objects.equals(this.expected, other.expected)) {
            return false;
        }
        if (!Objects.equals(this.updated, other.updated)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InfoDisplayVO{" + "flgtNo=" + flgtNo + ", schedFlgtDt=" + schedFlgtDt + ", arpo=" + arpo + ", expected=" + expected + ", comments=" + comments + ", updated=" + updated + '}';
    }
}
