package com.xqh.algorithm.generic;

public class GenericTest {

    public static void main(String[] args) {
        Person[] arry = new Employee[2];
        arry[0] = new Student();
        arry[1] = new Employee();
        test(arry);
    }

    public static void test(Person[] arr){
        System.out.println("nothing...");
    }

    public static class Person{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Student extends Person{
        private String studentCode;

        public String getStudentCode() {
            return studentCode;
        }

        public void setStudentCode(String studentCode) {
            this.studentCode = studentCode;
        }
    }

    public static class Employee extends Person{
        private String employeeCode;

        public String getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
        }
    }
}
