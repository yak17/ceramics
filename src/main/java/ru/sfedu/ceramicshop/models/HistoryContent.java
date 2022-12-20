package ru.sfedu.ceramicshop.models;

import ru.sfedu.ceramicshop.models.enums.Outcomes;

import java.util.Date;
import java.util.Objects;

/**
 * The type History content.
 */
public class HistoryContent {
    private String className;
    private Date createdDate;
    private String actor;
    private String methodName;
    private Object object;
    private Outcomes outcomes;

    /**
     * Instantiates a new History content.
     */
    public HistoryContent() {
    }

    /**
     * Instantiates a new History content.
     *
     * @param className   the class name
     * @param createdDate the created date
     * @param actor       the actor
     */
    public HistoryContent(String className, Date createdDate, String actor) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
    }

    /**
     * Instantiates a new History content.
     *
     * @param className   the class name
     * @param createdDate the created date
     * @param actor       the actor
     * @param methodName  the method name
     * @param object      the object
     * @param outcomes    the outcomes
     */
    public HistoryContent(String className, Date createdDate, String actor, String methodName, Object object, Outcomes outcomes) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.methodName = methodName;
        this.object = object;
        this.outcomes = outcomes;
    }

    /**
     * Gets class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets class name.
     *
     * @param className the class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets actor.
     *
     * @return the actor
     */
    public String getActor() {
        return actor;
    }

    /**
     * Sets actor.
     *
     * @param actor the actor
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * Gets method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets object.
     *
     * @param object the object
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Outcomes getStatus() {
        return outcomes;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Outcomes status) {
        this.outcomes = outcomes;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryContent that = (HistoryContent) o;
        return Objects.equals(className, that.className) && Objects.equals(createdDate, that.createdDate) && Objects.equals(actor, that.actor) && Objects.equals(methodName, that.methodName) && Objects.equals(object, that.object) && outcomes == that.outcomes;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(className, createdDate, actor, methodName, object, outcomes);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HistoryContent{" +
                "className='" + className + '\'' +
                ", createdDate=" + createdDate +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", object=" + object +
                ", outcomes=" + outcomes +
                '}';
    }

}
