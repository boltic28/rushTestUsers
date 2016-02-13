package ru.javarush.rushtest.users.backend.model;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Date;

public class User implements Cloneable {

    private int id = 0;
    private String name = "";
    private int age = 0;
    private String admin = "no";
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        try {
            return (User) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return  getId() + " " +
                getName() + " " +
                getAdmin() +  " " +
                getAge();
    }
}
