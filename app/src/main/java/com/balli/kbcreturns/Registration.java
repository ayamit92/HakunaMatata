package com.balli.kbcreturns;


import android.support.annotation.NonNull;

import java.util.Comparator;

public class Registration{

    public String name;
    public String age;
    public String city;
    public String gender;
    public String correct;
    public String percentage;
    public String attempted;
    public String uniqueid;

    public Registration() {
    }

    public Registration(String name, String age, String city, String gender, String correct, String attempted, String percentage, String uniqueid) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.gender = gender;
        this.correct = correct;
        this.percentage = percentage;
        this.attempted = attempted;
        this.uniqueid = uniqueid;
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

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

//    @Override
//    public int compareTo(@NonNull Registration registration) {
//        return 0;
//    }
//    public static Comparator<Registration> CorrectComparator
//            = new Comparator<Registration>() {
//
//        public int compare(Registration reg1, Registration reg2) {
//
//            String correct1 = reg1.getCorrect().toUpperCase();
//            String correct2 = reg2.getCorrect().toUpperCase();
//
//            //ascending order
//            //return correct1.compareTo(correct2);
//
//            //descending order
//            return correct2.compareTo(correct1);
//        }
//
//    };

    public static Comparator<Registration> CorrectComparator = new Comparator<Registration>() {

        @Override
        public int compare(Registration e1, Registration e2) {
            return e2.getCorrect().compareTo(e1.getCorrect());
        }
    };

}
