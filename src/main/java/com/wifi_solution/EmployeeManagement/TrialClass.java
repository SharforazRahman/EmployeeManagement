package com.wifi_solution.EmployeeManagement;

public class TrialClass {
    public static void main(String[] args) {
        String url = "http://res.cloudinary.com/doxlmzl4a/image/upload/v1726401588/gdpqjvhpg11kgnp6z1hg.jpg";
        System.out.println(make(url));
    }

    private static String make(String url) {
        System.out.println(url.split("/")[7].split("\\.")[0]);
        return null;
    }
}
