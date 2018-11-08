package com.balli.kbcreturns;

public class Registration {

    public String name;
    public String age;
    public String city;
    public String gender;
    public String correct;
    public String percentage;
    public String attempted;

    public Registration(String name, String age, String city, String gender, String correct, String attempted, String percentage) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.gender = gender;
        this.correct = correct;
        this.percentage = percentage;
        this.attempted = attempted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAttempted() {
        return attempted;
    }

    public void setAttempted(String attempted) {
        this.attempted = attempted;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
