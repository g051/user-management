package com.user.mngmnt.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AUDIT")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "actor")
    private String actor;

    @Column(name = "action")
    private String action;

    @Column(name = "status")
    private String status;

    @Column(name = "target")
    private String target;

    @Column(name = "info")
    private String info;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Audit() {
    }

    public Audit(String actor, String action, String status, String target, String info, LocalDateTime timestamp) {
        this.actor = actor;
        this.action = action;
        this.status = status;
        this.target = target;
        this.info = info;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "actor='" + actor + '\'' +
                ", action='" + action + '\'' +
                ", status='" + status + '\'' +
                ", target='" + target + '\'' +
                ", info='" + info + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
